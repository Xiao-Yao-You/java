package com.hk.jigai.module.system.dal.mysql.userreport;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.reportjobschedule.vo.ReportJobSchedulePageReqVO;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportJobScheduleDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 汇报工作进度 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ReportJobScheduleMapper extends BaseMapperX<ReportJobScheduleDO> {

    default PageResult<ReportJobScheduleDO> selectPage(ReportJobSchedulePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportJobScheduleDO>()
                .eqIfPresent(ReportJobScheduleDO::getUserReportId, reqVO.getUserReportId())
                .eqIfPresent(ReportJobScheduleDO::getSort, reqVO.getSort())
                .eqIfPresent(ReportJobScheduleDO::getContent, reqVO.getContent())
                .eqIfPresent(ReportJobScheduleDO::getSituation, reqVO.getSituation())
                .eqIfPresent(ReportJobScheduleDO::getConnectContent, reqVO.getConnectContent())
                .eqIfPresent(ReportJobScheduleDO::getConnectId, reqVO.getConnectId())
                .betweenIfPresent(ReportJobScheduleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReportJobScheduleDO::getId));
    }

}