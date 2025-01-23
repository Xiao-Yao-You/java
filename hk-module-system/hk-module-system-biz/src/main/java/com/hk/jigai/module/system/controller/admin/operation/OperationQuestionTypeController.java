package com.hk.jigai.module.system.controller.admin.operation;

import com.hk.jigai.framework.excel.core.util.ExcelUtils;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.io.IOException;
import java.util.*;

import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import static com.hk.jigai.framework.common.pojo.CommonResult.success;

import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationQuestionTypeDO;
import com.hk.jigai.module.system.service.operation.OperationQuestionTypeService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 运维问题类型")
@RestController
@RequestMapping("/operation-question-type")
@Validated
public class OperationQuestionTypeController {

    @Resource
    private OperationQuestionTypeService operationQuestionTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建运维问题类型")
    //   @PreAuthorize("@ss.hasPermission('hk:operation-question-type:create')")
    public CommonResult<Long> createOperationQuestionType(@Valid @RequestBody OperationQuestionTypeSaveReqVO createReqVO) {
        return success(operationQuestionTypeService.createOperationQuestionType(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新运维问题类型")
    //   @PreAuthorize("@ss.hasPermission('hk:operation-question-type:update')")
    public CommonResult<Boolean> updateOperationQuestionType(@Valid @RequestBody OperationQuestionTypeSaveReqVO updateReqVO) {
        operationQuestionTypeService.updateOperationQuestionType(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除运维问题类型")
    @Parameter(name = "id", description = "编号", required = true)
    //   @PreAuthorize("@ss.hasPermission('hk:operation-question-type:delete')")
    public CommonResult<Boolean> deleteOperationQuestionType(@RequestParam("id") Long id) {
        operationQuestionTypeService.deleteOperationQuestionType(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得运维问题类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //   @PreAuthorize("@ss.hasPermission('hk:operation-question-type:query')")
    public CommonResult<OperationQuestionTypeRespVO> getOperationQuestionType(@RequestParam("id") Long id) {
        OperationQuestionTypeDO operationQuestionType = operationQuestionTypeService.getOperationQuestionType(id);
        return success(BeanUtils.toBean(operationQuestionType, OperationQuestionTypeRespVO.class));
    }

    @GetMapping("/getAll")
    @Operation(summary = "获得运维问题类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
//     //   @PreAuthorize("@ss.hasPermission('hk:operation-question-type:queryAll')")
    public CommonResult<List<OperationQuestionTypeRespVO>> getAllOperationQuestionType(OperationQuestionTypeReqVO req) {
        List<OperationQuestionTypeDO> list = operationQuestionTypeService.queryAll(req);
        return success(BeanUtils.toBean(list, OperationQuestionTypeRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 空白表单
        List<QuestionTypeImportExcelVO> list = Arrays.asList();
        // 输出
        ExcelUtils.write(response, "问题类型导入模板.xls", "问题类型", QuestionTypeImportExcelVO.class, list);
    }

    @PostMapping("/import-excel")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @Operation(summary = "问题类型导入")
    public CommonResult<QuestionTypeImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                              @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<QuestionTypeImportExcelVO> list = ExcelUtils.read(file, QuestionTypeImportExcelVO.class);
        return success(operationQuestionTypeService.importQuestionTypeList(list, updateSupport));
    }

}