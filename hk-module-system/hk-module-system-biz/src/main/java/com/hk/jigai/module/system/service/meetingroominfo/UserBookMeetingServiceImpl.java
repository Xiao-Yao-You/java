package com.hk.jigai.module.system.service.meetingroominfo;

import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingPersonAttendRecordDO;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingRoomBookRecordDO;
import com.hk.jigai.module.system.dal.mysql.meetingroominfo.MeetingPersonAttendRecordMapper;
import com.hk.jigai.module.system.dal.mysql.meetingroominfo.MeetingRoomBookRecordMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.*;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.UserBookMeetingDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.meetingroominfo.UserBookMeetingMapper;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 用户预定会议记录 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class UserBookMeetingServiceImpl implements UserBookMeetingService {

    @Resource
    private UserBookMeetingMapper userBookMeetingMapper;

    @Resource
    private MeetingRoomBookRecordMapper meetingRoomBookRecordMapper;

    @Resource
    private MeetingPersonAttendRecordMapper meetingPersonAttendRecordMapper;

    @Override
    @Transactional
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
        if(CollectionUtils.isAnyEmpty(bookRecord) || bookRecord.get(0)==null){
            throw exception(MEETING_ROOM_INFO_NOT_EXISTS);
        }
        if(!CollectionUtils.isAnyEmpty(bookRecord.get(0).getList()) || bookRecord.get(0).getList().size()!=0 ){
            throw exception(USER_BOOK_MEETING_ALREADY_BOOKED);
        }

        UserBookMeetingDO userBookMeeting = BeanUtils.toBean(createReqVO, UserBookMeetingDO.class);
        userBookMeeting.setUserId(SecurityFrameworkUtils.getLoginUser().getId());
        // 插入
        int bookId = userBookMeetingMapper.insert(userBookMeeting);
        //插入会议室预定记录表
        List<MeetingRoomBookRecordDO> roomBookRecordList = new ArrayList<>();
        Integer start = userBookMeeting.getStartTime();
        Integer end = userBookMeeting.getEndTime();
        for(int i = start;i<end; i++){
            MeetingRoomBookRecordDO meetingRoomBookRecordDO = new MeetingRoomBookRecordDO();
            meetingRoomBookRecordDO.setMeetingRoomId(userBookMeeting.getMeetingRoomId());
            meetingRoomBookRecordDO.setUserPhone(userBookMeeting.getUserPhone());
            meetingRoomBookRecordDO.setMeetingBookId(new Long(bookId));
            meetingRoomBookRecordDO.setSubject(userBookMeeting.getSubject());
            meetingRoomBookRecordDO.setDateMeeting(userBookMeeting.getDateMeeting());
            meetingRoomBookRecordDO.setTimeKey(new Long(i));
            roomBookRecordList.add(meetingRoomBookRecordDO);
        }
        meetingRoomBookRecordMapper.insertBatch(roomBookRecordList);

        //插入参会人员记录表
        List<MeetingPersonAttendRecordDO> personAttendRecordList = new ArrayList<>();
        List<Long> joinList = createReqVO.getJoinUserId();
        for(Long userId : joinList){
            MeetingPersonAttendRecordDO personAttendRecord = new MeetingPersonAttendRecordDO();
            personAttendRecord.setMeetingBookId(new Long(bookId));
            personAttendRecord.setUserId(userId);
            personAttendRecordList.add(personAttendRecord);
        }
        meetingPersonAttendRecordMapper.insertBatch(personAttendRecordList);
        // 返回
        return userBookMeeting.getId();
    }

    @Override
    @Transactional
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
        for(int i = start;i<end; i++){
            MeetingRoomBookRecordDO meetingRoomBookRecordDO = new MeetingRoomBookRecordDO();
            meetingRoomBookRecordDO.setMeetingRoomId(updateObj.getMeetingRoomId());
            meetingRoomBookRecordDO.setUserPhone(updateObj.getUserPhone());
            meetingRoomBookRecordDO.setSubject(updateObj.getSubject());
            meetingRoomBookRecordDO.setDateMeeting(updateObj.getDateMeeting());
            meetingRoomBookRecordDO.setTimeKey(new Long(i));
            roomBookRecordList.add(meetingRoomBookRecordDO);
        }
        meetingRoomBookRecordMapper.insertBatch(roomBookRecordList);
        //先删除再插入参会人员记录表
        meetingPersonAttendRecordMapper.deleteByMeetingBookId(updateReqVO.getId());
        List<MeetingPersonAttendRecordDO> personAttendRecordList = new ArrayList<>();
        List<Long> joinList = updateReqVO.getJoinUserId();
        for(Long userId : joinList){
            MeetingPersonAttendRecordDO personAttendRecord = new MeetingPersonAttendRecordDO();
            personAttendRecord.setMeetingBookId(new Long(updateReqVO.getId()));
            personAttendRecord.setUserId(userId);
            personAttendRecordList.add(personAttendRecord);
        }
        meetingPersonAttendRecordMapper.insertBatch(personAttendRecordList);
    }

    @Override
    @Transactional
    public void deleteUserBookMeeting(Long id) {
        // 校验存在
        validateUserBookMeetingExists(id);
        // 删除
        userBookMeetingMapper.deleteById(id);
        meetingRoomBookRecordMapper.deleteByMeetingBookId(id);
        meetingPersonAttendRecordMapper.deleteByMeetingBookId(id);

    }

    @Override
    public void cancelUserBookMeeting(Long id) {
        // 校验存在
        validateUserBookMeetingExists(id);
        userBookMeetingMapper.cancel(id);
        meetingRoomBookRecordMapper.deleteByMeetingBookId(id);
        meetingPersonAttendRecordMapper.deleteByMeetingBookId(id);
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
        return userBookMeetingMapper.selectPage(pageReqVO);
    }

}