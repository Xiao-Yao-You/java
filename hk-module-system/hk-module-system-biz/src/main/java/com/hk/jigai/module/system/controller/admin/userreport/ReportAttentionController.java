package com.hk.jigai.module.system.controller.admin.userreport;

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
import static com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

import com.hk.jigai.module.system.controller.admin.userreport.vo.*;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportAttentionDO;
import com.hk.jigai.module.system.service.userreport.ReportAttentionService;

@Tag(name = "管理后台 - 汇报关注跟进")
@RestController
@RequestMapping("/report-attention")
@Validated
public class ReportAttentionController {

    @Resource
    private ReportAttentionService reportAttentionService;

    @PutMapping("/update")
    @Operation(summary = "更新汇报关注跟进")
    @PreAuthorize("@ss.hasPermission('hk:report-attention:update')")
    public CommonResult<Boolean> updateReportAttention(@Valid @RequestBody ReportAttentionSaveReqVO updateReqVO) {
        reportAttentionService.updateReportAttention(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除汇报关注跟进")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:report-attention:delete')")
    public CommonResult<Boolean> deleteReportAttention(@RequestParam("id") Long id) {
        reportAttentionService.deleteReportAttention(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得汇报关注跟进")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:report-attention:query')")
    public CommonResult<ReportAttentionRespVO> getReportAttention(@RequestParam("id") Long id) {
        ReportAttentionDO reportAttention = reportAttentionService.getReportAttention(id);
        return success(BeanUtils.toBean(reportAttention, ReportAttentionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得汇报关注跟进分页")
    @PreAuthorize("@ss.hasPermission('hk:report-attention:query')")
    public CommonResult<PageResult<ReportAttentionRespVO>> getReportAttentionPage(@Valid ReportAttentionPageReqVO pageReqVO) {
        PageResult<ReportAttentionDO> pageResult = reportAttentionService.getReportAttentionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportAttentionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出汇报关注跟进 Excel")
    @PreAuthorize("@ss.hasPermission('hk:report-attention:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportAttentionExcel(@Valid ReportAttentionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportAttentionDO> list = reportAttentionService.getReportAttentionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "汇报关注跟进.xls", "数据", ReportAttentionRespVO.class,
                        BeanUtils.toBean(list, ReportAttentionRespVO.class));
    }


    @PostMapping("/create")
    @Operation(summary = "关注这个动作")
    @PreAuthorize("@ss.hasPermission('hk:report-attention:create')")
    public CommonResult<Long> createAttention(@Valid @RequestBody CreateAttentionReqVO request) {
        return success(reportAttentionService.createAttention(request));
    }

    @GetMapping("/queryAttentionPage")
    @Operation(summary = "关注列表")
    @PreAuthorize("@ss.hasPermission('hk:report-attention:query')")
    public CommonResult<PageResult<ReportAttentionRespVO>> queryAttentionList(@Valid ReportAttentionPageReqVO pageReqVO) {
        pageReqVO.setUserId(getLoginUserId());
        PageResult<ReportAttentionDO> pageResult = reportAttentionService.getReportAttentionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportAttentionRespVO.class));
    }

    @GetMapping("/queryFollowPage")
    @Operation(summary = "跟进列表")
    @PreAuthorize("@ss.hasPermission('hk:report-follow:query')")
    public CommonResult<PageResult<ReportAttentionRespVO>> queryFollowList(@Valid ReportAttentionPageReqVO pageReqVO) {
        pageReqVO.setReplyUserId(getLoginUserId());
        PageResult<ReportAttentionDO> pageResult = reportAttentionService.getReportAttentionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportAttentionRespVO.class));
    }

    @PutMapping("/transfer")
    @Operation(summary = "转交")
    @PreAuthorize("@ss.hasPermission('hk:report-transfer:update')")
    public CommonResult<Boolean> transfer(@Valid @RequestBody ReportAttentionTransferReqVO updateReqVO) {
        reportAttentionService.transfer(updateReqVO);
        return success(true);
    }

    @PutMapping("/follow")
    @Operation(summary = "跟进")
    @PreAuthorize("@ss.hasPermission('hk:report-follow:update')")
    public CommonResult<Long> follow(@Valid @RequestBody ReportAttentionFollowReqVO updateReqVO) {
        return success(reportAttentionService.follow(updateReqVO));
    }
}