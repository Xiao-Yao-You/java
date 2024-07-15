package com.hk.jigai.module.system.service.meetingroominfo;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.MeetingRoomBookRecordReqVO;
import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.MeetingRoomBookRecordRespVO;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingRoomBookRecordDO;

/**
 * 会议室预定记录 Service 接口
 *
 * @author 超级管理员
 */
public interface MeetingRoomBookRecordService {
    /**
     * 查询会议室预定详情
     * @param req
     * @return
     */
    List<MeetingRoomBookRecordRespVO> getMeetingRoomBookRecord(MeetingRoomBookRecordReqVO req);

}