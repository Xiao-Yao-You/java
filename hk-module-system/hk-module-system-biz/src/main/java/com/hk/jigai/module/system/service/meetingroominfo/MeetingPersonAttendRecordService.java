package com.hk.jigai.module.system.service.meetingroominfo;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingPersonAttendRecordDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;

/**
 * 参会人员记录 Service 接口
 *
 * @author 超级管理员
 */
public interface MeetingPersonAttendRecordService {


    /**
     * 根据会议ID获得参会人员记录
     *
     * @param meetingBookId 会议编号
     * @return 参会人员记录
     */
    List<Long> getMeetingPersonAttendRecord(Long meetingBookId);

}