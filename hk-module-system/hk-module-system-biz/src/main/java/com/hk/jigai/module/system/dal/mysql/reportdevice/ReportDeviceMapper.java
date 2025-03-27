package com.hk.jigai.module.system.dal.mysql.reportdevice;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.reportdevice.vo.ReportDevicePageReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportdevice.ReportDeviceDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备资产报 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface ReportDeviceMapper extends BaseMapperX<ReportDeviceDO> {

    default PageResult<ReportDeviceDO> selectPage(ReportDevicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportDeviceDO>()
                .eqIfPresent(ReportDeviceDO::getReportMonth, reqVO.getReportMonth())
                .eqIfPresent(ReportDeviceDO::getCompanyId, reqVO.getCompanyId())
                .likeIfPresent(ReportDeviceDO::getCompanyName, reqVO.getCompanyName())
                .eqIfPresent(ReportDeviceDO::getDeviceType, reqVO.getDeviceType())
                .eqIfPresent(ReportDeviceDO::getDeviceCount, reqVO.getDeviceCount())
                .eqIfPresent(ReportDeviceDO::getStateDistribution, reqVO.getStateDistribution())
                .eqIfPresent(ReportDeviceDO::getEffectDistribution, reqVO.getEffectDistribution())
                .eqIfPresent(ReportDeviceDO::getDeptDistribution, reqVO.getDeptDistribution())
                .eqIfPresent(ReportDeviceDO::getFailureRate, reqVO.getFailureRate())
                .betweenIfPresent(ReportDeviceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReportDeviceDO::getId));
    }

}