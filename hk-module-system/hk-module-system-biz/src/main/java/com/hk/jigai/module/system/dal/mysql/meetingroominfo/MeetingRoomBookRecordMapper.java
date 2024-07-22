package com.hk.jigai.module.system.dal.mysql.meetingroominfo;


import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.MeetingRoomBookRecordReqVO;
import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.MeetingRoomBookRecordRespVO;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingRoomBookRecordDO;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.UserBookMeetingDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 会议室预定记录 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MeetingRoomBookRecordMapper extends BaseMapperX<MeetingRoomBookRecordDO> {

    default int deleteByMeetingBookId(Long meetingBookId){
        return delete(MeetingRoomBookRecordDO::getMeetingBookId, meetingBookId);
    }

    List<MeetingRoomBookRecordRespVO> queryMeetingBookList(MeetingRoomBookRecordReqVO req);

    default int cancel(Long id){
        return update(new MeetingRoomBookRecordDO().setStatus(new Integer(1)),
                new LambdaQueryWrapperX<MeetingRoomBookRecordDO>()
                        .eq(MeetingRoomBookRecordDO::getMeetingBookId, id));
    }

}