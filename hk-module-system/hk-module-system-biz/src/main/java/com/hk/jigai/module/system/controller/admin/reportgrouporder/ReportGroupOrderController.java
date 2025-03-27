package com.hk.jigai.module.system.controller.admin.reportgrouporder;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.excel.core.util.ExcelUtils;
import com.hk.jigai.module.system.controller.admin.reportgrouporder.vo.GroupReportDetailResultVO;
import com.hk.jigai.module.system.controller.admin.reportgrouporder.vo.ReportGroupOrderPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportgrouporder.vo.ReportGroupOrderRespVO;
import com.hk.jigai.module.system.controller.admin.reportgrouporder.vo.ReportGroupOrderSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportgrouporder.ReportGroupOrderDO;
import com.hk.jigai.module.system.service.reportgrouporder.ReportGroupOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.hk.jigai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.hk.jigai.framework.common.pojo.CommonResult.success;


@Tag(name = "管理后台 - 小组工单处理月报")
@RestController
@RequestMapping("/report-group-order")
@Validated
public class ReportGroupOrderController {

    @Resource
    private ReportGroupOrderService reportGroupOrderService;

    @PostMapping("/generateReport")
    @Operation(summary = "生成报表")
    public CommonResult<List<ReportGroupOrderDO>> generateReport(@RequestParam("month") String month) {
        List<ReportGroupOrderDO> groupReportDOS = reportGroupOrderService.generateReport(month);
        return success(groupReportDOS);
    }

    @PostMapping("/create")
    @Operation(summary = "创建小组工单处理月报")
    @PreAuthorize("@ss.hasPermission('hk:report-group-order:create')")
    public CommonResult<Long> createReportGroupOrder(@Valid @RequestBody ReportGroupOrderSaveReqVO createReqVO) {
        return success(reportGroupOrderService.createReportGroupOrder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新小组工单处理月报")
    @PreAuthorize("@ss.hasPermission('hk:report-group-order:update')")
    public CommonResult<Boolean> updateReportGroupOrder(@Valid @RequestBody ReportGroupOrderSaveReqVO updateReqVO) {
        reportGroupOrderService.updateReportGroupOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除小组工单处理月报")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:report-group-order:delete')")
    public CommonResult<Boolean> deleteReportGroupOrder(@RequestParam("id") Long id) {
        reportGroupOrderService.deleteReportGroupOrder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得小组工单处理月报")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:report-group-order:query')")
    public CommonResult<ReportGroupOrderRespVO> getReportGroupOrder(@RequestParam("id") Long id) {
        ReportGroupOrderDO reportGroupOrder = reportGroupOrderService.getReportGroupOrder(id);
        return success(BeanUtils.toBean(reportGroupOrder, ReportGroupOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得小组工单处理月报分页")
    @PreAuthorize("@ss.hasPermission('hk:report-group-order:query')")
    public CommonResult<PageResult<ReportGroupOrderRespVO>> getReportGroupOrderPage(@Valid ReportGroupOrderPageReqVO pageReqVO) {
        PageResult<ReportGroupOrderDO> pageResult = reportGroupOrderService.getReportGroupOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportGroupOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出小组工单处理月报 Excel")
    @PreAuthorize("@ss.hasPermission('hk:report-group-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportGroupOrderExcel(@Valid ReportGroupOrderPageReqVO pageReqVO,
                                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportGroupOrderDO> list = reportGroupOrderService.getReportGroupOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "小组工单处理月报.xls", "数据", ReportGroupOrderRespVO.class,
                BeanUtils.toBean(list, ReportGroupOrderRespVO.class));
    }

}