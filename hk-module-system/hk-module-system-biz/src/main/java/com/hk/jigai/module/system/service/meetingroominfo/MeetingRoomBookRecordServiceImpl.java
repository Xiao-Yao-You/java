package com.hk.jigai.module.system.service.meetingroominfo;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.*;
import com.hk.jigai.module.system.dal.mysql.meetingroominfo.MeetingRoomBookRecordMapper;

import java.util.List;

/**
 * 会议室预定记录 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class MeetingRoomBookRecordServiceImpl implements MeetingRoomBookRecordService {

    @Resource
    private MeetingRoomBookRecordMapper meetingRoomBookRecordMapper;


    @Override
    public List<MeetingRoomBookRecordRespVO> getMeetingRoomBookRecord(MeetingRoomBookRecordReqVO req) {
        return meetingRoomBookRecordMapper.queryMeetingBookList(req);
    }
}