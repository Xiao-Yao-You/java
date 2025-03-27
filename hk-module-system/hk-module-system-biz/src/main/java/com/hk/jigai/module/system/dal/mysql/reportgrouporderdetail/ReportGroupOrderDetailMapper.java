package com.hk.jigai.module.system.dal.mysql.reportgrouporderdetail;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.reportgrouporderdetail.vo.ReportGroupOrderDetailPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportgrouporderdetail.ReportGroupOrderDetailDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 小组工单处理月报详情 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface ReportGroupOrderDetailMapper extends BaseMapperX<ReportGroupOrderDetailDO> {

    default PageResult<ReportGroupOrderDetailDO> selectPage(ReportGroupOrderDetailPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportGroupOrderDetailDO>()
                .eqIfPresent(ReportGroupOrderDetailDO::getGroupId, reqVO.getGroupId())
                .likeIfPresent(ReportGroupOrderDetailDO::getGroupName, reqVO.getGroupName())
                .eqIfPresent(ReportGroupOrderDetailDO::getReportMonth, reqVO.getReportMonth())
                .eqIfPresent(ReportGroupOrderDetailDO::getQuestionTypeId, reqVO.getQuestionTypeId())
                .likeIfPresent(ReportGroupOrderDetailDO::getQuestionTypeName, reqVO.getQuestionTypeName())
                .eqIfPresent(ReportGroupOrderDetailDO::getOrderCount, reqVO.getOrderCount())
                .eqIfPresent(ReportGroupOrderDetailDO::getGroupProportion, reqVO.getGroupProportion())
                .eqIfPresent(ReportGroupOrderDetailDO::getUrgencyLevelDistribution, reqVO.getUrgencyLevelDistribution())
                .eqIfPresent(ReportGroupOrderDetailDO::getOrderTypeDistribution, reqVO.getOrderTypeDistribution())
                .eqIfPresent(ReportGroupOrderDetailDO::getOrderAcceptedProportion, reqVO.getOrderAcceptedProportion())
                .eqIfPresent(ReportGroupOrderDetailDO::getPendingOrderCount, reqVO.getPendingOrderCount())
                .betweenIfPresent(ReportGroupOrderDetailDO::getPendingTotalTime, reqVO.getPendingTotalTime())
                .eqIfPresent(ReportGroupOrderDetailDO::getApt, reqVO.getApt())
                .eqIfPresent(ReportGroupOrderDetailDO::getOnTimeCompletionRate, reqVO.getOnTimeCompletionRate())
                .eqIfPresent(ReportGroupOrderDetailDO::getMonthOnMonthGrowth, reqVO.getMonthOnMonthGrowth())
                .eqIfPresent(ReportGroupOrderDetailDO::getMonthOnMonthGrowthRate, reqVO.getMonthOnMonthGrowthRate())
                .betweenIfPresent(ReportGroupOrderDetailDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReportGroupOrderDetailDO::getId));
    }

}