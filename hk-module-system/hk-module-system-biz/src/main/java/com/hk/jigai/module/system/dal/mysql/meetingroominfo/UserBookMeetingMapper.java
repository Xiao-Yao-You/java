package com.hk.jigai.module.system.dal.mysql.meetingroominfo;

import java.time.LocalDateTime;
import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.UserBookMeetingDO;
import com.hk.jigai.module.system.dal.dataobject.notify.NotifyMessageDO;
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
                .betweenIfPresent(UserBookMeetingDO::getCapacity,reqVO.getCapacityList())
                .eqIfPresent(UserBookMeetingDO::getSubject, reqVO.getSubject())
                .betweenIfPresent(UserBookMeetingDO::getDateMeeting, reqVO.getDate())
                .eqIfPresent(UserBookMeetingDO::getEquipment, reqVO.getEquipment())
                .eqIfPresent(UserBookMeetingDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(UserBookMeetingDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(UserBookMeetingDO::getId));
    }

    default int cancel(Long id){
        return update(new UserBookMeetingDO().setStatus(new Integer(1)),
                new LambdaQueryWrapperX<UserBookMeetingDO>()
                        .eq(UserBookMeetingDO::getId, id));
    }


}