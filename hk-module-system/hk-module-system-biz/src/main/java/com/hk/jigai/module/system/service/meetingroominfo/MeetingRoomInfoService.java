package com.hk.jigai.module.system.service.meetingroominfo;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.*;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingRoomInfoDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;

/**
 * 会议室基本信息 Service 接口
 *
 * @author 超级管理员
 */
public interface MeetingRoomInfoService {

    /**
     * 创建会议室基本信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMeetingRoomInfo(@Valid MeetingRoomInfoSaveReqVO createReqVO);

    /**
     * 更新会议室基本信息
     *
     * @param updateReqVO 更新信息
     */
    void updateMeetingRoomInfo(@Valid MeetingRoomInfoSaveReqVO updateReqVO);

    /**
     * 删除会议室基本信息
     *
     * @param id 编号
     */
    void deleteMeetingRoomInfo(Long id);

    /**
     * 获得会议室基本信息
     *
     * @param id 编号
     * @return 会议室基本信息
     */
    MeetingRoomInfoDO getMeetingRoomInfo(Long id);

    /**
     * 获得会议室基本信息分页
     *
     * @param pageReqVO 分页查询
     * @return 会议室基本信息分页
     */
    PageResult<MeetingRoomInfoDO> getMeetingRoomInfoPage(MeetingRoomInfoPageReqVO pageReqVO);

}