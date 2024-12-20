package com.hk.jigai.module.system.controller.admin.inspectionproject.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 巡检项目指标 Response VO")
@Data
@ExcelIgnoreUnannotated
public class InspectionProjectRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30269")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "巡检项目")
    @ExcelProperty("巡检项目")
    private String inspectionProject;

    @Schema(description = "项目指标")
    @ExcelProperty("项目指标")
    private String inspectionIndicators;

    @Schema(description = "状态：0可用，1禁用", example = "1")
    @ExcelProperty("状态：0可用，1禁用")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "备注", example = "父级id")
    @ExcelProperty("父级id")
    private Long parentId;

}