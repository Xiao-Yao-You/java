package com.hk.jigai.module.system.controller.admin.inspectionrecord;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.excel.core.util.ExcelUtils;
import com.hk.jigai.module.system.controller.admin.inspectionrecord.vo.InspectionRecordPageReqVO;
import com.hk.jigai.module.system.controller.admin.inspectionrecord.vo.InspectionRecordRespVO;
import com.hk.jigai.module.system.controller.admin.inspectionrecord.vo.InspectionRecordSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.inspectionrecord.InspectionRecordDO;
import com.hk.jigai.module.system.service.inspectionrecord.InspectionRecordService;
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


@Tag(name = "管理后台 - 设备巡检记录")
@RestController
@RequestMapping("/inspection-record")
@Validated
public class InspectionRecordController {

    @Resource
    private InspectionRecordService inspectionRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建设备巡检记录")
    @PreAuthorize("@ss.hasPermission('hk:inspection-record:create')")
    public CommonResult<Long> createInspectionRecord(@Valid @RequestBody InspectionRecordSaveReqVO createReqVO) {
        return success(inspectionRecordService.createInspectionRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备巡检记录")
    @PreAuthorize("@ss.hasPermission('hk:inspection-record:update')")
    public CommonResult<Boolean> updateInspectionRecord(@Valid @RequestBody InspectionRecordSaveReqVO updateReqVO) {
        inspectionRecordService.updateInspectionRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备巡检记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:inspection-record:delete')")
    public CommonResult<Boolean> deleteInspectionRecord(@RequestParam("id") Long id) {
        inspectionRecordService.deleteInspectionRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备巡检记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:inspection-record:query')")
    public CommonResult<InspectionRecordRespVO> getInspectionRecord(@RequestParam("id") Long id) {
        InspectionRecordDO inspectionRecord = inspectionRecordService.getInspectionRecord(id);
        return success(BeanUtils.toBean(inspectionRecord, InspectionRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备巡检记录分页")
    @PreAuthorize("@ss.hasPermission('hk:inspection-record:query')")
    public CommonResult<PageResult<InspectionRecordRespVO>> getInspectionRecordPage(@Valid InspectionRecordPageReqVO pageReqVO) {
        PageResult<InspectionRecordDO> pageResult = inspectionRecordService.getInspectionRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InspectionRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备巡检记录 Excel")
    @PreAuthorize("@ss.hasPermission('hk:inspection-record:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportInspectionRecordExcel(@Valid InspectionRecordPageReqVO pageReqVO,
                                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<InspectionRecordDO> list = inspectionRecordService.getInspectionRecordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备巡检记录.xls", "数据", InspectionRecordRespVO.class,
                BeanUtils.toBean(list, InspectionRecordRespVO.class));
    }

}