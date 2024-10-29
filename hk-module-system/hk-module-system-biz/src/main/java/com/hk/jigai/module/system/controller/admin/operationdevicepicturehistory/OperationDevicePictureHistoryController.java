package com.hk.jigai.module.system.controller.admin.operationdevicepicturehistory;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.excel.core.util.ExcelUtils;
import com.hk.jigai.module.system.controller.admin.operationdevicepicturehistory.vo.OperationDevicePictureHistoryPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationdevicepicturehistory.vo.OperationDevicePictureHistoryRespVO;
import com.hk.jigai.module.system.controller.admin.operationdevicepicturehistory.vo.OperationDevicePictureHistorySaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicepicturehistory.OperationDevicePictureHistoryDO;
import com.hk.jigai.module.system.service.operationdevicepicturehistory.OperationDevicePictureHistoryService;
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


@Tag(name = "管理后台 - 运维设备照片表_快照")
@RestController
@RequestMapping("/operation-device-picture-history")
@Validated
public class OperationDevicePictureHistoryController {

    @Resource
    private OperationDevicePictureHistoryService operationDevicePictureHistoryService;

    @PostMapping("/create")
    @Operation(summary = "创建运维设备照片表_快照")
    public CommonResult<Long> createOperationDevicePictureHistory(@Valid @RequestBody OperationDevicePictureHistorySaveReqVO createReqVO) {
        return success(operationDevicePictureHistoryService.createOperationDevicePictureHistory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新运维设备照片表_快照")
    public CommonResult<Boolean> updateOperationDevicePictureHistory(@Valid @RequestBody OperationDevicePictureHistorySaveReqVO updateReqVO) {
        operationDevicePictureHistoryService.updateOperationDevicePictureHistory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除运维设备照片表_快照")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOperationDevicePictureHistory(@RequestParam("id") Long id) {
        operationDevicePictureHistoryService.deleteOperationDevicePictureHistory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得运维设备照片表_快照")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<OperationDevicePictureHistoryRespVO> getOperationDevicePictureHistory(@RequestParam("id") Long id) {
        OperationDevicePictureHistoryDO operationDevicePictureHistory = operationDevicePictureHistoryService.getOperationDevicePictureHistory(id);
        return success(BeanUtils.toBean(operationDevicePictureHistory, OperationDevicePictureHistoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得运维设备照片表_快照分页")
    public CommonResult<PageResult<OperationDevicePictureHistoryRespVO>> getOperationDevicePictureHistoryPage(@Valid OperationDevicePictureHistoryPageReqVO pageReqVO) {
        PageResult<OperationDevicePictureHistoryDO> pageResult = operationDevicePictureHistoryService.getOperationDevicePictureHistoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OperationDevicePictureHistoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出运维设备照片表_快照 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOperationDevicePictureHistoryExcel(@Valid OperationDevicePictureHistoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperationDevicePictureHistoryDO> list = operationDevicePictureHistoryService.getOperationDevicePictureHistoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "运维设备照片表_快照.xls", "数据", OperationDevicePictureHistoryRespVO.class,
                        BeanUtils.toBean(list, OperationDevicePictureHistoryRespVO.class));
    }

}