package com.hk.jigai.module.system.controller.admin.mapcoordinateinfo;

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

import com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo.*;
import com.hk.jigai.module.system.dal.dataobject.mapcoordinateinfo.MapFeedbackDO;
import com.hk.jigai.module.system.service.mapcoordinateinfo.MapFeedbackService;

@Tag(name = "管理后台 - 厂区地图反馈")
@RestController
@RequestMapping("/map-feedback")
@Validated
public class MapFeedbackController {

    @Resource
    private MapFeedbackService mapFeedbackService;

    @PostMapping("/create")
    @Operation(summary = "创建厂区地图反馈")
    @PreAuthorize("@ss.hasPermission('hk:map-feedback:create')")
    public CommonResult<Integer> createMapFeedback(@Valid @RequestBody MapFeedbackSaveReqVO createReqVO) {
        return success(mapFeedbackService.createMapFeedback(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新厂区地图反馈")
    @PreAuthorize("@ss.hasPermission('hk:map-feedback:update')")
    public CommonResult<Boolean> updateMapFeedback(@Valid @RequestBody MapFeedbackSaveReqVO updateReqVO) {
        mapFeedbackService.updateMapFeedback(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除厂区地图反馈")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:map-feedback:delete')")
    public CommonResult<Boolean> deleteMapFeedback(@RequestParam("id") Integer id) {
        mapFeedbackService.deleteMapFeedback(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得厂区地图反馈")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:map-feedback:query')")
    public CommonResult<MapFeedbackRespVO> getMapFeedback(@RequestParam("id") Integer id) {
        MapFeedbackDO mapFeedback = mapFeedbackService.getMapFeedback(id);
        return success(BeanUtils.toBean(mapFeedback, MapFeedbackRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得厂区地图反馈分页")
    @PreAuthorize("@ss.hasPermission('hk:map-feedback:query')")
    public CommonResult<PageResult<MapFeedbackRespVO>> getMapFeedbackPage(@Valid MapFeedbackPageReqVO pageReqVO) {
        PageResult<MapFeedbackDO> pageResult = mapFeedbackService.getMapFeedbackPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MapFeedbackRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出厂区地图反馈 Excel")
    @PreAuthorize("@ss.hasPermission('hk:map-feedback:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMapFeedbackExcel(@Valid MapFeedbackPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MapFeedbackDO> list = mapFeedbackService.getMapFeedbackPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "厂区地图反馈.xls", "数据", MapFeedbackRespVO.class,
                        BeanUtils.toBean(list, MapFeedbackRespVO.class));
    }

}