package com.hk.jigai.module.system.service.meetingroominfo;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.*;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.UserBookMeetingDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;

/**
 * 用户预定会议记录 Service 接口
 *
 * @author 超级管理员
 */
public interface UserBookMeetingService {

    /**
     * 创建用户预定会议记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUserBookMeeting(@Valid UserBookMeetingSaveReqVO createReqVO);

    /**
     * 更新用户预定会议记录
     *
     * @param updateReqVO 更新信息
     */
    void updateUserBookMeeting(@Valid UserBookMeetingSaveReqVO updateReqVO);

    /**
     * 删除用户预定会议记录
     *
     * @param id 编号
     */
    void deleteUserBookMeeting(Long id);

    /**
     * 获得用户预定会议记录
     *
     * @param id 编号
     * @return 用户预定会议记录
     */
    UserBookMeetingDO getUserBookMeeting(Long id);

    /**
     * 获得用户预定会议记录分页
     *
     * @param pageReqVO 分页查询
     * @return 用户预定会议记录分页
     */
    PageResult<UserBookMeetingDO> getUserBookMeetingPage(UserBookMeetingPageReqVO pageReqVO);

}