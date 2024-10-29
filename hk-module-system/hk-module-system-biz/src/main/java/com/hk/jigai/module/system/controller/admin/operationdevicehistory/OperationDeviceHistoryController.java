package com.hk.jigai.module.system.controller.admin.operationdevicehistory;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.excel.core.util.ExcelUtils;
import com.hk.jigai.module.system.controller.admin.operationdevicehistory.vo.OperationDeviceHistoryPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationdevicehistory.vo.OperationDeviceHistoryRespVO;
import com.hk.jigai.module.system.controller.admin.operationdevicehistory.vo.OperationDeviceHistorySaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicehistory.OperationDeviceHistoryDO;
import com.hk.jigai.module.system.service.operationdevicehistory.OperationDeviceHistoryService;
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


@Tag(name = "管理后台 - 运维设备")
@RestController
@RequestMapping("/operation-device-history")
@Validated
public class OperationDeviceHistoryController {

    @Resource
    private OperationDeviceHistoryService operationDeviceHistoryService;

    @PostMapping("/create")
    @Operation(summary = "创建运维设备")
    @PreAuthorize("@ss.hasPermission('hk:operation-device-history:create')")
    public CommonResult<Long> createOperationDeviceHistory(@Valid @RequestBody OperationDeviceHistorySaveReqVO createReqVO) {
        return success(operationDeviceHistoryService.createOperationDeviceHistory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新运维设备")
    @PreAuthorize("@ss.hasPermission('hk:operation-device-history:update')")
    public CommonResult<Boolean> updateOperationDeviceHistory(@Valid @RequestBody OperationDeviceHistorySaveReqVO updateReqVO) {
        operationDeviceHistoryService.updateOperationDeviceHistory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除运维设备")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:operation-device-history:delete')")
    public CommonResult<Boolean> deleteOperationDeviceHistory(@RequestParam("id") Long id) {
        operationDeviceHistoryService.deleteOperationDeviceHistory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得运维设备")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:operation-device-history:query')")
    public CommonResult<OperationDeviceHistoryRespVO> getOperationDeviceHistory(@RequestParam("id") Long id) {
        OperationDeviceHistoryDO operationDeviceHistory = operationDeviceHistoryService.getOperationDeviceHistory(id);
        return success(BeanUtils.toBean(operationDeviceHistory, OperationDeviceHistoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得运维设备分页")
    @PreAuthorize("@ss.hasPermission('hk:operation-device-history:query')")
    public CommonResult<PageResult<OperationDeviceHistoryRespVO>> getOperationDeviceHistoryPage(@Valid OperationDeviceHistoryPageReqVO pageReqVO) {
        PageResult<OperationDeviceHistoryDO> pageResult = operationDeviceHistoryService.getOperationDeviceHistoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OperationDeviceHistoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出运维设备 Excel")
    @PreAuthorize("@ss.hasPermission('hk:operation-device-history:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOperationDeviceHistoryExcel(@Valid OperationDeviceHistoryPageReqVO pageReqVO,
                                                  HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperationDeviceHistoryDO> list = operationDeviceHistoryService.getOperationDeviceHistoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "运维设备.xls", "数据", OperationDeviceHistoryRespVO.class,
                BeanUtils.toBean(list, OperationDeviceHistoryRespVO.class));
    }

}