package com.hk.jigai.module.system.controller.admin.reportlist;

import com.hk.jigai.module.system.controller.admin.reportlist.vo.ReportListPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportlist.vo.ReportListRespVO;
import com.hk.jigai.module.system.controller.admin.reportlist.vo.ReportListSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportlist.ReportListDO;
import com.hk.jigai.module.system.service.reportlist.ReportListService;
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


@Tag(name = "管理后台 - 月报表列")
@RestController
@RequestMapping("/report-list")
@Validated
public class ReportListController {

    @Resource
    private ReportListService reportListService;

    @PostMapping("/create")
    @Operation(summary = "创建月报表列")
    @PreAuthorize("@ss.hasPermission('hk:report-list:create')")
    public CommonResult<Long> createReportList(@Valid @RequestBody ReportListSaveReqVO createReqVO) {
        return success(reportListService.createReportList(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新月报表列")
    @PreAuthorize("@ss.hasPermission('hk:report-list:update')")
    public CommonResult<Boolean> updateReportList(@Valid @RequestBody ReportListSaveReqVO updateReqVO) {
        reportListService.updateReportList(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除月报表列")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:report-list:delete')")
    public CommonResult<Boolean> deleteReportList(@RequestParam("id") Long id) {
        reportListService.deleteReportList(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得月报表列")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:report-list:query')")
    public CommonResult<ReportListRespVO> getReportList(@RequestParam("id") Long id) {
        ReportListDO reportList = reportListService.getReportList(id);
        return success(BeanUtils.toBean(reportList, ReportListRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得月报表列分页")
    @PreAuthorize("@ss.hasPermission('hk:report-list:query')")
    public CommonResult<PageResult<ReportListRespVO>> getReportListPage(@Valid ReportListPageReqVO pageReqVO) {
        PageResult<ReportListDO> pageResult = reportListService.getReportListPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportListRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出月报表列 Excel")
    @PreAuthorize("@ss.hasPermission('hk:report-list:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportListExcel(@Valid ReportListPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportListDO> list = reportListService.getReportListPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "月报表列.xls", "数据", ReportListRespVO.class,
                        BeanUtils.toBean(list, ReportListRespVO.class));
    }

}