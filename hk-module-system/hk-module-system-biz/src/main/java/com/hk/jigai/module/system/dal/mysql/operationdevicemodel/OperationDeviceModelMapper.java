package com.hk.jigai.module.system.dal.mysql.operationdevicemodel;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.operationdevicemodel.vo.OperationDeviceModelPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicemodel.OperationDeviceModelDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备型号 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface OperationDeviceModelMapper extends BaseMapperX<OperationDeviceModelDO> {

    default PageResult<OperationDeviceModelDO> selectPage(OperationDeviceModelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OperationDeviceModelDO>()
                .eqIfPresent(OperationDeviceModelDO::getModel, reqVO.getModel())
                .eqIfPresent(OperationDeviceModelDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(OperationDeviceModelDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OperationDeviceModelDO::getId));
    }

}