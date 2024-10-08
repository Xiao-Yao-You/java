package com.hk.jigai.module.system.dal.mysql.operation;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;

/**
 * 运维设备 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface OperationDeviceMapper extends BaseMapperX<OperationDeviceDO> {

    default PageResult<OperationDeviceDO> selectPage(OperationDevicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OperationDeviceDO>()
                .likeIfPresent(OperationDeviceDO::getName, reqVO.getName())
                .eqIfPresent(OperationDeviceDO::getCode, reqVO.getCode())
                .eqIfPresent(OperationDeviceDO::getDeviceType, reqVO.getDeviceType())
                .likeIfPresent(OperationDeviceDO::getDeviceTypeName, reqVO.getDeviceTypeName())
                .eqIfPresent(OperationDeviceDO::getModel, reqVO.getModel())
                .eqIfPresent(OperationDeviceDO::getLabelCode, reqVO.getLabelCode())
                .eqIfPresent(OperationDeviceDO::getStatus, reqVO.getStatus())
                .eqIfPresent(OperationDeviceDO::getCompany, reqVO.getCompany())
                .eqIfPresent(OperationDeviceDO::getSerialNumber, reqVO.getSerialNumber())
                .eqIfPresent(OperationDeviceDO::getEffectLevel, reqVO.getEffectLevel())
                .likeIfPresent(OperationDeviceDO::getNumberName, reqVO.getNumberName())
                .eqIfPresent(OperationDeviceDO::getAssetNumber, reqVO.getAssetNumber())
                .eqIfPresent(OperationDeviceDO::getMacAddress1, reqVO.getMacAddress1())
                .eqIfPresent(OperationDeviceDO::getMacAddress2, reqVO.getMacAddress2())
                .eqIfPresent(OperationDeviceDO::getAddressId, reqVO.getAddressId())
                .likeIfPresent(OperationDeviceDO::getAddress, reqVO.getAddress())
                .betweenIfPresent(OperationDeviceDO::getManufactureDate, reqVO.getManufactureDate())
                .betweenIfPresent(OperationDeviceDO::getWarrantyDate, reqVO.getWarrantyDate())
                .eqIfPresent(OperationDeviceDO::getNeedCheckFlag, reqVO.getNeedCheckFlag())
                .betweenIfPresent(OperationDeviceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OperationDeviceDO::getId));
    }

}