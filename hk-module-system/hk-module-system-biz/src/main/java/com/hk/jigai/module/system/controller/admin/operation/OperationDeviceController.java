package com.hk.jigai.module.system.controller.admin.operation;

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

import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceDO;
import com.hk.jigai.module.system.service.operation.OperationDeviceService;

@Tag(name = "管理后台 - 运维设备")
@RestController
@RequestMapping("/operation-device")
@Validated
public class OperationDeviceController {

    @Resource
    private OperationDeviceService operationDeviceService;

    @PostMapping("/create")
    @Operation(summary = "创建运维设备")
    @PreAuthorize("@ss.hasPermission('hk:operation-device:create')")
    public CommonResult<Long> createOperationDevice(@Valid @RequestBody OperationDeviceSaveReqVO createReqVO) {
        return success(operationDeviceService.createOperationDevice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新运维设备")
    @PreAuthorize("@ss.hasPermission('hk:operation-device:update')")
    public CommonResult<Boolean> updateOperationDevice(@Valid @RequestBody OperationDeviceSaveReqVO updateReqVO) {
        operationDeviceService.updateOperationDevice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除运维设备")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:operation-device:delete')")
    public CommonResult<Boolean> deleteOperationDevice(@RequestParam("id") Long id) {
        operationDeviceService.deleteOperationDevice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得运维设备")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:operation-device:query')")
    public CommonResult<OperationDeviceRespVO> getOperationDevice(@RequestParam("id") Long id) {
        return success(operationDeviceService.getOperationDevice(id));
    }


    @GetMapping("/getByLabelCode")
    @Operation(summary = "根据标签号获取设备信息")
//    @PreAuthorize("@ss.hasPermission('hk:operation-device:query')")
    public CommonResult<OperationDeviceRespVO> getOperationDeviceByLabelCode(@RequestParam("labelCode") String labelCode) {
        return success(operationDeviceService.getOperationDeviceByLabelCode(labelCode));
    }

    @GetMapping("/page")
    @Operation(summary = "获得运维设备分页")
    @PreAuthorize("@ss.hasPermission('hk:operation-device:query')")
    public CommonResult<PageResult<OperationDeviceRespVO>> getOperationDevicePage(@Valid OperationDevicePageReqVO pageReqVO) {
        PageResult<OperationDeviceDO> pageResult = operationDeviceService.getOperationDevicePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OperationDeviceRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出运维设备 Excel")
    @PreAuthorize("@ss.hasPermission('hk:operation-device:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOperationDeviceExcel(@Valid OperationDevicePageReqVO pageReqVO,
                                           HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperationDeviceDO> list = operationDeviceService.getOperationDevicePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "运维设备.xls", "数据", OperationDeviceRespVO.class,
                BeanUtils.toBean(list, OperationDeviceRespVO.class));
    }

    @PutMapping("/register")
    @Operation(summary = "设备分配")
    @PreAuthorize("@ss.hasPermission('hk:operation-device:update')")
    public CommonResult<Boolean> register(@Valid @RequestBody OperationDeviceRegisterReqVO registerReqVO) {
        operationDeviceService.register(registerReqVO);
        return success(true);
    }

    @PutMapping("/scrap")
    @Operation(summary = "设备报废")
    @PreAuthorize("@ss.hasPermission('hk:operation-device:update')")
    public CommonResult<Boolean> scrap(@Valid @RequestBody OperationDeviceScrapReqVO scrapReqVO) {
        operationDeviceService.scrap(scrapReqVO);
        return success(true);
    }

    @GetMapping("/getUseableLabelCode")
    public CommonResult<List<OperationLabelCodeRespVO>> getUseableLabelCode() {
        return operationDeviceService.getUseableLabelCode();
    }
}