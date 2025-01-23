package com.hk.jigai.module.system.controller.admin.reasonablesuggestion;

import com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo.ReasonableSuggestionPageReqVO;
import com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo.ReasonableSuggestionRespVO;
import com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo.ReasonableSuggestionSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reasonablesuggestion.ReasonableSuggestionDO;
import com.hk.jigai.module.system.service.reasonablesuggestion.ReasonableSuggestionService;
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
import javax.xml.ws.Service;
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


@Tag(name = "管理后台 - 合理化建议")
@RestController
@RequestMapping("/reasonableSuggestion")
@Validated
public class ReasonableSuggestionController {

    @Resource
    private ReasonableSuggestionService reasonableSuggestionService;

    @PostMapping("/create")
    @Operation(summary = "创建合理化建议")
    @PreAuthorize("@ss.hasPermission('reasonableSuggestion::create')")
    public CommonResult<Long> create(@Valid @RequestBody ReasonableSuggestionSaveReqVO createReqVO) {
        return success(reasonableSuggestionService.create(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新合理化建议")
    @PreAuthorize("@ss.hasPermission('reasonableSuggestion::update')")
    public CommonResult<Boolean> update(@Valid @RequestBody ReasonableSuggestionSaveReqVO updateReqVO) {
        reasonableSuggestionService.update(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除合理化建议")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('reasonableSuggestion::delete')")
    public CommonResult<Boolean> delete(@RequestParam("id") Long id) {
        reasonableSuggestionService.delete(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得合理化建议")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('reasonableSuggestion::query')")
    public CommonResult<ReasonableSuggestionRespVO> get(@RequestParam("id") Long id) {
        ReasonableSuggestionDO reasonableSuggestionDO = reasonableSuggestionService.get(id);
        return success(BeanUtils.toBean(reasonableSuggestionDO, ReasonableSuggestionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得合理化建议分页")
    @PreAuthorize("@ss.hasPermission('reasonableSuggestion::query')")
    public CommonResult<PageResult<ReasonableSuggestionRespVO>> getPage(@Valid ReasonableSuggestionPageReqVO pageReqVO) {
        PageResult<ReasonableSuggestionDO> pageResult = reasonableSuggestionService.getPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReasonableSuggestionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出合理化建议 Excel")
    @PreAuthorize("@ss.hasPermission('reasonableSuggestion::export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportExcel(@Valid ReasonableSuggestionPageReqVO pageReqVO,
                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReasonableSuggestionDO> list = reasonableSuggestionService.getPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "合理化建议.xls", "数据", ReasonableSuggestionRespVO.class,
                BeanUtils.toBean(list, ReasonableSuggestionRespVO.class));
    }

}