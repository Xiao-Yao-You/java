package com.hk.jigai.module.system.dal.mysql.reportpersonorder;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.reportpersonorder.vo.ReportPersonOrderPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportpersonorder.ReportPersonOrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 个人工单处理月报 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface ReportPersonOrderMapper extends BaseMapperX<ReportPersonOrderDO> {

    default PageResult<ReportPersonOrderDO> selectPage(ReportPersonOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportPersonOrderDO>()
                .eqIfPresent(ReportPersonOrderDO::getReportMonth, reqVO.getReportMonth())
                .eqIfPresent(ReportPersonOrderDO::getUserId, reqVO.getUserId())
                .likeIfPresent(ReportPersonOrderDO::getUsername, reqVO.getUsername())
                .eqIfPresent(ReportPersonOrderDO::getTotalOrderCount, reqVO.getTotalOrderCount())
                .eqIfPresent(ReportPersonOrderDO::getCompleteOrderCount, reqVO.getCompleteOrderCount())
                .eqIfPresent(ReportPersonOrderDO::getProcessingOrderCount, reqVO.getProcessingOrderCount())
                .eqIfPresent(ReportPersonOrderDO::getAht, reqVO.getAht())
                .betweenIfPresent(ReportPersonOrderDO::getTotalHandleTime, reqVO.getTotalHandleTime())
                .eqIfPresent(ReportPersonOrderDO::getTimeProportion, reqVO.getTimeProportion())
                .eqIfPresent(ReportPersonOrderDO::getUrgencyLevelDistribution, reqVO.getUrgencyLevelDistribution())
                .eqIfPresent(ReportPersonOrderDO::getOrderTypeDistribution, reqVO.getOrderTypeDistribution())
                .eqIfPresent(ReportPersonOrderDO::getOrderAcceptedProportion, reqVO.getOrderAcceptedProportion())
                .eqIfPresent(ReportPersonOrderDO::getPendingOrderCount, reqVO.getPendingOrderCount())
                .betweenIfPresent(ReportPersonOrderDO::getPendingTotalTime, reqVO.getPendingTotalTime())
                .eqIfPresent(ReportPersonOrderDO::getApt, reqVO.getApt())
                .eqIfPresent(ReportPersonOrderDO::getOnTimeCompletionRate, reqVO.getOnTimeCompletionRate())
                .eqIfPresent(ReportPersonOrderDO::getDailyHandleMax, reqVO.getDailyHandleMax())
                .eqIfPresent(ReportPersonOrderDO::getMonthOnMonthGrowth, reqVO.getMonthOnMonthGrowth())
                .eqIfPresent(ReportPersonOrderDO::getMonthOnMonthGrowthRate, reqVO.getMonthOnMonthGrowthRate())
                .betweenIfPresent(ReportPersonOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReportPersonOrderDO::getId));
    }

}