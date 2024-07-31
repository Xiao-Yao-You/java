package com.hk.jigai.module.system.controller.admin.reportjobschedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 汇报工作进度新增/修改 Request VO")
@Data
public class ReportJobScheduleSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10832")
    private Long id;

    @Schema(description = "汇报id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22586")
    @NotNull(message = "汇报id不能为空")
    private Long userReportId;

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;

    @Schema(description = "工作内容")
    private String content;

    @Schema(description = "完成情况")
    private String situation;

    @Schema(description = "关联待跟进的事项工作内容")
    private String connectContent;

    @Schema(description = "关联待跟进的事项id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24794")
    @NotNull(message = "关联待跟进的事项id不能为空")
    private Long connectId;

}