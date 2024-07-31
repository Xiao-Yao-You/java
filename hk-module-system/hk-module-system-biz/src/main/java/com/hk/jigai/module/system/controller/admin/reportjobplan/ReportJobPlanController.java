package com.hk.jigai.module.system.controller.admin.reportjobplan;

import com.hk.jigai.module.system.controller.admin.reportjobplan.vo.ReportJobPlanPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportjobplan.vo.ReportJobPlanRespVO;
import com.hk.jigai.module.system.controller.admin.reportjobplan.vo.ReportJobPlanSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportjobplan.ReportJobPlanDO;
import com.hk.jigai.module.system.service.reportjobplan.ReportJobPlanService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import static com.hk.jigai.framework.common.pojo.CommonResult.success;

import com.hk.jigai.framework.excel.core.util.ExcelUtils;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;
import static com.hk.jigai.framework.apilog.core.enums.OperateTypeEnum.*;


@Tag(name = "管理后台 - 汇报工作计划")
@RestController
@RequestMapping("/hk/report-job-plan")
@Validated
public class ReportJobPlanController {

    @Resource
    private ReportJobPlanService reportJobPlanService;

    @PostMapping("/create")
    @Operation(summary = "创建汇报工作计划")
    @PreAuthorize("@ss.hasPermission('hk:report-job-plan:create')")
    public CommonResult<Long> createReportJobPlan(@Valid @RequestBody ReportJobPlanSaveReqVO createReqVO) {
        return success(reportJobPlanService.createReportJobPlan(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新汇报工作计划")
    @PreAuthorize("@ss.hasPermission('hk:report-job-plan:update')")
    public CommonResult<Boolean> updateReportJobPlan(@Valid @RequestBody ReportJobPlanSaveReqVO updateReqVO) {
        reportJobPlanService.updateReportJobPlan(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除汇报工作计划")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:report-job-plan:delete')")
    public CommonResult<Boolean> deleteReportJobPlan(@RequestParam("id") Long id) {
        reportJobPlanService.deleteReportJobPlan(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得汇报工作计划")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:report-job-plan:query')")
    public CommonResult<ReportJobPlanRespVO> getReportJobPlan(@RequestParam("id") Long id) {
        ReportJobPlanDO reportJobPlan = reportJobPlanService.getReportJobPlan(id);
        return success(BeanUtils.toBean(reportJobPlan, ReportJobPlanRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得汇报工作计划分页")
    @PreAuthorize("@ss.hasPermission('hk:report-job-plan:query')")
    public CommonResult<PageResult<ReportJobPlanRespVO>> getReportJobPlanPage(@Valid ReportJobPlanPageReqVO pageReqVO) {
        PageResult<ReportJobPlanDO> pageResult = reportJobPlanService.getReportJobPlanPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportJobPlanRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出汇报工作计划 Excel")
    @PreAuthorize("@ss.hasPermission('hk:report-job-plan:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportJobPlanExcel(@Valid ReportJobPlanPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportJobPlanDO> list = reportJobPlanService.getReportJobPlanPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "汇报工作计划.xls", "数据", ReportJobPlanRespVO.class,
                        BeanUtils.toBean(list, ReportJobPlanRespVO.class));
    }

}