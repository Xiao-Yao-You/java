package com.hk.jigai.module.system.controller.admin.reportgrouporderdetail;

import com.hk.jigai.module.system.controller.admin.reportgrouporderdetail.vo.ReportGroupOrderDetailPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportgrouporderdetail.vo.ReportGroupOrderDetailRespVO;
import com.hk.jigai.module.system.controller.admin.reportgrouporderdetail.vo.ReportGroupOrderDetailSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportgrouporderdetail.ReportGroupOrderDetailDO;
import com.hk.jigai.module.system.service.reportgrouporderdetail.ReportGroupOrderDetailService;
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


@Tag(name = "管理后台 - 小组工单处理月报详情")
@RestController
@RequestMapping("/report-group-order-detail")
@Validated
public class ReportGroupOrderDetailController {

    @Resource
    private ReportGroupOrderDetailService reportGroupOrderDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建小组工单处理月报详情")
    @PreAuthorize("@ss.hasPermission('hk:report-group-order-detail:create')")
    public CommonResult<Long> createReportGroupOrderDetail(@Valid @RequestBody ReportGroupOrderDetailSaveReqVO createReqVO) {
        return success(reportGroupOrderDetailService.createReportGroupOrderDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新小组工单处理月报详情")
    @PreAuthorize("@ss.hasPermission('hk:report-group-order-detail:update')")
    public CommonResult<Boolean> updateReportGroupOrderDetail(@Valid @RequestBody ReportGroupOrderDetailSaveReqVO updateReqVO) {
        reportGroupOrderDetailService.updateReportGroupOrderDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除小组工单处理月报详情")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:report-group-order-detail:delete')")
    public CommonResult<Boolean> deleteReportGroupOrderDetail(@RequestParam("id") Long id) {
        reportGroupOrderDetailService.deleteReportGroupOrderDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得小组工单处理月报详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:report-group-order-detail:query')")
    public CommonResult<ReportGroupOrderDetailRespVO> getReportGroupOrderDetail(@RequestParam("id") Long id) {
        ReportGroupOrderDetailDO reportGroupOrderDetail = reportGroupOrderDetailService.getReportGroupOrderDetail(id);
        return success(BeanUtils.toBean(reportGroupOrderDetail, ReportGroupOrderDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得小组工单处理月报详情分页")
    @PreAuthorize("@ss.hasPermission('hk:report-group-order-detail:query')")
    public CommonResult<PageResult<ReportGroupOrderDetailRespVO>> getReportGroupOrderDetailPage(@Valid ReportGroupOrderDetailPageReqVO pageReqVO) {
        PageResult<ReportGroupOrderDetailDO> pageResult = reportGroupOrderDetailService.getReportGroupOrderDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportGroupOrderDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出小组工单处理月报详情 Excel")
    @PreAuthorize("@ss.hasPermission('hk:report-group-order-detail:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportGroupOrderDetailExcel(@Valid ReportGroupOrderDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportGroupOrderDetailDO> list = reportGroupOrderDetailService.getReportGroupOrderDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "小组工单处理月报详情.xls", "数据", ReportGroupOrderDetailRespVO.class,
                        BeanUtils.toBean(list, ReportGroupOrderDetailRespVO.class));
    }

}