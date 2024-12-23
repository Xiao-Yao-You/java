package com.hk.jigai.module.system.dal.mysql.inspectionrecorddetail;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.module.system.controller.admin.inspectionrecorddetail.vo.InspectionRecordDetailPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.inspectionrecorddetail.InspectionRecordDetailDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * 巡检记录详情 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface InspectionRecordDetailMapper extends BaseMapperX<InspectionRecordDetailDO> {

    default PageResult<InspectionRecordDetailDO> selectPage(InspectionRecordDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InspectionRecordDetailDO>()
                .eqIfPresent(InspectionRecordDetailDO::getRecordId, reqVO.getRecordId())
                .eqIfPresent(InspectionRecordDetailDO::getInspectionProjectId, reqVO.getInspectionProjectId())
                .eqIfPresent(InspectionRecordDetailDO::getInspectionProject, reqVO.getInspectionProject())
                .eqIfPresent(InspectionRecordDetailDO::getInspectionIndicators, reqVO.getInspectionIndicators())
                .eqIfPresent(InspectionRecordDetailDO::getResult, reqVO.getResult())
                .eqIfPresent(InspectionRecordDetailDO::getRemark, reqVO.getRemark())
                .eqIfPresent(InspectionRecordDetailDO::getFilePath, reqVO.getFilePath())
                .eqIfPresent(InspectionRecordDetailDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(InspectionRecordDetailDO::getCorrectionStatus, reqVO.getCorrectionStatus())
                .betweenIfPresent(InspectionRecordDetailDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(InspectionRecordDetailDO::getId));
    }

}