package com.hk.jigai.module.system.controller.admin.operation;

import com.hk.jigai.module.system.dal.dataobject.operation.OperationLabelCodeDO;
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
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceTypeDO;
import com.hk.jigai.module.system.service.operation.OperationDeviceTypeService;

@Tag(name = "管理后台 - 运维设备类型")
@RestController
@RequestMapping("/operation-device-type")
@Validated
public class OperationDeviceTypeController {

    @Resource
    private OperationDeviceTypeService operationDeviceTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建运维设备类型")
    //   @PreAuthorize("@ss.hasPermission('hk:operation-device-type:create')")
    public CommonResult<Long> createOperationDeviceType(@Valid @RequestBody OperationDeviceTypeSaveReqVO createReqVO) {
        return success(operationDeviceTypeService.createOperationDeviceType(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新运维设备类型")
    //   @PreAuthorize("@ss.hasPermission('hk:operation-device-type:update')")
    public CommonResult<Boolean> updateOperationDeviceType(@Valid @RequestBody OperationDeviceTypeSaveReqVO updateReqVO) {
        operationDeviceTypeService.updateOperationDeviceType(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除运维设备类型")
    @Parameter(name = "id", description = "编号", required = true)
    //   @PreAuthorize("@ss.hasPermission('hk:operation-device-type:delete')")
    public CommonResult<Boolean> deleteOperationDeviceType(@RequestParam("id") Long id) {
        operationDeviceTypeService.deleteOperationDeviceType(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得运维设备类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //   @PreAuthorize("@ss.hasPermission('hk:operation-device-type:query')")
    public CommonResult<OperationDeviceTypeRespVO> getOperationDeviceType(@RequestParam("id") Long id) {
        OperationDeviceTypeDO operationDeviceType = operationDeviceTypeService.getOperationDeviceType(id);
        return success(BeanUtils.toBean(operationDeviceType, OperationDeviceTypeRespVO.class));
    }

    @GetMapping("/getAll")
    @Operation(summary = "获得运维设备类型")
//    //   @PreAuthorize("@ss.hasPermission('hk:operation-device-type:query')")
    public CommonResult<List<OperationDeviceTypeRespVO>> getAll() {
        List<OperationDeviceTypeDO> list = operationDeviceTypeService.getAll();
        return success(BeanUtils.toBean(list, OperationDeviceTypeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得运维设备类型分页")
    //   @PreAuthorize("@ss.hasPermission('hk:operation-device-type:query')")
    public CommonResult<PageResult<OperationDeviceTypeRespVO>> getOperationDeviceTypePage(@Valid OperationDeviceTypePageReqVO pageReqVO) {
        PageResult<OperationDeviceTypeDO> pageResult = operationDeviceTypeService.getOperationDeviceTypePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OperationDeviceTypeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出运维设备类型 Excel")
    //   @PreAuthorize("@ss.hasPermission('hk:operation-device-type:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOperationDeviceTypeExcel(@Valid OperationDeviceTypePageReqVO pageReqVO,
                                               HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperationDeviceTypeDO> list = operationDeviceTypeService.getOperationDeviceTypePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "运维设备类型.xls", "数据", OperationDeviceTypeRespVO.class,
                BeanUtils.toBean(list, OperationDeviceTypeRespVO.class));
    }

    @GetMapping("/preview")
    @Operation(summary = "预览最新的标签")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //   @PreAuthorize("@ss.hasPermission('hk:operation-label:query')")
    public CommonResult<OperationLabelCodeRespVO> preview(@RequestParam("id") Long id) {
        OperationLabelCodeDO operationLabelCode = operationDeviceTypeService.preview(id);
        return success(BeanUtils.toBean(operationLabelCode, OperationLabelCodeRespVO.class));
    }

    @GetMapping("/batchPrintLabelCode")
    @Operation(summary = "标签批量生成")
    //   @PreAuthorize("@ss.hasPermission('hk:operation-label-code:create')")
    public CommonResult<List<BatchPrintLabelRespVO>> batchPrintLabelCode(OperationBatchCreateLabelReqVO req) {
        return success(operationDeviceTypeService.batchCreateLabelCode(req));
    }

}