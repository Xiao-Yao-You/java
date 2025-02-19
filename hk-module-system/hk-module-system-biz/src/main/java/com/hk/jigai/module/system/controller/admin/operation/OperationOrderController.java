package com.hk.jigai.module.system.controller.admin.operation;

import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderOperateRecordDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
import java.time.LocalTime;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import static com.hk.jigai.framework.common.pojo.CommonResult.success;

import com.hk.jigai.framework.excel.core.util.ExcelUtils;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;

import static com.hk.jigai.framework.apilog.core.enums.OperateTypeEnum.*;

import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderDO;
import com.hk.jigai.module.system.service.operation.OperationOrderService;

@Tag(name = "管理后台 - 工单")
@RestController
@RequestMapping("/operation-order")
@Validated
public class OperationOrderController {

//    @Autowired
//    private OperationOrderController self;

    @Resource
    private OperationOrderService operationOrderService;

    private final Executor asyncExecutor = Executors.newFixedThreadPool(2);

    @PostMapping("/create")
    @Operation(summary = "创建工单")
//    @PreAuthorize("@ss.hasPermission('hk:operation-order:create')")
    public CommonResult<Long> createOperationOrder(@Valid @RequestBody OperationOrderSaveReqVO createReqVO) {

        CreateVO createVO = creatOrder(createReqVO);
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(17, 0);
        boolean isBetween = !now.isBefore(start) && !now.isAfter(end);

        //上午八点-下午五点，发送新工单通知
        if (isBetween && createVO.getOpenIdList().size() > 0) {
            asyncExecutor.execute(() -> {
                sendCreateMsg(createVO);
            });
        }
        return success(createVO.getId());
    }

    public CreateVO creatOrder(OperationOrderSaveReqVO createReqVO) {
        CreateVO createVO = operationOrderService.createOperationOrder(createReqVO);
        return createVO;
    }

    @Async
    public void sendCreateMsg(CreateVO createVO) {
        // 模拟耗时操作
        try {
            Thread.sleep(500);
            operationOrderService.sendCreateMsg(createVO.getOpenIdList(), createVO.getMap());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新工单")
//    @PreAuthorize("@ss.hasPermission('hk:operation-order:update')")
    public CommonResult<Boolean> updateOperationOrder(@Valid @RequestBody OperationOrderSaveReqVO updateReqVO) {
        operationOrderService.updateOperationOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除工单")
    @Parameter(name = "id", description = "编号", required = true)
//    @PreAuthorize("@ss.hasPermission('hk:operation-order:delete')")
    public CommonResult<Boolean> deleteOperationOrder(@RequestParam("id") Long id) {
        operationOrderService.deleteOperationOrder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得工单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('hk:operation-order:query')")
    public CommonResult<OperationOrderRespVO> getOperationOrder(@RequestParam("id") Long id) {
        OperationOrderDO operationOrder = operationOrderService.getOperationOrder(id);
        return success(BeanUtils.toBean(operationOrder, OperationOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得工单分页")
//    @PreAuthorize("@ss.hasPermission('hk:operation-order:query')")
    public CommonResult<PageResult<OperationOrderRespVO>> getOperationOrderPage(@Valid OperationOrderPageReqVO pageReqVO) {
        PageResult<OperationOrderDO> pageResult = operationOrderService.getOperationOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OperationOrderRespVO.class));
    }

    @GetMapping("/pageForApp")
    @Operation(summary = "获得工单分页")
//    @PreAuthorize("@ss.hasPermission('hk:operation-order:query')")
    public CommonResult<PageResult<OperationOrderRespVO>> getOperationOrderPageForApp(@Valid OperationOrderPageReqVO pageReqVO) {
        PageResult<OperationOrderDO> pageResult = operationOrderService.getOperationOrderPageForApp(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OperationOrderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出工单 Excel")
//    @PreAuthorize("@ss.hasPermission('hk:operation-order:export')")


    @ApiAccessLog(operateType = EXPORT)
    public void exportOperationOrderExcel(@Valid OperationOrderPageReqVO pageReqVO,
                                          HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperationOrderDO> list = operationOrderService.getOperationOrderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "工单.xls", "数据", OperationOrderRespExportVO.class,
                BeanUtils.toBean(list, OperationOrderRespExportVO.class));
    }

    @PutMapping("/operateOrder")
    @Operation(summary = "运维团队处理工单")
//    @PreAuthorize("@ss.hasPermission('hk:operation-order:update')")
    public CommonResult<Boolean> operateOrder(@Valid @RequestBody OperationOrderReqVO updateReqVO) {
        operationOrderService.operateOrder(updateReqVO);
        return success(true);
    }

    @PutMapping("/workOrderCirculation")
    @Operation(summary = "工单流转")
//    @PreAuthorize("@ss.hasPermission('hk:operation-order:workOrderCirculation')")
    public CommonResult workOrderCirculation(@Valid @RequestBody OperationOrderReqVO operationOrderReqVO) {
        return operationOrderService.workOrderCirculation(operationOrderReqVO);
    }

    @GetMapping("/getUnDealOrderCount")
    @Operation(summary = "查询未处理的工单数量")
    public CommonResult<Integer> getUnDealOrderCount() {
        return operationOrderService.getUnDealOrderCount();
    }


}