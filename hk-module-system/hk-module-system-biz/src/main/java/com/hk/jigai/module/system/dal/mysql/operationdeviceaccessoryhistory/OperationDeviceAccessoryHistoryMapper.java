package com.hk.jigai.module.system.dal.mysql.operationdeviceaccessoryhistory;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.module.system.controller.admin.operationdeviceaccessoryhistory.vo.OperationDeviceAccessoryHistoryPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdeviceaccessoryhistory.OperationDeviceAccessoryHistoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 运维设备配件表_快照 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface OperationDeviceAccessoryHistoryMapper extends BaseMapperX<OperationDeviceAccessoryHistoryDO> {

    default PageResult<OperationDeviceAccessoryHistoryDO> selectPage(OperationDeviceAccessoryHistoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OperationDeviceAccessoryHistoryDO>()
                .eqIfPresent(OperationDeviceAccessoryHistoryDO::getDeviceId, reqVO.getDeviceId())
                .eqIfPresent(OperationDeviceAccessoryHistoryDO::getAccessoryDesc, reqVO.getAccessoryDesc())
                .eqIfPresent(OperationDeviceAccessoryHistoryDO::getModel, reqVO.getModel())
                .eqIfPresent(OperationDeviceAccessoryHistoryDO::getNum, reqVO.getNum())
                .eqIfPresent(OperationDeviceAccessoryHistoryDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(OperationDeviceAccessoryHistoryDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(OperationDeviceAccessoryHistoryDO::getHistoryId, reqVO.getHistoryId())
                .orderByDesc(OperationDeviceAccessoryHistoryDO::getId));
    }

}