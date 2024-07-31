package com.hk.jigai.module.system.controller.admin.reportjobschedule;

import com.hk.jigai.module.system.controller.admin.reportjobschedule.vo.ReportJobSchedulePageReqVO;
import com.hk.jigai.module.system.controller.admin.reportjobschedule.vo.ReportJobScheduleRespVO;
import com.hk.jigai.module.system.controller.admin.reportjobschedule.vo.ReportJobScheduleSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportjobschedule.ReportJobScheduleDO;
import com.hk.jigai.module.system.service.reportjobschedule.ReportJobScheduleService;
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


@Tag(name = "管理后台 - 汇报工作进度")
@RestController
@RequestMapping("/hk/report-job-schedule")
@Validated
public class ReportJobScheduleController {

    @Resource
    private ReportJobScheduleService reportJobScheduleService;

    @PostMapping("/create")
    @Operation(summary = "创建汇报工作进度")
    @PreAuthorize("@ss.hasPermission('hk:report-job-schedule:create')")
    public CommonResult<Long> createReportJobSchedule(@Valid @RequestBody ReportJobScheduleSaveReqVO createReqVO) {
        return success(reportJobScheduleService.createReportJobSchedule(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新汇报工作进度")
    @PreAuthorize("@ss.hasPermission('hk:report-job-schedule:update')")
    public CommonResult<Boolean> updateReportJobSchedule(@Valid @RequestBody ReportJobScheduleSaveReqVO updateReqVO) {
        reportJobScheduleService.updateReportJobSchedule(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除汇报工作进度")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:report-job-schedule:delete')")
    public CommonResult<Boolean> deleteReportJobSchedule(@RequestParam("id") Long id) {
        reportJobScheduleService.deleteReportJobSchedule(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得汇报工作进度")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:report-job-schedule:query')")
    public CommonResult<ReportJobScheduleRespVO> getReportJobSchedule(@RequestParam("id") Long id) {
        ReportJobScheduleDO reportJobSchedule = reportJobScheduleService.getReportJobSchedule(id);
        return success(BeanUtils.toBean(reportJobSchedule, ReportJobScheduleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得汇报工作进度分页")
    @PreAuthorize("@ss.hasPermission('hk:report-job-schedule:query')")
    public CommonResult<PageResult<ReportJobScheduleRespVO>> getReportJobSchedulePage(@Valid ReportJobSchedulePageReqVO pageReqVO) {
        PageResult<ReportJobScheduleDO> pageResult = reportJobScheduleService.getReportJobSchedulePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportJobScheduleRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出汇报工作进度 Excel")
    @PreAuthorize("@ss.hasPermission('hk:report-job-schedule:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportJobScheduleExcel(@Valid ReportJobSchedulePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportJobScheduleDO> list = reportJobScheduleService.getReportJobSchedulePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "汇报工作进度.xls", "数据", ReportJobScheduleRespVO.class,
                        BeanUtils.toBean(list, ReportJobScheduleRespVO.class));
    }

}