package com.hk.jigai.module.system.service.wechat;

import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingPersonAttendRecordDO;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingRoomInfoDO;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.UserBookMeetingDO;
import com.hk.jigai.module.system.dal.mysql.meetingroominfo.MeetingPersonAttendRecordMapper;
import com.hk.jigai.module.system.dal.mysql.meetingroominfo.MeetingRoomInfoMapper;
import com.hk.jigai.module.system.dal.mysql.meetingroominfo.UserBookMeetingMapper;
import com.hk.jigai.module.system.dal.redis.RedisKeyConstants;
import com.hk.jigai.module.system.service.user.AdminUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
@Service
@Validated
public class MeetingReminderServiceImpl implements MeetingReminderService{
    @Resource
    private UserBookMeetingMapper userBookMeetingMapper;
    @Resource
    private MeetingRoomInfoMapper meetingRoomInfoMapper;
    @Resource
    private MeetingPersonAttendRecordMapper meetingPersonAttendRecordMapper;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private AdminUserService adminUserService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    //存储每个会议的定时任务
    private ConcurrentHashMap<Long, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();

    @Override
    public void scheduleReminder(Long meetingId, LocalDateTime meetingTime) {
        Runnable reminderTask = () -> sendReminder(meetingId);
        long delay = Duration.between(LocalDateTime.now(), meetingTime.minusMinutes(15)).toMillis();
        ScheduledFuture<?> future = scheduler.schedule(reminderTask, delay, TimeUnit.MILLISECONDS);
        tasks.put(meetingId, future);
    }

    @Override
    public void updateReminder(Long meetingId) {
        //1.有更新先删
        ScheduledFuture<?> future = tasks.remove(meetingId);
        if (future != null) {
            future.cancel(false);
        }
        //2.如果状态正常，重新put
        UserBookMeetingDO userBookMeetingDO = userBookMeetingMapper.selectById(meetingId);
        if(userBookMeetingDO != null && userBookMeetingDO.getStatus()==0){
            Integer startTime = userBookMeetingDO.getStartTime();
            int a = (startTime.intValue()-1 )%2 ;
            int hour = (startTime.intValue()-1 )/2 ;
            LocalDate dateMeeting = userBookMeetingDO.getDateMeeting();
            LocalDateTime meetingTime = LocalDateTime.of(dateMeeting.getYear(),dateMeeting.getMonth(),dateMeeting.getDayOfMonth(),hour,(a==1)?30:0);
            scheduleReminder(meetingId,meetingTime);
        }
    }


    //文档参考https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
    //跳转接口，根据code获取accessToken以及openid，将该openid落地user表
    //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
    /**
     * {
     *   "access_token":"ACCESS_TOKEN",
     *   "expires_in":7200,
     *   "refresh_token":"REFRESH_TOKEN",
     *   "openid":"OPENID",
     *   "scope":"SCOPE",
     *   "is_snapshotuser": 1,
     *   "unionid": "UNIONID"
     * }
     */
    @Override
    public String wechatQueryOpenid(String code) {
        Map authMap = restTemplate.exchange("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx145112d98bdbfbfe&secret=3d82310d78a1a2e54b2232ca97bbfdb7&code=" + code + "&grant_type=authorization_code", HttpMethod.POST, null,
                new ParameterizedTypeReference<HashMap>() {}).getBody();
        String openid = (String)authMap.get("openid");
        if(StringUtils.isNotBlank(openid)){
            Long userid = SecurityFrameworkUtils.getLoginUser().getId();
            adminUserService.updateUserOpenid(userid, openid);
        }
        return null;
    }

    private void sendReminder(Long meetingId) {
        UserBookMeetingDO userBookMeetingDO = userBookMeetingMapper.selectById(meetingId);
        MeetingRoomInfoDO meetingRoomInfoDO = meetingRoomInfoMapper.selectById(userBookMeetingDO.getMeetingRoomId());
        if(userBookMeetingDO != null){
            //1.先重新获取accessToken
            String authToken = (String)redisTemplate.opsForValue().get(RedisKeyConstants.WECHAT_AUTHTOKEN);
            if(!StringUtils.isNotBlank(authToken)){
                Map authMap = restTemplate.exchange("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx145112d98bdbfbfe&secret=3d82310d78a1a2e54b2232ca97bbfdb7", HttpMethod.POST, null,
                        new ParameterizedTypeReference<HashMap>() {}).getBody();
                if(authMap != null && authMap.get("access_token") != null) {
                    authToken = (String) authMap.get("access_token");
                }
            }

            if(StringUtils.isNotBlank(authToken)){
                redisTemplate.opsForValue().set(RedisKeyConstants.WECHAT_AUTHTOKEN,authToken,7000,TimeUnit.SECONDS);
                //2.发送诶新消息
                //2.1 查询该会议下，所有参与人的openid
                List<Long> openidList = meetingPersonAttendRecordMapper.selectOppenidByMeetingId(meetingId);
                if(!CollectionUtils.isAnyEmpty(openidList)){
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    for(Long openid : openidList){
                        Integer startTime = userBookMeetingDO.getStartTime();
                        int a = (startTime.intValue()-1 )%2 ;
                        int hour = (startTime.intValue()-1 )/2 ;

                        Map requestBody = new HashMap();
                        requestBody.put("touser",openid.toString());
                        requestBody.put("template_id","gqKg0G5-01cuXzj7Ldk-vnX7fFaOhudOdP6KZ0YNCO4");
                        Map data = new HashMap();
                        Map thing6 = new HashMap();
                        data.put("thing6", thing6);
                        thing6.put("value", userBookMeetingDO.getSubject());
                        Map time4 = new HashMap();
                        data.put("time4", time4);
                        time4.put("value", userBookMeetingDO.getDateMeeting() + " " + hour +":"+ ((a==1)?30:0));
                        Map thing7 = new HashMap();
                        data.put("thing7", thing7);
                        thing7.put("value", meetingRoomInfoDO.getName() + meetingRoomInfoDO.getRoomNo());
                        requestBody.put("data",data);
                        HttpEntity<HashMap> requestEntity = new HttpEntity(requestBody, headers);
                        restTemplate.exchange("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+authToken, HttpMethod.POST, requestEntity,
                                new ParameterizedTypeReference<HashMap>() {}).getBody();
                    }
                }
            }
        }
//        {
//            "touser":"OPENID",
//                "template_id":"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
//                "url":"http://weixin.qq.com/download",
//                "miniprogram":{
//            "appid":"xiaochengxuappid12345",
//                    "pagepath":"index?foo=bar"
//        },
//            "client_msg_id":"MSG_000001",
//                "data":{
//
//            "keyword1":{
//                "value":"巧克力"
//            },
//            "keyword2": {
//                "value":"39.8元"
//            },
//            "keyword3": {
//                "value":"2014年9月22日"
//            }
//        }
//        }

//        {
//            "errcode":0,
//                "errmsg":"ok",
//                "msgid":200228332
//        }

    }
}
