package com.hk.jigai.module.system.dal.mysql.meetingroominfo;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingRoomInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.*;

/**
 * 会议室基本信息 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface MeetingRoomInfoMapper extends BaseMapperX<MeetingRoomInfoDO> {

    default PageResult<MeetingRoomInfoDO> selectPage(MeetingRoomInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MeetingRoomInfoDO>()
                .likeIfPresent(MeetingRoomInfoDO::getName, reqVO.getName())
                .eqIfPresent(MeetingRoomInfoDO::getPosition, reqVO.getPosition())
                .likeIfPresent(MeetingRoomInfoDO::getRoomNo, reqVO.getRoomNo())
                .betweenIfPresent(MeetingRoomInfoDO::getCapacity, reqVO.getCapacityList())
                .eqIfPresent(MeetingRoomInfoDO::getStatus, reqVO.getStatus())
                .eqIfPresent(MeetingRoomInfoDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(MeetingRoomInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MeetingRoomInfoDO::getId));
    }

}