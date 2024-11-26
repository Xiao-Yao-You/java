package com.hk.jigai.module.system.controller.admin.operationdevicemodel;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.excel.core.util.ExcelUtils;
import com.hk.jigai.module.system.controller.admin.operationdevicemodel.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operationdevicemodel.OperationDeviceModelDO;
import com.hk.jigai.module.system.service.operationdevicemodel.OperationDeviceModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.hk.jigai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.hk.jigai.framework.common.pojo.CommonResult.success;


@Tag(name = "管理后台 - 设备型号")
@RestController
@RequestMapping("/operation-device-model")
@Validated
public class OperationDeviceModelController {

    @Resource
    private OperationDeviceModelService operationDeviceModelService;

    @PostMapping("/create")
    @Operation(summary = "创建设备型号")
//    @PreAuthorize("@ss.hasPermission('hk:operation-device-model:create')")
    public CommonResult<Long> createOperationDeviceModel(@Valid @RequestBody OperationDeviceModelSaveReqVO createReqVO) {
        return success(operationDeviceModelService.createOperationDeviceModel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备型号")
//    @PreAuthorize("@ss.hasPermission('hk:operation-device-model:update')")
    public CommonResult<Boolean> updateOperationDeviceModel(@Valid @RequestBody OperationDeviceModelSaveReqVO updateReqVO) {
        operationDeviceModelService.updateOperationDeviceModel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备型号")
    @Parameter(name = "id", description = "编号", required = true)
//    @PreAuthorize("@ss.hasPermission('hk:operation-device-model:delete')")
    public CommonResult<Boolean> deleteOperationDeviceModel(@RequestParam("id") Long id) {
        operationDeviceModelService.deleteOperationDeviceModel(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备型号")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('hk:operation-device-model:query')")
    public CommonResult<OperationDeviceModelRespVO> getOperationDeviceModel(@RequestParam("id") Long id) {
        OperationDeviceModelDO operationDeviceModel = operationDeviceModelService.getOperationDeviceModel(id);
        return success(BeanUtils.toBean(operationDeviceModel, OperationDeviceModelRespVO.class));
    }

    @GetMapping("/getByDeviceTypeId")
    @Operation(summary = "根据设备类型获得设备型号列表")
    public CommonResult<List<OperationDeviceModelDO>> getByDeviceTypeId(@RequestParam("deviceTypeId") Long deviceTypeId) {
        List<OperationDeviceModelDO> operationDeviceModels = operationDeviceModelService.getByDeviceTypeId(deviceTypeId);
        return success(operationDeviceModels);
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备型号分页")
//    @PreAuthorize("@ss.hasPermission('hk:operation-device-model:query')")
    public CommonResult<PageResult<OperationDeviceModelRespVO>> getOperationDeviceModelPage(@Valid OperationDeviceModelPageReqVO pageReqVO) {
        PageResult<OperationDeviceModelDO> pageResult = operationDeviceModelService.getOperationDeviceModelPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OperationDeviceModelRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备型号 Excel")
//    @PreAuthorize("@ss.hasPermission('hk:operation-device-model:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOperationDeviceModelExcel(@Valid OperationDeviceModelPageReqVO pageReqVO,
                                                HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperationDeviceModelDO> list = operationDeviceModelService.getOperationDeviceModelPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备型号.xls", "数据", OperationDeviceModelRespVO.class,
                BeanUtils.toBean(list, OperationDeviceModelRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 空白表单
        List<ModelImportExcelVO> list = Arrays.asList();
        // 输出
        ExcelUtils.write(response, "设备型号导入模板.xls", "设备型号", ModelImportExcelVO.class, list);
    }

    @PostMapping("/import-excel")
    @Operation(summary = "设备型号导入")
    public CommonResult<ModelImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                       @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<ModelImportExcelVO> list = ExcelUtils.read(file, ModelImportExcelVO.class);
        return success(operationDeviceModelService.importModelList(list, updateSupport));
    }

}