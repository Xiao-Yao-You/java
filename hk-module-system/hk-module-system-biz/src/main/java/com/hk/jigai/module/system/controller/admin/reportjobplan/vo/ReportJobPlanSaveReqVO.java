package com.hk.jigai.module.system.controller.admin.reportjobplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 汇报工作计划新增/修改 Request VO")
@Data
public class ReportJobPlanSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "4665")
    private Long id;

    @Schema(description = "汇报id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9060")
    @NotNull(message = "汇报id不能为空")
    private Long userReportId;

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;

    @Schema(description = "工作内容")
    private String content;

    @Schema(description = "预计时间")
    private String estimatedTime;

    @Schema(description = "需要的资源")
    private String needSource;

}