package com.hk.jigai.module.system.service.meetingroominfo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.hk.jigai.framework.common.enums.CommonStatusEnum;
import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils;
import com.hk.jigai.module.system.convert.auth.AuthConvert;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingPersonAttendRecordDO;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingRoomBookRecordDO;
import com.hk.jigai.module.system.dal.dataobject.permission.RoleDO;
import com.hk.jigai.module.system.dal.dataobject.permission.UserRoleDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.dal.mysql.meetingroominfo.MeetingPersonAttendRecordMapper;
import com.hk.jigai.module.system.dal.mysql.meetingroominfo.MeetingRoomBookRecordMapper;
import com.hk.jigai.module.system.dal.mysql.permission.RoleMapper;
import com.hk.jigai.module.system.dal.mysql.permission.UserRoleMapper;
import com.hk.jigai.module.system.dal.mysql.user.AdminUserMapper;
import com.hk.jigai.module.system.enums.permission.RoleCodeEnum;
import com.hk.jigai.module.system.service.wechat.MeetingReminderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.*;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.UserBookMeetingDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.meetingroominfo.UserBookMeetingMapper;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.framework.common.pojo.CommonResult.success;
import static com.hk.jigai.framework.common.util.collection.CollectionUtils.convertSet;
import static com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 用户预定会议记录 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
@Slf4j
public class UserBookMeetingServiceImpl implements UserBookMeetingService {

    @Resource
    private UserBookMeetingMapper userBookMeetingMapper;

    @Resource
    private MeetingRoomBookRecordMapper meetingRoomBookRecordMapper;

    @Resource
    private MeetingPersonAttendRecordMapper meetingPersonAttendRecordMapper;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private MeetingReminderService meetingReminderService;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public Long createUserBookMeeting(UserBookMeetingSaveReqVO createReqVO) {
        //校验该时间段是可以预定的
        MeetingRoomBookRecordReqVO req = new MeetingRoomBookRecordReqVO();
        List<Long> meetingIdList = new ArrayList<>();
        meetingIdList.add(createReqVO.getMeetingRoomId());
        req.setDate(createReqVO.getDateMeeting());
        req.setMeetingIdList(meetingIdList);
        req.setStartTime(createReqVO.getStartTime());
        req.setEndTime(createReqVO.getEndTime());
        List<MeetingRoomBookRecordRespVO> bookRecord = meetingRoomBookRecordMapper.queryMeetingBookList(req);
        if (CollectionUtils.isAnyEmpty(bookRecord) || bookRecord.get(0) == null) {
            throw exception(MEETING_ROOM_INFO_NOT_EXISTS);
        }
        if (!CollectionUtils.isAnyEmpty(bookRecord.get(0).getList()) || bookRecord.get(0).getList().size() != 0) {
            throw exception(USER_BOOK_MEETING_ALREADY_BOOKED);
        }

        UserBookMeetingDO userBookMeeting = BeanUtils.toBean(createReqVO, UserBookMeetingDO.class);
        userBookMeeting.setUserId(SecurityFrameworkUtils.getLoginUser().getId());
        //by szw，补充插入发起人名称的逻辑
        AdminUserDO adminUserDO = adminUserMapper.selectById(SecurityFrameworkUtils.getLoginUser().getId());
        if (adminUserDO != null)
            userBookMeeting.setUserNickName(adminUserDO.getNickname());
        // 插入
        userBookMeetingMapper.insert(userBookMeeting);
        //插入会议室预定记录表
        List<MeetingRoomBookRecordDO> roomBookRecordList = new ArrayList<>();
        Integer start = userBookMeeting.getStartTime();
        Integer end = userBookMeeting.getEndTime();
        for (int i = start; i <= end; i++) {
            MeetingRoomBookRecordDO meetingRoomBookRecordDO = new MeetingRoomBookRecordDO();
            meetingRoomBookRecordDO.setMeetingRoomId(userBookMeeting.getMeetingRoomId());
            meetingRoomBookRecordDO.setUserPhone(userBookMeeting.getUserPhone());
            meetingRoomBookRecordDO.setMeetingBookId(new Long(userBookMeeting.getId()));
            meetingRoomBookRecordDO.setSubject(userBookMeeting.getSubject());
            meetingRoomBookRecordDO.setDateMeeting(userBookMeeting.getDateMeeting());
            meetingRoomBookRecordDO.setTimeKey(new Long(i));
            roomBookRecordList.add(meetingRoomBookRecordDO);
        }
        meetingRoomBookRecordMapper.insertBatch(roomBookRecordList);

        //插入参会人员记录表
        List<MeetingPersonAttendRecordDO> personAttendRecordList = new ArrayList<>();
        List<MeetingPersonAttendRecordDO> joinList = createReqVO.getJoinUserList();
        for (MeetingPersonAttendRecordDO meetingPersonAttendRecordDO : joinList) {
            MeetingPersonAttendRecordDO personAttendRecord = new MeetingPersonAttendRecordDO();
            personAttendRecord.setMeetingBookId(new Long(userBookMeeting.getId()));
            personAttendRecord.setUserId(meetingPersonAttendRecordDO.getUserId());
            personAttendRecord.setUserNickName(meetingPersonAttendRecordDO.getUserNickName());
            personAttendRecordList.add(personAttendRecord);
        }
        meetingPersonAttendRecordMapper.insertBatch(personAttendRecordList);
        //会议创建完成后，需要发送微信通知
        Integer startTime = createReqVO.getStartTime();
        int a = (startTime.intValue()-1 )%2 ;
        int hour = (startTime.intValue()-1 )/2 ;
        LocalDate dateMeeting = createReqVO.getDateMeeting();
        LocalDateTime meetingTime = LocalDateTime.of(dateMeeting.getYear(),dateMeeting.getMonth(),dateMeeting.getDayOfMonth(),hour,(a==1)?30:0);
        if(Duration.between(LocalDateTime.now(),meetingTime).toMinutes()>15){
            meetingReminderService.sendReminder(new Long(userBookMeeting.getId()));
        }
        meetingReminderService.scheduleReminder(new Long(userBookMeeting.getId()), meetingTime);
        // 返回
        return userBookMeeting.getId();
    }

    @Override
    public void updateUserBookMeeting(UserBookMeetingSaveReqVO updateReqVO) {
        // 校验存在
        validateUserBookMeetingExists(updateReqVO.getId());
        // 更新
        UserBookMeetingDO updateObj = BeanUtils.toBean(updateReqVO, UserBookMeetingDO.class);
        userBookMeetingMapper.updateById(updateObj);
        //先删除再插入
        meetingRoomBookRecordMapper.deleteByMeetingBookId(updateReqVO.getId());
        List<MeetingRoomBookRecordDO> roomBookRecordList = new ArrayList<>();
        Integer start = updateObj.getStartTime();
        Integer end = updateObj.getEndTime();
        for (int i = start; i < end; i++) {
            MeetingRoomBookRecordDO meetingRoomBookRecordDO = new MeetingRoomBookRecordDO();
            meetingRoomBookRecordDO.setMeetingRoomId(updateObj.getMeetingRoomId());
            meetingRoomBookRecordDO.setUserPhone(updateObj.getUserPhone());
            meetingRoomBookRecordDO.setSubject(updateObj.getSubject());
            meetingRoomBookRecordDO.setMeetingBookId(updateReqVO.getId());
            meetingRoomBookRecordDO.setDateMeeting(updateObj.getDateMeeting());
            meetingRoomBookRecordDO.setTimeKey(new Long(i));
            roomBookRecordList.add(meetingRoomBookRecordDO);
        }
        meetingRoomBookRecordMapper.insertBatch(roomBookRecordList);
        //先删除再插入参会人员记录表
        meetingPersonAttendRecordMapper.deleteByMeetingBookId(updateReqVO.getId());
        List<MeetingPersonAttendRecordDO> personAttendRecordList = new ArrayList<>();
        List<MeetingPersonAttendRecordDO> joinList = updateReqVO.getJoinUserList();
        for (MeetingPersonAttendRecordDO meetingPersonAttendRecordDO : joinList) {
            MeetingPersonAttendRecordDO personAttendRecord = new MeetingPersonAttendRecordDO();
            personAttendRecord.setMeetingBookId(new Long(updateReqVO.getId()));
            personAttendRecord.setUserId(meetingPersonAttendRecordDO.getUserId());
            personAttendRecord.setUserNickName(meetingPersonAttendRecordDO.getUserNickName());
            personAttendRecordList.add(personAttendRecord);
        }
        meetingPersonAttendRecordMapper.insertBatch(personAttendRecordList);
        meetingReminderService.updateReminder(updateReqVO.getId());
    }

    @Override
    public void deleteUserBookMeeting(Long id) {
        // 校验存在
        validateUserBookMeetingExists(id);
        // 删除
        userBookMeetingMapper.deleteById(id);
        meetingRoomBookRecordMapper.deleteByMeetingBookId(id);
        meetingPersonAttendRecordMapper.deleteByMeetingBookId(id);
        meetingReminderService.updateReminder(id);
    }

    @Override
    public void cancelUserBookMeeting(Long id) {
        // 校验存在
        validateUserBookMeetingExists(id);
        userBookMeetingMapper.cancel(id);
        meetingRoomBookRecordMapper.cancel(id);
        meetingReminderService.updateReminder(id);
    }

    private void validateUserBookMeetingExists(Long id) {
        if (userBookMeetingMapper.selectById(id) == null) {
            throw exception(USER_BOOK_MEETING_NOT_EXISTS);
        }
    }

    @Override
    public UserBookMeetingDO getUserBookMeeting(Long id) {
        return userBookMeetingMapper.selectById(id);
    }

    @Override
    public PageResult<UserBookMeetingDO> getUserBookMeetingPage(UserBookMeetingPageReqVO pageReqVO) {
        // 1.2 获得角色列表
        Set<Long> roleIds = convertSet(userRoleMapper.selectListByUserId(getLoginUserId()), UserRoleDO::getRoleId);
        if (CollUtil.isEmpty(roleIds)) {
            return null;
        }
        List<RoleDO> roles = roleMapper.selectBatchIds(roleIds);
        roles.removeIf(role -> !CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus())); // 移除禁用的角色
        boolean isSuperAdmin = false;
        for(RoleDO roleDO : roles){
            if(RoleCodeEnum.isSuperAdmin(roleDO.getCode())){
                isSuperAdmin = true;
                break;
            }
        }
        if(!isSuperAdmin){
            pageReqVO.setUserId(getLoginUserId());
        }

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userPhone",pageReqVO.getUserPhone());
        requestMap.put("meetingRoomId",pageReqVO.getMeetingRoomId());
        requestMap.put("capacityList",pageReqVO.getCapacityList());
        requestMap.put("subject",pageReqVO.getSubject());
        requestMap.put("date",pageReqVO.getDate());
        requestMap.put("userId",pageReqVO.getUserId());
        requestMap.put("offset", (pageReqVO.getPageNo()-1) * pageReqVO.getPageSize());
        requestMap.put("pageSize", pageReqVO.getPageSize());
        Integer count = userBookMeetingMapper.selectCount1(requestMap);
        PageResult<UserBookMeetingDO> result = new PageResult<>();
        result.setTotal(Long.valueOf(count));
        result.setList(userBookMeetingMapper.selectPage(requestMap));
        return result;
    }
}