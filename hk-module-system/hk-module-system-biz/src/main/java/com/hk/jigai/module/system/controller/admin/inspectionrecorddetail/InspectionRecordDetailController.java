package com.hk.jigai.module.system.controller.admin.inspectionrecorddetail;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.excel.core.util.ExcelUtils;
import com.hk.jigai.module.system.controller.admin.inspectionrecorddetail.vo.InspectionRecordDetailPageReqVO;
import com.hk.jigai.module.system.controller.admin.inspectionrecorddetail.vo.InspectionRecordDetailRespVO;
import com.hk.jigai.module.system.controller.admin.inspectionrecorddetail.vo.InspectionRecordDetailSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.inspectionrecorddetail.InspectionRecordDetailDO;
import com.hk.jigai.module.system.service.inspectionrecorddetail.InspectionRecordDetailService;
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


@Tag(name = "管理后台 - 巡检记录详情")
@RestController
@RequestMapping("/inspection-record-detail")
@Validated
public class InspectionRecordDetailController {

    @Resource
    private InspectionRecordDetailService inspectionRecordDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建巡检记录详情")
    @PreAuthorize("@ss.hasPermission('hk:inspection-record-detail:create')")
    public CommonResult<Long> createInspectionRecordDetail(@Valid @RequestBody InspectionRecordDetailSaveReqVO createReqVO) {
        return success(inspectionRecordDetailService.createInspectionRecordDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新巡检记录详情")
    @PreAuthorize("@ss.hasPermission('hk:inspection-record-detail:update')")
    public CommonResult<Boolean> updateInspectionRecordDetail(@Valid @RequestBody InspectionRecordDetailSaveReqVO updateReqVO) {
        inspectionRecordDetailService.updateInspectionRecordDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除巡检记录详情")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:inspection-record-detail:delete')")
    public CommonResult<Boolean> deleteInspectionRecordDetail(@RequestParam("id") Long id) {
        inspectionRecordDetailService.deleteInspectionRecordDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得巡检记录详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:inspection-record-detail:query')")
    public CommonResult<InspectionRecordDetailRespVO> getInspectionRecordDetail(@RequestParam("id") Long id) {
        InspectionRecordDetailDO inspectionRecordDetail = inspectionRecordDetailService.getInspectionRecordDetail(id);
        return success(BeanUtils.toBean(inspectionRecordDetail, InspectionRecordDetailRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得巡检记录详情分页")
    @PreAuthorize("@ss.hasPermission('hk:inspection-record-detail:query')")
    public CommonResult<PageResult<InspectionRecordDetailRespVO>> getInspectionRecordDetailPage(@Valid InspectionRecordDetailPageReqVO pageReqVO) {
        PageResult<InspectionRecordDetailDO> pageResult = inspectionRecordDetailService.getInspectionRecordDetailPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InspectionRecordDetailRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出巡检记录详情 Excel")
    @PreAuthorize("@ss.hasPermission('hk:inspection-record-detail:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportInspectionRecordDetailExcel(@Valid InspectionRecordDetailPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<InspectionRecordDetailDO> list = inspectionRecordDetailService.getInspectionRecordDetailPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "巡检记录详情.xls", "数据", InspectionRecordDetailRespVO.class,
                        BeanUtils.toBean(list, InspectionRecordDetailRespVO.class));
    }

}