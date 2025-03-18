package com.hk.jigai.module.system.controller.admin.reportpersonorder;

import com.hk.jigai.module.system.controller.admin.reportpersonorder.vo.ReportPersonOrderPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportpersonorder.vo.ReportPersonOrderRespVO;
import com.hk.jigai.module.system.controller.admin.reportpersonorder.vo.ReportPersonOrderSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportpersonorder.ReportPersonOrderDO;
import com.hk.jigai.module.system.service.reportpersonorder.ReportPersonOrderService;
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


@Tag(name = "管理后台 - 个人工单处理月报")
@RestController
@RequestMapping("/report-person-order")
@Validated
public class ReportPersonOrderController {

    @Resource
    private ReportPersonOrderService reportPersonOrderService;

    @PostMapping("/generateReport")
    @Operation(summary = "生成报表")
    public CommonResult<List<ReportPersonOrderDO>> generateReport(@RequestParam("month") String month) {
        List<ReportPersonOrderDO> personOrderDOS = reportPersonOrderService.generateReport(month);
        return success(personOrderDOS);
    }


    @PostMapping("/create")
    @Operation(summary = "创建个人工单处理月报")
    @PreAuthorize("@ss.hasPermission('hk:report-person-order:create')")
    public CommonResult<Long> createReportPersonOrder(@Valid @RequestBody ReportPersonOrderSaveReqVO createReqVO) {
        return success(reportPersonOrderService.createReportPersonOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新个人工单处理月报")
    @PreAuthorize("@ss.hasPermission('hk:report-person-order:update')")
    public CommonResult<Boolean> updateReportPersonOrder(@Valid @RequestBody ReportPersonOrderSaveReqVO updateReqVO) {
        reportPersonOrderService.updateReportPersonOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除个人工单处理月报")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:report-person-order:delete')")
    public CommonResult<Boolean> deleteReportPersonOrder(@RequestParam("id") Long id) {
        reportPersonOrderService.deleteReportPersonOrder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得个人工单处理月报")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:report-person-order:query')")
    public CommonResult<ReportPersonOrderRespVO> getReportPersonOrder(@RequestParam("id") Long id) {
        ReportPersonOrderDO reportPersonOrder = reportPersonOrderService.getReportPersonOrder(id);
        return success(BeanUtils.toBean(reportPersonOrder, ReportPersonOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得个人工单处理月报分页")
    @PreAuthorize("@ss.hasPermission('hk:report-person-order:query')")
    public CommonResult<PageResult<ReportPersonOrderRespVO>> getReportPersonOrderPage(@Valid ReportPersonOrderPageReqVO pageReqVO) {
        PageResult<ReportPersonOrderDO> pageResult = reportPersonOrderService.getReportPersonOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportPersonOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出个人工单处理月报 Excel")
    @PreAuthorize("@ss.hasPermission('hk:report-person-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportPersonOrderExcel(@Valid ReportPersonOrderPageReqVO pageReqVO,
                                             HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportPersonOrderDO> list = reportPersonOrderService.getReportPersonOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "个人工单处理月报.xls", "数据", ReportPersonOrderRespVO.class,
                BeanUtils.toBean(list, ReportPersonOrderRespVO.class));
    }

}