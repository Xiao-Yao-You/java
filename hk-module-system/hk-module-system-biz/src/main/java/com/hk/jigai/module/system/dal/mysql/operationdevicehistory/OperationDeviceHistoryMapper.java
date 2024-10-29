package com.hk.jigai.module.system.dal.mysql.operationdevicehistory;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.module.system.controller.admin.operationdevicehistory.vo.OperationDeviceHistoryPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicehistory.OperationDeviceHistoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 运维设备 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface OperationDeviceHistoryMapper extends BaseMapperX<OperationDeviceHistoryDO> {

    default PageResult<OperationDeviceHistoryDO> selectPage(OperationDeviceHistoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OperationDeviceHistoryDO>()
                .likeIfPresent(OperationDeviceHistoryDO::getName, reqVO.getName())
                .eqIfPresent(OperationDeviceHistoryDO::getCode, reqVO.getCode())
                .eqIfPresent(OperationDeviceHistoryDO::getDeviceType, reqVO.getDeviceType())
                .likeIfPresent(OperationDeviceHistoryDO::getDeviceTypeName, reqVO.getDeviceTypeName())
                .eqIfPresent(OperationDeviceHistoryDO::getModel, reqVO.getModel())
                .eqIfPresent(OperationDeviceHistoryDO::getLabelCode, reqVO.getLabelCode())
                .eqIfPresent(OperationDeviceHistoryDO::getStatus, reqVO.getStatus())
                .eqIfPresent(OperationDeviceHistoryDO::getCompany, reqVO.getCompany())
                .eqIfPresent(OperationDeviceHistoryDO::getSerialNumber, reqVO.getSerialNumber())
                .eqIfPresent(OperationDeviceHistoryDO::getEffectLevel, reqVO.getEffectLevel())
                .eqIfPresent(OperationDeviceHistoryDO::getNumberName, reqVO.getNumberName())
                .eqIfPresent(OperationDeviceHistoryDO::getAssetNumber, reqVO.getAssetNumber())
                .eqIfPresent(OperationDeviceHistoryDO::getMacAddress1, reqVO.getMacAddress1())
                .eqIfPresent(OperationDeviceHistoryDO::getMacAddress2, reqVO.getMacAddress2())
                .betweenIfPresent(OperationDeviceHistoryDO::getManufactureDate, reqVO.getManufactureDate())
                .betweenIfPresent(OperationDeviceHistoryDO::getWarrantyDate, reqVO.getWarrantyDate())
                .eqIfPresent(OperationDeviceHistoryDO::getNeedCheckFlag, reqVO.getNeedCheckFlag())
                .eqIfPresent(OperationDeviceHistoryDO::getDeptId, reqVO.getDeptId())
                .likeIfPresent(OperationDeviceHistoryDO::getDeptName, reqVO.getDeptName())
                .eqIfPresent(OperationDeviceHistoryDO::getUserId, reqVO.getUserId())
                .eqIfPresent(OperationDeviceHistoryDO::getAddressId, reqVO.getAddressId())
                .eqIfPresent(OperationDeviceHistoryDO::getAddress, reqVO.getAddress())
                .eqIfPresent(OperationDeviceHistoryDO::getLocation, reqVO.getLocation())
                .eqIfPresent(OperationDeviceHistoryDO::getIp1, reqVO.getIp1())
                .eqIfPresent(OperationDeviceHistoryDO::getIp2, reqVO.getIp2())
                .eqIfPresent(OperationDeviceHistoryDO::getRegisterUserId, reqVO.getRegisterUserId())
                .likeIfPresent(OperationDeviceHistoryDO::getRegisterUserName, reqVO.getRegisterUserName())
                .betweenIfPresent(OperationDeviceHistoryDO::getRegisterDate, reqVO.getRegisterDate())
                .betweenIfPresent(OperationDeviceHistoryDO::getScrapDate, reqVO.getScrapDate())
                .eqIfPresent(OperationDeviceHistoryDO::getScrapType, reqVO.getScrapType())
                .eqIfPresent(OperationDeviceHistoryDO::getScrapUserId, reqVO.getScrapUserId())
                .eqIfPresent(OperationDeviceHistoryDO::getScrapDealType, reqVO.getScrapDealType())
                .eqIfPresent(OperationDeviceHistoryDO::getScrapRemark, reqVO.getScrapRemark())
                .betweenIfPresent(OperationDeviceHistoryDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(OperationDeviceHistoryDO::getScrapUserName, reqVO.getScrapUserName())
                .likeIfPresent(OperationDeviceHistoryDO::getUserNickName, reqVO.getUserNickName())
                .eqIfPresent(OperationDeviceHistoryDO::getDeviceId, reqVO.getDeviceId())
                .orderByDesc(OperationDeviceHistoryDO::getId));
    }

}