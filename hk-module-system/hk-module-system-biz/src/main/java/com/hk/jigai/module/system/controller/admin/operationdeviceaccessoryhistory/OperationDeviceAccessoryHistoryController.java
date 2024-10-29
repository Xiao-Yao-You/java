package com.hk.jigai.module.system.controller.admin.operationdeviceaccessoryhistory;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.excel.core.util.ExcelUtils;
import com.hk.jigai.module.system.controller.admin.operationdeviceaccessoryhistory.vo.OperationDeviceAccessoryHistoryPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationdeviceaccessoryhistory.vo.OperationDeviceAccessoryHistoryRespVO;
import com.hk.jigai.module.system.controller.admin.operationdeviceaccessoryhistory.vo.OperationDeviceAccessoryHistorySaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdeviceaccessoryhistory.OperationDeviceAccessoryHistoryDO;
import com.hk.jigai.module.system.service.operationdeviceaccessoryhistory.OperationDeviceAccessoryHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.hk.jigai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.hk.jigai.framework.common.pojo.CommonResult.success;


@Tag(name = "管理后台 - 运维设备配件表_快照")
@RestController
@RequestMapping("/operation-device-accessory-history")
@Validated
public class OperationDeviceAccessoryHistoryController {

    @Resource
    private OperationDeviceAccessoryHistoryService operationDeviceAccessoryHistoryService;

    @PostMapping("/create")
    @Operation(summary = "创建运维设备配件表_快照")
    public CommonResult<Long> createOperationDeviceAccessoryHistory(@Valid @RequestBody OperationDeviceAccessoryHistorySaveReqVO createReqVO) {
        return success(operationDeviceAccessoryHistoryService.createOperationDeviceAccessoryHistory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新运维设备配件表_快照")
    public CommonResult<Boolean> updateOperationDeviceAccessoryHistory(@Valid @RequestBody OperationDeviceAccessoryHistorySaveReqVO updateReqVO) {
        operationDeviceAccessoryHistoryService.updateOperationDeviceAccessoryHistory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除运维设备配件表_快照")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOperationDeviceAccessoryHistory(@RequestParam("id") Long id) {
        operationDeviceAccessoryHistoryService.deleteOperationDeviceAccessoryHistory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得运维设备配件表_快照")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<OperationDeviceAccessoryHistoryRespVO> getOperationDeviceAccessoryHistory(@RequestParam("id") Long id) {
        OperationDeviceAccessoryHistoryDO operationDeviceAccessoryHistory = operationDeviceAccessoryHistoryService.getOperationDeviceAccessoryHistory(id);
        return success(BeanUtils.toBean(operationDeviceAccessoryHistory, OperationDeviceAccessoryHistoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得运维设备配件表_快照分页")
    public CommonResult<PageResult<OperationDeviceAccessoryHistoryRespVO>> getOperationDeviceAccessoryHistoryPage(@Valid OperationDeviceAccessoryHistoryPageReqVO pageReqVO) {
        PageResult<OperationDeviceAccessoryHistoryDO> pageResult = operationDeviceAccessoryHistoryService.getOperationDeviceAccessoryHistoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OperationDeviceAccessoryHistoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出运维设备配件表_快照 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOperationDeviceAccessoryHistoryExcel(@Valid OperationDeviceAccessoryHistoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperationDeviceAccessoryHistoryDO> list = operationDeviceAccessoryHistoryService.getOperationDeviceAccessoryHistoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "运维设备配件表_快照.xls", "数据", OperationDeviceAccessoryHistoryRespVO.class,
                        BeanUtils.toBean(list, OperationDeviceAccessoryHistoryRespVO.class));
    }

}