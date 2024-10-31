package com.hk.jigai.module.system.controller.admin.operation;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.*;
import java.util.*;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import static com.hk.jigai.framework.common.pojo.CommonResult.success;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationQuestionTypeDO;
import com.hk.jigai.module.system.service.operation.OperationQuestionTypeService;

@Tag(name = "管理后台 - 运维问题类型")
@RestController
@RequestMapping("/operation-question-type")
@Validated
public class OperationQuestionTypeController {

    @Resource
    private OperationQuestionTypeService operationQuestionTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建运维问题类型")
    @PreAuthorize("@ss.hasPermission('hk:operation-question-type:create')")
    public CommonResult<Long> createOperationQuestionType(@Valid @RequestBody OperationQuestionTypeSaveReqVO createReqVO) {
        return success(operationQuestionTypeService.createOperationQuestionType(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新运维问题类型")
    @PreAuthorize("@ss.hasPermission('hk:operation-question-type:update')")
    public CommonResult<Boolean> updateOperationQuestionType(@Valid @RequestBody OperationQuestionTypeSaveReqVO updateReqVO) {
        operationQuestionTypeService.updateOperationQuestionType(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除运维问题类型")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:operation-question-type:delete')")
    public CommonResult<Boolean> deleteOperationQuestionType(@RequestParam("id") Long id) {
        operationQuestionTypeService.deleteOperationQuestionType(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得运维问题类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:operation-question-type:query')")
    public CommonResult<OperationQuestionTypeRespVO> getOperationQuestionType(@RequestParam("id") Long id) {
        OperationQuestionTypeDO operationQuestionType = operationQuestionTypeService.getOperationQuestionType(id);
        return success(BeanUtils.toBean(operationQuestionType, OperationQuestionTypeRespVO.class));
    }

    @GetMapping("/getAll")
    @Operation(summary = "获得运维问题类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//    @PreAuthorize("@ss.hasPermission('hk:operation-question-type:queryAll')")
    public CommonResult<List<OperationQuestionTypeRespVO>> getAllOperationQuestionType(OperationQuestionTypeReqVO req) {
        List<OperationQuestionTypeDO> list = operationQuestionTypeService.queryAll(req);
        return success(BeanUtils.toBean(list, OperationQuestionTypeRespVO.class));
    }

}