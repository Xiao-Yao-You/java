package com.hk.jigai.module.system.dal.mysql.userreport;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.reportjobplan.vo.ReportJobPlanPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportJobPlanDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 汇报工作计划 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ReportJobPlanMapper extends BaseMapperX<ReportJobPlanDO> {

    default PageResult<ReportJobPlanDO> selectPage(ReportJobPlanPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportJobPlanDO>()
                .eqIfPresent(ReportJobPlanDO::getUserReportId, reqVO.getUserReportId())
                .eqIfPresent(ReportJobPlanDO::getSort, reqVO.getSort())
                .eqIfPresent(ReportJobPlanDO::getContent, reqVO.getContent())
                .betweenIfPresent(ReportJobPlanDO::getEstimatedTime, reqVO.getEstimatedTime())
                .eqIfPresent(ReportJobPlanDO::getNeedSource, reqVO.getNeedSource())
                .betweenIfPresent(ReportJobPlanDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReportJobPlanDO::getId));
    }

}