package com.hk.jigai.module.system.dal.mysql.meetingroominfo;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.UserBookMeetingDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.*;

/**
 * 用户预定会议记录 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface UserBookMeetingMapper extends BaseMapperX<UserBookMeetingDO> {

    default PageResult<UserBookMeetingDO> selectPage(UserBookMeetingPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserBookMeetingDO>()
                .eqIfPresent(UserBookMeetingDO::getUserId, reqVO.getUserId())
                .eqIfPresent(UserBookMeetingDO::getUserPhone, reqVO.getUserPhone())
                .eqIfPresent(UserBookMeetingDO::getMeetingRoomId, reqVO.getMeetingRoomId())
                .eqIfPresent(UserBookMeetingDO::getSubject, reqVO.getSubject())
                .eqIfPresent(UserBookMeetingDO::getDateMeeting, reqVO.getDateMeeting())
                .betweenIfPresent(UserBookMeetingDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(UserBookMeetingDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(UserBookMeetingDO::getEquipment, reqVO.getEquipment())
                .eqIfPresent(UserBookMeetingDO::getCapacity, reqVO.getCapacity())
                .eqIfPresent(UserBookMeetingDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(UserBookMeetingDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserBookMeetingDO::getId));
    }

}