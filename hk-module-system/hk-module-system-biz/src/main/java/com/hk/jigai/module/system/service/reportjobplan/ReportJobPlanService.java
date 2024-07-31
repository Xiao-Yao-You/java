package com.hk.jigai.module.system.service.reportjobplan;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.reportjobplan.vo.ReportJobPlanPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportjobplan.vo.ReportJobPlanSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportjobplan.ReportJobPlanDO;

/**
 * 汇报工作计划 Service 接口
 *
 * @author 超级管理员
 */
public interface ReportJobPlanService {

    /**
     * 创建汇报工作计划
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReportJobPlan(@Valid ReportJobPlanSaveReqVO createReqVO);

    /**
     * 更新汇报工作计划
     *
     * @param updateReqVO 更新信息
     */
    void updateReportJobPlan(@Valid ReportJobPlanSaveReqVO updateReqVO);

    /**
     * 删除汇报工作计划
     *
     * @param id 编号
     */
    void deleteReportJobPlan(Long id);

    /**
     * 获得汇报工作计划
     *
     * @param id 编号
     * @return 汇报工作计划
     */
    ReportJobPlanDO getReportJobPlan(Long id);

    /**
     * 获得汇报工作计划分页
     *
     * @param pageReqVO 分页查询
     * @return 汇报工作计划分页
     */
    PageResult<ReportJobPlanDO> getReportJobPlanPage(ReportJobPlanPageReqVO pageReqVO);

}