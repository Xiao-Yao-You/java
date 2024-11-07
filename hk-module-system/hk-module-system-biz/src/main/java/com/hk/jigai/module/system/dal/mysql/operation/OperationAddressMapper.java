package com.hk.jigai.module.system.dal.mysql.operation;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationAddressDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationQuestionTypeDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;

/**
 * 运维地点 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface OperationAddressMapper extends BaseMapperX<OperationAddressDO> {

    default PageResult<OperationAddressDO> selectPage(OperationAddressPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OperationAddressDO>()
                .likeIfPresent(OperationAddressDO::getAddressName, reqVO.getAddressName())
                .eqIfPresent(OperationAddressDO::getParentAddressId, reqVO.getParentAddressId())
                .eqIfPresent(OperationAddressDO::getSoftUserId, reqVO.getSoftUserId())
                .likeIfPresent(OperationAddressDO::getSoftUserNickName, reqVO.getSoftUserNickName())
                .eqIfPresent(OperationAddressDO::getHardwareUserId, reqVO.getHardwareUserId())
                .likeIfPresent(OperationAddressDO::getHardwareUserNickName, reqVO.getHardwareUserNickName())
                .eqIfPresent(OperationAddressDO::getStatus, reqVO.getStatus())
                .eqIfPresent(OperationAddressDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(OperationAddressDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OperationAddressDO::getId));
    }


    default List<OperationAddressDO> selectList(OperationAddressRespVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OperationAddressDO>()
                .likeIfPresent(OperationAddressDO::getAddressName, reqVO.getAddressName())
        );
    }
}