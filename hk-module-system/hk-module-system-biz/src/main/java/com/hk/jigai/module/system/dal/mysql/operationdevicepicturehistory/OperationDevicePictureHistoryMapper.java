package com.hk.jigai.module.system.dal.mysql.operationdevicepicturehistory;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.module.system.controller.admin.operationdevicepicturehistory.vo.OperationDevicePictureHistoryPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicepicturehistory.OperationDevicePictureHistoryDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 运维设备照片表_快照 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface OperationDevicePictureHistoryMapper extends BaseMapperX<OperationDevicePictureHistoryDO> {

    default PageResult<OperationDevicePictureHistoryDO> selectPage(OperationDevicePictureHistoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OperationDevicePictureHistoryDO>()
                .eqIfPresent(OperationDevicePictureHistoryDO::getDeviceId, reqVO.getDeviceId())
                .eqIfPresent(OperationDevicePictureHistoryDO::getType, reqVO.getType())
                .eqIfPresent(OperationDevicePictureHistoryDO::getUrl, reqVO.getUrl())
                .betweenIfPresent(OperationDevicePictureHistoryDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(OperationDevicePictureHistoryDO::getHistoryId, reqVO.getHistoryId())
                .orderByDesc(OperationDevicePictureHistoryDO::getId));
    }

}