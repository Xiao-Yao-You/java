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
import com.hk.jigai.module.system.dal.dataobject.operation.OperationAddressDO;
import com.hk.jigai.module.system.service.operation.OperationAddressService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 运维地点")
@RestController
@RequestMapping("/operation-address")
@Validated
public class OperationAddressController {

    @Resource
    private OperationAddressService operationAddressService;

    @PostMapping("/create")
    @Operation(summary = "创建运维地点")
//    @PreAuthorize("@ss.hasPermission('hk:operation-address:create')")
    public CommonResult<Long> createOperationAddress(@Valid @RequestBody OperationAddressSaveReqVO createReqVO) {
        return success(operationAddressService.createOperationAddress(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新运维地点")
//    @PreAuthorize("@ss.hasPermission('hk:operation-address:update')")
    public CommonResult<Boolean> updateOperationAddress(@Valid @RequestBody OperationAddressSaveReqVO updateReqVO) {
        operationAddressService.updateOperationAddress(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除运维地点")
    @Parameter(name = "id", description = "编号", required = true)
//    @PreAuthorize("@ss.hasPermission('hk:operation-address:delete')")
    public CommonResult<Boolean> deleteOperationAddress(@RequestParam("id") Long id) {
        operationAddressService.deleteOperationAddress(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得运维地点")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('hk:operation-address:query')")
    public CommonResult<OperationAddressRespVO> getOperationAddress(@RequestParam("id") Long id) {
        OperationAddressDO operationAddress = operationAddressService.getOperationAddress(id);
        return success(BeanUtils.toBean(operationAddress, OperationAddressRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得运维地点分页")
//    @PreAuthorize("@ss.hasPermission('hk:operation-address:query')")
    public CommonResult<PageResult<OperationAddressRespVO>> getOperationAddressPage(@Valid OperationAddressPageReqVO pageReqVO) {
        PageResult<OperationAddressDO> pageResult = operationAddressService.getOperationAddressPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OperationAddressRespVO.class));
    }

    @GetMapping("/getAll")
    @Operation(summary = "获得运维地点List")
//    @PreAuthorize("@ss.hasPermission('hk:operation-address:query')")
    public CommonResult<List<OperationAddressRespVO>> getAll(OperationAddressRespVO reqVO) {
        List<OperationAddressDO> list = operationAddressService.getAll(reqVO);
        return success(BeanUtils.toBean(list, OperationAddressRespVO.class));
    }


    @GetMapping("/getAllAddress")
    @Operation(summary = "获得运维地点List")
//    @PreAuthorize("@ss.hasPermission('hk:operation-address:query')")
    public CommonResult<List<OperationAddressRespVO>> getAllAddress(OperationAddressRespVO reqVO) {
        List<OperationAddressDO> list = operationAddressService.getAllAddressForPage(reqVO);
        return success(BeanUtils.toBean(list, OperationAddressRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出运维地点 Excel")
//    @PreAuthorize("@ss.hasPermission('hk:operation-address:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOperationAddressExcel(@Valid OperationAddressPageReqVO pageReqVO,
                                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperationAddressDO> list = operationAddressService.getOperationAddressPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "运维地点.xls", "数据", OperationAddressRespVO.class,
                BeanUtils.toBean(list, OperationAddressRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 空白表单
        List<AddressImportExcelVO> list = Arrays.asList();
        // 输出
        ExcelUtils.write(response, "厂区地点导入模板.xls", "厂区地点", AddressImportExcelVO.class, list);
    }

    @PostMapping("/import-excel")
    @Operation(summary = "设备类型导入")
    public CommonResult<AddressImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                         @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<AddressImportExcelVO> list = ExcelUtils.read(file, AddressImportExcelVO.class);
        return success(operationAddressService.importAddressList(list, updateSupport));
    }

}