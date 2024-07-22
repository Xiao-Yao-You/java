package com.hk.jigai.module.system.service.meetingroominfo;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingPersonAttendRecordDO;
import com.hk.jigai.module.system.dal.mysql.meetingroominfo.MeetingPersonAttendRecordMapper;

import java.util.List;

/**
 * 参会人员记录 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class MeetingPersonAttendRecordServiceImpl implements MeetingPersonAttendRecordService {

    @Resource
    private MeetingPersonAttendRecordMapper meetingPersonAttendRecordMapper;

    @Override
    public List<MeetingPersonAttendRecordDO> getMeetingPersonAttendRecord(Long meetingBookId) {
        return meetingPersonAttendRecordMapper.selectByMeetingId(meetingBookId);
    }


}