package com.hk.jigai.module.system.dal.mysql.reportgrouporder;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.reportgrouporder.vo.ReportGroupOrderPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportgrouporder.ReportGroupOrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 小组工单处理月报 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface ReportGroupOrderMapper extends BaseMapperX<ReportGroupOrderDO> {

    default PageResult<ReportGroupOrderDO> selectPage(ReportGroupOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportGroupOrderDO>()
                .eqIfPresent(ReportGroupOrderDO::getGroupId, reqVO.getGroupId())
                .likeIfPresent(ReportGroupOrderDO::getGroupName, reqVO.getGroupName())
                .eqIfPresent(ReportGroupOrderDO::getReportMonth, reqVO.getReportMonth())
                .eqIfPresent(ReportGroupOrderDO::getOrderCount, reqVO.getOrderCount())
                .eqIfPresent(ReportGroupOrderDO::getUrgencyLevelDistribution, reqVO.getUrgencyLevelDistribution())
                .eqIfPresent(ReportGroupOrderDO::getOrderTypeDistribution, reqVO.getOrderTypeDistribution())
                .eqIfPresent(ReportGroupOrderDO::getOrderAcceptedProportion, reqVO.getOrderAcceptedProportion())
                .eqIfPresent(ReportGroupOrderDO::getPendingOrderCount, reqVO.getPendingOrderCount())
                .betweenIfPresent(ReportGroupOrderDO::getPendingTotalTime, reqVO.getPendingTotalTime())
                .eqIfPresent(ReportGroupOrderDO::getApt, reqVO.getApt())
                .eqIfPresent(ReportGroupOrderDO::getOnTimeCompletionRate, reqVO.getOnTimeCompletionRate())
                .eqIfPresent(ReportGroupOrderDO::getMonthOnMonthGrowth, reqVO.getMonthOnMonthGrowth())
                .eqIfPresent(ReportGroupOrderDO::getMonthOnMonthGrowthRate, reqVO.getMonthOnMonthGrowthRate())
                .betweenIfPresent(ReportGroupOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReportGroupOrderDO::getId));
    }

}