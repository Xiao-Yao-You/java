package com.hk.jigai.module.system.controller.admin.inspectionproject;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.excel.core.util.ExcelUtils;
import com.hk.jigai.module.system.controller.admin.inspectionproject.vo.InspectionProjectPageReqVO;
import com.hk.jigai.module.system.controller.admin.inspectionproject.vo.InspectionProjectRespVO;
import com.hk.jigai.module.system.controller.admin.inspectionproject.vo.InspectionProjectSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.inspectionproject.InspectionProjectDO;
import com.hk.jigai.module.system.service.inspectionproject.InspectionProjectService;
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

@Tag(name = "管理后台 - 巡检项目指标")
@RestController
@RequestMapping("/inspection-project")
@Validated
public class InspectionProjectController {

    @Resource
    private InspectionProjectService inspectionProjectService;

    @PostMapping("/create")
    @Operation(summary = "创建巡检项目指标")
    @PreAuthorize("@ss.hasPermission('hk:inspection-project:create')")
    public CommonResult<Long> createInspectionProject(@Valid @RequestBody InspectionProjectSaveReqVO createReqVO) {
        return success(inspectionProjectService.createInspectionProject(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新巡检项目指标")
    @PreAuthorize("@ss.hasPermission('hk:inspection-project:update')")
    public CommonResult<Boolean> updateInspectionProject(@Valid @RequestBody InspectionProjectSaveReqVO updateReqVO) {
        inspectionProjectService.updateInspectionProject(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除巡检项目指标")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:inspection-project:delete')")
    public CommonResult<Boolean> deleteInspectionProject(@RequestParam("id") Long id) {
        inspectionProjectService.deleteInspectionProject(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得巡检项目指标")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:inspection-project:query')")
    public CommonResult<InspectionProjectRespVO> getInspectionProject(@RequestParam("id") Long id) {
        InspectionProjectDO inspectionProject = inspectionProjectService.getInspectionProject(id);
        return success(BeanUtils.toBean(inspectionProject, InspectionProjectRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得巡检项目指标分页")
    @PreAuthorize("@ss.hasPermission('hk:inspection-project:query')")
    public CommonResult<PageResult<InspectionProjectRespVO>> getInspectionProjectPage(@Valid InspectionProjectPageReqVO pageReqVO) {
        PageResult<InspectionProjectDO> pageResult = inspectionProjectService.getInspectionProjectPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InspectionProjectRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出巡检项目指标 Excel")
    @PreAuthorize("@ss.hasPermission('hk:inspection-project:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportInspectionProjectExcel(@Valid InspectionProjectPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<InspectionProjectDO> list = inspectionProjectService.getInspectionProjectPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "巡检项目指标.xls", "数据", InspectionProjectRespVO.class,
                        BeanUtils.toBean(list, InspectionProjectRespVO.class));
    }

}