package com.hk.jigai.module.system.controller.admin.userreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 汇报关注跟进 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportAttentionRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "24197")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "部门领导id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27701")
    @ExcelProperty("部门领导id")
    private Long userId;

    @Schema(description = "部门领导批复")
    @ExcelProperty("部门领导批复")
    private String reply;

    @Schema(description = "0:工作进度,1:工作计划", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("0:工作进度,1:工作计划")
    private Integer type;

    @Schema(description = "工作进度、计划id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17207")
    @ExcelProperty("工作进度、计划id")
    private Long jobId;

    @Schema(description = "关联待跟进的事项工作内容")
    @ExcelProperty("关联待跟进的事项工作内容")
    private String connectContent;

    @Schema(description = "部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15598")
    @ExcelProperty("部门id")
    private Long deptId;

    @Schema(description = "部门name")
    @ExcelProperty("部门name")
    private Long deptName;

    @Schema(description = "汇报日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("汇报日期")
    private LocalDate dateReport;

    @Schema(description = "关注事项工作内容")
    @ExcelProperty("关注事项工作内容")
    private String content;

    @Schema(description = "转交备注", example = "你猜")
    @ExcelProperty("转交备注")
    private String transferRemark;

    @Schema(description = "跟进人id", example = "11207")
    @ExcelProperty("跟进人id")
    private String replyUserId;

    @Schema(description = "跟进人name", example = "张三")
    @ExcelProperty("跟进人name")
    private String replyUserNickName;

    @Schema(description = "跟进的完成情况")
    @ExcelProperty("跟进的完成情况")
    private String situation;

    @Schema(description = "跟进状态,0:未跟进,1:已跟进", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("跟进状态,0:未跟进,1:已跟进")
    private String replyStatus;

    @Schema(description = "跟进最新结果,新增记录,关联工作进度表", example = "19256")
    @ExcelProperty("跟进最新结果,新增记录,关联工作进度表")
    private Long jobScheduleId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}