package com.hk.jigai.module.system.controller.admin.operationnoticeobject;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.excel.core.util.ExcelUtils;
import com.hk.jigai.module.system.controller.admin.operationnoticeobject.vo.OperationNoticeObjectPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationnoticeobject.vo.OperationNoticeObjectRespVO;
import com.hk.jigai.module.system.controller.admin.operationnoticeobject.vo.OperationNoticeObjectSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationnoticeobject.OperationNoticeObjectDO;
import com.hk.jigai.module.system.service.operationnoticeobject.OperationNoticeObjectService;
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

@Tag(name = "管理后台 - 消息通知对象设置")
@RestController
@RequestMapping("/operation-notice-object")
@Validated
public class OperationNoticeObjectController {

    @Resource
    private OperationNoticeObjectService operationNoticeObjectService;

    @PostMapping("/create")
    @Operation(summary = "创建消息通知对象设置")
    public CommonResult<Boolean> createOperationNoticeObject(@Valid @RequestBody OperationNoticeObjectSaveReqVO createReqVO) {
        return success(operationNoticeObjectService.createOperationNoticeObject(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新消息通知对象设置")
    public CommonResult<Boolean> updateOperationNoticeObject(@Valid @RequestBody OperationNoticeObjectSaveReqVO updateReqVO) {
        operationNoticeObjectService.updateOperationNoticeObject(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除消息通知对象设置")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteOperationNoticeObject(@RequestParam("id") Long id) {
        operationNoticeObjectService.deleteOperationNoticeObject(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得消息通知对象设置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<OperationNoticeObjectRespVO> getOperationNoticeObject(@RequestParam("id") Long id) {
        OperationNoticeObjectDO operationNoticeObject = operationNoticeObjectService.getOperationNoticeObject(id);
        return success(BeanUtils.toBean(operationNoticeObject, OperationNoticeObjectRespVO.class));
    }

    @GetMapping("/getAllUser")
    public CommonResult<List<OperationNoticeObjectDO>> getAllUsers() {
        return success(operationNoticeObjectService.getAllUsers());
    }


    @GetMapping("/page")
    public CommonResult<PageResult<OperationNoticeObjectRespVO>> getOperationNoticeObjectPage(@Valid OperationNoticeObjectPageReqVO pageReqVO) {
        PageResult<OperationNoticeObjectDO> pageResult = operationNoticeObjectService.getOperationNoticeObjectPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OperationNoticeObjectRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出消息通知对象设置 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOperationNoticeObjectExcel(@Valid OperationNoticeObjectPageReqVO pageReqVO,
                                                 HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperationNoticeObjectDO> list = operationNoticeObjectService.getOperationNoticeObjectPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "消息通知对象设置.xls", "数据", OperationNoticeObjectRespVO.class,
                BeanUtils.toBean(list, OperationNoticeObjectRespVO.class));
    }

}