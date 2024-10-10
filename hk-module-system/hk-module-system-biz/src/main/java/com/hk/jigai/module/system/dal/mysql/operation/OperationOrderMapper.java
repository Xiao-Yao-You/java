package com.hk.jigai.module.system.dal.mysql.operation;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;

/**
 * 工单 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface OperationOrderMapper extends BaseMapperX<OperationOrderDO> {

    default PageResult<OperationOrderDO> selectPage(OperationOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OperationOrderDO>()
                .eqIfPresent(OperationOrderDO::getTitle, reqVO.getTitle())
                .eqIfPresent(OperationOrderDO::getStatus, reqVO.getStatus())
                .eqIfPresent(OperationOrderDO::getDeviceId, reqVO.getDeviceId())
                .eqIfPresent(OperationOrderDO::getLabelCode, reqVO.getLabelCode())
                .likeIfPresent(OperationOrderDO::getDeviceName, reqVO.getDeviceName())
                .eqIfPresent(OperationOrderDO::getAddressId, reqVO.getAddressId())
                .eqIfPresent(OperationOrderDO::getLocation, reqVO.getLocation())
                .eqIfPresent(OperationOrderDO::getSubmitUserId, reqVO.getSubmitUserId())
                .likeIfPresent(OperationOrderDO::getSubmitUserNickName, reqVO.getSubmitUserNickName())
                .eqIfPresent(OperationOrderDO::getSubmitUserMobile, reqVO.getSubmitUserMobile())
                .eqIfPresent(OperationOrderDO::getRequestType, reqVO.getRequestType())
                .eqIfPresent(OperationOrderDO::getQuestionType, reqVO.getQuestionType())
                .eqIfPresent(OperationOrderDO::getLevel, reqVO.getLevel())
                .eqIfPresent(OperationOrderDO::getDescription, reqVO.getDescription())
                .eqIfPresent(OperationOrderDO::getType, reqVO.getType())
                .eqIfPresent(OperationOrderDO::getSourceType, reqVO.getSourceType())
                .eqIfPresent(OperationOrderDO::getDealUserId, reqVO.getDealUserId())
                .likeIfPresent(OperationOrderDO::getDealUserNickName, reqVO.getDealUserNickName())
                .eqIfPresent(OperationOrderDO::getAllocationUserId, reqVO.getAllocationUserId())
                .likeIfPresent(OperationOrderDO::getAllocationUserNickName, reqVO.getAllocationUserNickName())
                .betweenIfPresent(OperationOrderDO::getAllocationTime, reqVO.getAllocationTime())
                .eqIfPresent(OperationOrderDO::getAllocationConsume, reqVO.getAllocationConsume())
                .betweenIfPresent(OperationOrderDO::getSiteConfirmTime, reqVO.getSiteConfirmTime())
                .eqIfPresent(OperationOrderDO::getSiteDonfirmConsume, reqVO.getSiteDonfirmConsume())
                .betweenIfPresent(OperationOrderDO::getDealTime, reqVO.getDealTime())
                .eqIfPresent(OperationOrderDO::getDealConsume, reqVO.getDealConsume())
                .betweenIfPresent(OperationOrderDO::getHangUpTime, reqVO.getHangUpTime())
                .eqIfPresent(OperationOrderDO::getHangUpConsume, reqVO.getHangUpConsume())
                .betweenIfPresent(OperationOrderDO::getCompleteTime, reqVO.getCompleteTime())
                .eqIfPresent(OperationOrderDO::getCompleteConsume, reqVO.getCompleteConsume())
                .betweenIfPresent(OperationOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OperationOrderDO::getId));
    }

}