package com.hk.jigai.module.system.controller.admin.scenecode;

import com.hk.jigai.framework.common.enums.CommonStatusEnum;
import com.hk.jigai.module.system.controller.admin.user.vo.user.UserImportExcelVO;
import com.hk.jigai.module.system.enums.common.SexEnum;
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

import com.hk.jigai.module.system.controller.admin.scenecode.vo.*;
import com.hk.jigai.module.system.dal.dataobject.scenecode.SceneCodeDO;
import com.hk.jigai.module.system.service.scenecode.SceneCodeService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 单据编码类型配置")
@RestController
@RequestMapping("/system/scene-code")
@Validated
public class SceneCodeController {

    @Resource
    private SceneCodeService sceneCodeService;

    @PostMapping("/create")
    @Operation(summary = "创建单据编码类型配置")
    @PreAuthorize("@ss.hasPermission('system:scene-code:create')")
    public CommonResult<Integer> createSceneCode(@Valid @RequestBody SceneCodeSaveReqVO createReqVO) {
        return success(sceneCodeService.createSceneCode(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新单据编码类型配置")
    @PreAuthorize("@ss.hasPermission('system:scene-code:update')")
    public CommonResult<Boolean> updateSceneCode(@Valid @RequestBody SceneCodeSaveReqVO updateReqVO) {
        sceneCodeService.updateSceneCode(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除单据编码类型配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:scene-code:delete')")
    public CommonResult<Boolean> deleteSceneCode(@RequestParam("id") Integer id) {
        sceneCodeService.deleteSceneCode(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得单据编码类型配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:scene-code:query')")
    public CommonResult<SceneCodeRespVO> getSceneCode(@RequestParam("id") Integer id) {
        SceneCodeDO sceneCode = sceneCodeService.getSceneCode(id);
        return success(BeanUtils.toBean(sceneCode, SceneCodeRespVO.class));
    }

    @GetMapping("/getAll")
    @Operation(summary = "获得单据编码类型配置列表")
//    @PreAuthorize("@ss.hasPermission('system:scene-code:query')")
    public CommonResult<List<SceneCodeRespVO>> getSceneCodePage() {
        List<SceneCodeDO> list = sceneCodeService.getSceneCodeList();
        return success(BeanUtils.toBean(list, SceneCodeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得单据编码类型配置分页")
    @PreAuthorize("@ss.hasPermission('system:scene-code:query')")
    public CommonResult<PageResult<SceneCodeRespVO>> getSceneCodePage(@Valid SceneCodePageReqVO pageReqVO) {
        PageResult<SceneCodeDO> pageResult = sceneCodeService.getSceneCodePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SceneCodeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出单据编码类型配置 Excel")
    @PreAuthorize("@ss.hasPermission('system:scene-code:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSceneCodeExcel(@Valid SceneCodePageReqVO pageReqVO,
                                     HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SceneCodeDO> list = sceneCodeService.getSceneCodePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "单据编码类型配置.xls", "数据", SceneCodeRespVO.class,
                BeanUtils.toBean(list, SceneCodeRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 空白表单
        List<SceneCodeImportExcelVO> list = Arrays.asList();
        // 输出
        ExcelUtils.write(response, "单据编码模板.xls", "单据编码", SceneCodeImportExcelVO.class, list);
    }

    @PostMapping("/import-excel")
    @Operation(summary = "单据编码导入")
    public CommonResult<SceneCodeImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                            @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<SceneCodeImportExcelVO> list = ExcelUtils.read(file, SceneCodeImportExcelVO.class);
        return success(sceneCodeService.importSceneCodeList(list, updateSupport));
    }

}