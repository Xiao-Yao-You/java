package com.hk.jigai.module.system.controller.admin.reportdevice;

import com.hk.jigai.module.system.controller.admin.reportdevice.vo.ReportDevicePageReqVO;
import com.hk.jigai.module.system.controller.admin.reportdevice.vo.ReportDeviceRespVO;
import com.hk.jigai.module.system.controller.admin.reportdevice.vo.ReportDeviceSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportdevice.ReportDeviceDO;
import com.hk.jigai.module.system.dal.dataobject.reportgrouporder.ReportGroupOrderDO;
import com.hk.jigai.module.system.service.reportdevice.ReportDeviceService;
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


@Tag(name = "管理后台 - 设备资产报")
@RestController
@RequestMapping("/report-device")
@Validated
public class ReportDeviceController {

    @Resource
    private ReportDeviceService reportDeviceService;

    @PostMapping("/generateReport")
    @Operation(summary = "生成报表")
    public CommonResult<List<ReportDeviceDO>> generateReport(@RequestParam("month") String month) {
        List<ReportDeviceDO> reportDeviceDOList = reportDeviceService.generateReport(month);
        return success(reportDeviceDOList);
    }


    @PostMapping("/create")
    @Operation(summary = "创建设备资产报")
    @PreAuthorize("@ss.hasPermission('hk:report-device:create')")
    public CommonResult<Long> createReportDevice(@Valid @RequestBody ReportDeviceSaveReqVO createReqVO) {
        return success(reportDeviceService.createReportDevice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备资产报")
    @PreAuthorize("@ss.hasPermission('hk:report-device:update')")
    public CommonResult<Boolean> updateReportDevice(@Valid @RequestBody ReportDeviceSaveReqVO updateReqVO) {
        reportDeviceService.updateReportDevice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备资产报")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:report-device:delete')")
    public CommonResult<Boolean> deleteReportDevice(@RequestParam("id") Long id) {
        reportDeviceService.deleteReportDevice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备资产报")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:report-device:query')")
    public CommonResult<ReportDeviceRespVO> getReportDevice(@RequestParam("id") Long id) {
        ReportDeviceDO reportDevice = reportDeviceService.getReportDevice(id);
        return success(BeanUtils.toBean(reportDevice, ReportDeviceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备资产报分页")
    @PreAuthorize("@ss.hasPermission('hk:report-device:query')")
    public CommonResult<PageResult<ReportDeviceRespVO>> getReportDevicePage(@Valid ReportDevicePageReqVO pageReqVO) {
        PageResult<ReportDeviceDO> pageResult = reportDeviceService.getReportDevicePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportDeviceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备资产报 Excel")
    @PreAuthorize("@ss.hasPermission('hk:report-device:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportDeviceExcel(@Valid ReportDevicePageReqVO pageReqVO,
                                        HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportDeviceDO> list = reportDeviceService.getReportDevicePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备资产报.xls", "数据", ReportDeviceRespVO.class,
                BeanUtils.toBean(list, ReportDeviceRespVO.class));
    }

}