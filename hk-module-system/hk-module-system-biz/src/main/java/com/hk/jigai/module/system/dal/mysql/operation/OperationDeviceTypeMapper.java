package com.hk.jigai.module.system.dal.mysql.operation;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceTypeDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;

/**
 * 运维设备类型 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface OperationDeviceTypeMapper extends BaseMapperX<OperationDeviceTypeDO> {

    default PageResult<OperationDeviceTypeDO> selectPage(OperationDeviceTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OperationDeviceTypeDO>()
                .likeIfPresent(OperationDeviceTypeDO::getName, reqVO.getName())
                .eqIfPresent(OperationDeviceTypeDO::getSceneCodeId, reqVO.getSceneCodeId())
                .eqIfPresent(OperationDeviceTypeDO::getStatus, reqVO.getStatus())
                .eqIfPresent(OperationDeviceTypeDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(OperationDeviceTypeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OperationDeviceTypeDO::getId));
    }

}