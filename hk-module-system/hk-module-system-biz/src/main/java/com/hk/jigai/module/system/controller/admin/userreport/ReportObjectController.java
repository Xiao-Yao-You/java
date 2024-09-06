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

import com.hk.jigai.module.system.controller.admin.userreport.vo.*;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportObjectDO;
import com.hk.jigai.module.system.service.userreport.ReportObjectService;

@Tag(name = "管理后台 - 汇报对象")
@RestController
@RequestMapping("/report-object")
@Validated
public class ReportObjectController {

    @Resource
    private ReportObjectService reportObjectService;

    @PostMapping("/batchCreate")
    @Operation(summary = "创建汇报对象")
    @PreAuthorize("@ss.hasPermission('hk:report-object:create')")
    public CommonResult<Boolean> batchCreate(@Valid @RequestBody List<ReportObjectSaveReqVO> createReqVO) {
        reportObjectService.createReportObject(createReqVO);
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得汇报对象分页")
    @PreAuthorize("@ss.hasPermission('hk:report-object:query')")
    public CommonResult<List<ReportObjectRespVO>> getReportObjectPage(@Valid ReportObjectPageReqVO pageReqVO) {
        List<ReportObjectDO> pageResult = reportObjectService.getReportObjectPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportObjectRespVO.class));
    }

}