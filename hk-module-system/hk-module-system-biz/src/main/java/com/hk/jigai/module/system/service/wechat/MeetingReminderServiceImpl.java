package com.hk.jigai.module.system.service.wechat;

import com.alibaba.fastjson.JSON;
import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.framework.security.core.LoginUser;
import com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingPersonAttendRecordDO;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingRoomInfoDO;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.UserBookMeetingDO;
import com.hk.jigai.module.system.dal.mysql.meetingroominfo.MeetingPersonAttendRecordMapper;
import com.hk.jigai.module.system.dal.mysql.meetingroominfo.MeetingRoomInfoMapper;
import com.hk.jigai.module.system.dal.mysql.meetingroominfo.UserBookMeetingMapper;
import com.hk.jigai.module.system.dal.mysql.user.AdminUserMapper;
import com.hk.jigai.module.system.dal.redis.RedisKeyConstants;
import com.hk.jigai.module.system.service.user.AdminUserService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.*;
import java.util.concurrent.*;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.MEETING_SEND_TEMPLATE_FAILED;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.USER_REPORT_EXISTS;

@Service
@Validated
@Slf4j
public class MeetingReminderServiceImpl implements MeetingReminderService {
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

    @Resource
    private AdminUserMapper adminUserMapper;

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
        if (userBookMeetingDO != null && userBookMeetingDO.getStatus() == 0) {
            Integer startTime = userBookMeetingDO.getStartTime();
            int a = (startTime.intValue() - 1) % 2;
            int hour = (startTime.intValue() - 1) / 2;
            LocalDate dateMeeting = userBookMeetingDO.getDateMeeting();
            LocalDateTime meetingTime = LocalDateTime.of(dateMeeting.getYear(), dateMeeting.getMonth(), dateMeeting.getDayOfMonth(), hour, (a == 1) ? 30 : 0);
            scheduleReminder(meetingId, meetingTime);
        }
    }


    //文档参考https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
    //https://api.weixin.qq.com/sns/jscode2session
    //https://api.weixin.qq.com/sns/oauth2/access_token
    @Override
    public String wechatQueryOpenid(String code) {
        String authStr = restTemplate.exchange("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx145112d98bdbfbfe&secret=3d82310d78a1a2e54b2232ca97bbfdb7&code=" + code + "&grant_type=authorization_code", HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                }).getBody();
        Map authMap = (Map) JSON.parse(authStr);
        if (authMap == null || authMap.get("openid") == null) {
            return null;
        }
        String openid = (String) authMap.get("openid");
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (loginUser != null) {
            adminUserService.updateUserOpenid(SecurityFrameworkUtils.getLoginUser().getId(), openid);
        }
        return openid;
    }

    @Override
    public String wechatMiniAppGetOpenid(String code) {
        String authStr = restTemplate.exchange(
                "https://api.weixin.qq.com/sns/jscode2session?appid=wx49590d619c10f743&secret=aed35eb95d9c6e032f05a76c83d6b514&js_code="
                        + code + "&grant_type=authorization_code", HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                }).getBody();
        Map authMap = (Map) JSON.parse(authStr);
        if (authMap == null || authMap.get("openid") == null) {
            return null;
        }
        String openid = (String) authMap.get("openid");
        return openid;
    }

    @Override
    public void sendReminder(Long meetingId) {
        try {
            UserBookMeetingDO userBookMeetingDO = userBookMeetingMapper.selectById(meetingId);
            MeetingRoomInfoDO meetingRoomInfoDO = meetingRoomInfoMapper.selectById(userBookMeetingDO.getMeetingRoomId());
            if (userBookMeetingDO != null) {
                //1.先重新获取accessToken
                String authToken = (String) redisTemplate.opsForValue().get(RedisKeyConstants.WECHAT_AUTHTOKEN);
                if (!StringUtils.isNotBlank(authToken)) {
                    log.info("请求查询token！");
                    Map authMap = restTemplate.exchange("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx145112d98bdbfbfe&secret=3d82310d78a1a2e54b2232ca97bbfdb7", HttpMethod.POST, null,
                            new ParameterizedTypeReference<HashMap>() {
                            }).getBody();
                    if (authMap != null && authMap.get("access_token") != null) {
                        authToken = (String) authMap.get("access_token");
                    }
                }
                log.info("token:" + authToken);
                if (StringUtils.isNotBlank(authToken)) {
                    redisTemplate.opsForValue().set(RedisKeyConstants.WECHAT_AUTHTOKEN, authToken, 1000, TimeUnit.SECONDS);
                    //2.发送诶新消息
                    //2.1 查询该会议下，所有参与人的openid
                    List<String> openidList = meetingPersonAttendRecordMapper.selectOpenidByMeetingId(meetingId);
                    String launchOpenid = adminUserMapper.selectById(userBookMeetingDO.getUserId()).getOpenid();
                    if (StringUtils.isNotBlank(launchOpenid)) {
                        if (openidList == null) {
                            openidList = new ArrayList<>();
                        }
                        openidList.add(launchOpenid);
                    }
                    Set<String> openidSet = new HashSet<String>(openidList);
                    if (!CollectionUtils.isAnyEmpty(openidSet)) {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        for (String openid : openidSet) {
                            log.info("向微信用户:" + openid + ",发送提醒！");
                            Integer startTime = userBookMeetingDO.getStartTime();
                            int a = (startTime.intValue() - 1) % 2;
                            int hour = (startTime.intValue() - 1) / 2;

                            Map requestBody = new HashMap();
                            requestBody.put("touser", openid);
                            requestBody.put("template_id", "gqKg0G5-01cuXzj7Ldk-vnX7fFaOhudOdP6KZ0YNCO4");
                            Map miniprogram = new HashMap();
                            miniprogram.put("appid", "wx49590d619c10f743");
                            miniprogram.put("pagepath", "pages/meeting/meet-detail/index?id=" + meetingId);
                            requestBody.put("miniprogram", miniprogram);
                            Map data = new HashMap();
                            Map thing6 = new HashMap();
                            data.put("thing6", thing6);
                            thing6.put("value", userBookMeetingDO.getSubject());
                            Map time4 = new HashMap();
                            data.put("time4", time4);
                            time4.put("value", userBookMeetingDO.getDateMeeting() + " " + hour + ":" + ((a == 1) ? 30 : 0));
                            Map thing7 = new HashMap();
                            data.put("thing7", thing7);
                            String roomNo = meetingRoomInfoDO.getRoomNo();
                            if (StringUtils.isBlank(roomNo) || "null".equals(roomNo)) {
                                roomNo = "";
                            }
                            thing7.put("value", meetingRoomInfoDO.getName() + roomNo);
                            requestBody.put("data", data);
                            HttpEntity<HashMap> requestEntity = new HttpEntity(requestBody, headers);
                            HashMap result = restTemplate.exchange("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + authToken, HttpMethod.POST, requestEntity,
                                    new ParameterizedTypeReference<HashMap>() {
                                    }).getBody();
                            Integer successode = new Integer("0");
                            if (result == null || !successode.equals((Integer) result.get("errcode"))) {
                                log.info("发送会议消息异常" + (Integer) result.get("errcode") + ":" + (String) result.get("errmsg"));
                                Integer tokenExpired = new Integer("42001");
                                if (tokenExpired.equals((Integer) result.get("errcode"))) {
                                    redisTemplate.delete(RedisKeyConstants.WECHAT_AUTHTOKEN);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.info("发送会议模板消息异常！", e);
        }
    }


}
