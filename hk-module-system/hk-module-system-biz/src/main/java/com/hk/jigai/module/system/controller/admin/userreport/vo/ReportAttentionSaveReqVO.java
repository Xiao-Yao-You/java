package com.hk.jigai.module.system.controller.admin.userreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 汇报关注跟进新增/修改 Request VO")
@Data
public class ReportAttentionSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "24197")
    private Long id;

    @Schema(description = "部门领导id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27701")
    @NotNull(message = "部门领导id不能为空")
    private Long userId;

    @Schema(description = "部门领导批复")
    private String reply;

    @Schema(description = "0:工作进度,1:工作计划", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "0:工作进度,1:工作计划不能为空")
    private Integer type;

    @Schema(description = "工作进度、计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17207")
    @NotNull(message = "工作进度、计划id不能为空")
    private Long jobId;

    @Schema(description = "关联待跟进的事项工作内容")
    private String connectContent;

    @Schema(description = "部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15598")
    @NotNull(message = "部门id不能为空")
    private Long deptId;

    @Schema(description = "汇报日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "汇报日期不能为空")
    private LocalDate dateReport;

    @Schema(description = "关注事项工作内容")
    private String content;

    @Schema(description = "转交备注", example = "你猜")
    private String transferRemark;

    @Schema(description = "跟进人id", example = "11207")
    private String replyUserId;

    @Schema(description = "跟进的完成情况")
    private String situation;

    @Schema(description = "跟进状态,0:未跟进,1:已跟进", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "跟进状态,0:未跟进,1:已跟进不能为空")
    private String replyStatus;

    @Schema(description = "跟进最新结果,新增记录,关联工作进度表", example = "19256")
    private Long jobScheduleId;

}