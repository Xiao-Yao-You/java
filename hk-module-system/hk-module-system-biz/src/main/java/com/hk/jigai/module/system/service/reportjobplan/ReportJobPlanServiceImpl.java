package com.hk.jigai.module.system.service.reportjobplan;

import com.hk.jigai.module.system.controller.admin.reportjobplan.vo.ReportJobPlanPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportjobplan.vo.ReportJobPlanSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportjobplan.ReportJobPlanDO;
import com.hk.jigai.module.system.dal.mysql.reportjobplan.ReportJobPlanMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.REPORT_JOB_PLAN_NOT_EXISTS;

/**
 * 汇报工作计划 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class ReportJobPlanServiceImpl implements ReportJobPlanService {

    @Resource
    private ReportJobPlanMapper reportJobPlanMapper;

    @Override
    public Long createReportJobPlan(ReportJobPlanSaveReqVO createReqVO) {
        // 插入
        ReportJobPlanDO reportJobPlan = BeanUtils.toBean(createReqVO, ReportJobPlanDO.class);
        reportJobPlanMapper.insert(reportJobPlan);
        // 返回
        return reportJobPlan.getId();
    }

    @Override
    public void updateReportJobPlan(ReportJobPlanSaveReqVO updateReqVO) {
        // 校验存在
        validateReportJobPlanExists(updateReqVO.getId());
        // 更新
        ReportJobPlanDO updateObj = BeanUtils.toBean(updateReqVO, ReportJobPlanDO.class);
        reportJobPlanMapper.updateById(updateObj);
    }

    @Override
    public void deleteReportJobPlan(Long id) {
        // 校验存在
        validateReportJobPlanExists(id);
        // 删除
        reportJobPlanMapper.deleteById(id);
    }

    private void validateReportJobPlanExists(Long id) {
        if (reportJobPlanMapper.selectById(id) == null) {
            throw exception(REPORT_JOB_PLAN_NOT_EXISTS);
        }
    }

    @Override
    public ReportJobPlanDO getReportJobPlan(Long id) {
        return reportJobPlanMapper.selectById(id);
    }

    @Override
    public PageResult<ReportJobPlanDO> getReportJobPlanPage(ReportJobPlanPageReqVO pageReqVO) {
        return reportJobPlanMapper.selectPage(pageReqVO);
    }

}