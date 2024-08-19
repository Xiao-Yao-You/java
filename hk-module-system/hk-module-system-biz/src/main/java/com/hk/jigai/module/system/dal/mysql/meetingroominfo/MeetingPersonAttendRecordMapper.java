package com.hk.jigai.module.system.dal.mysql.meetingroominfo;

import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingPersonAttendRecordDO;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingRoomBookRecordDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.*;

import java.util.List;

/**
 * 参会人员记录 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MeetingPersonAttendRecordMapper extends BaseMapperX<MeetingPersonAttendRecordDO> {

    default int deleteByMeetingBookId(Long meetingBookId){
        return delete(MeetingPersonAttendRecordDO::getMeetingBookId, meetingBookId);
    }

    List<MeetingPersonAttendRecordDO> selectByMeetingId(Long meetingBookId);

    List<Long> selectOpenidByMeetingId(Long meetingBookId);

}