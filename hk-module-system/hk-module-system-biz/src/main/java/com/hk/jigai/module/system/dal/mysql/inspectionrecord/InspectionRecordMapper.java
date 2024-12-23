package com.hk.jigai.module.system.dal.mysql.inspectionrecord;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.module.system.controller.admin.inspectionrecord.vo.InspectionRecordPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.inspectionrecord.InspectionRecordDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 设备巡检记录 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface InspectionRecordMapper extends BaseMapperX<InspectionRecordDO> {

    default PageResult<InspectionRecordDO> selectPage(InspectionRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InspectionRecordDO>()
                .eqIfPresent(InspectionRecordDO::getDeviceCode, reqVO.getDeviceCode())
                .likeIfPresent(InspectionRecordDO::getDeviceName, reqVO.getDeviceName())
                .eqIfPresent(InspectionRecordDO::getDeviceSerial, reqVO.getDeviceSerial())
                .eqIfPresent(InspectionRecordDO::getDeviceLabelCode, reqVO.getDeviceLabelCode())
                .eqIfPresent(InspectionRecordDO::getDeviceAssetNum, reqVO.getDeviceAssetNum())
                .eqIfPresent(InspectionRecordDO::getTaskStatus, reqVO.getTaskStatus())
                .betweenIfPresent(InspectionRecordDO::getCheckPlanTime, reqVO.getCheckPlanTime())
                .likeIfPresent(InspectionRecordDO::getCheckExecutorName, reqVO.getCheckExecutorName())
                .betweenIfPresent(InspectionRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(InspectionRecordDO::getId));
    }

}