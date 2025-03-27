package com.hk.jigai.module.system.dal.mysql.reportlist;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.reportlist.vo.ReportListPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportlist.ReportListDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 月报表列 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface ReportListMapper extends BaseMapperX<ReportListDO> {

    default PageResult<ReportListDO> selectPage(ReportListPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportListDO>()
                .eqIfPresent(ReportListDO::getReportTitle, reqVO.getReportTitle())
                .eqIfPresent(ReportListDO::getReportMonth, reqVO.getReportMonth())
                .betweenIfPresent(ReportListDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReportListDO::getId));
    }

}