package com.hk.jigai.module.system.controller.admin.userreport.vo;

import lombok.*;

import java.time.LocalDate;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 汇报关注跟进分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReportAttentionPageReqVO extends PageParam {

    @Schema(description = "部门领导id", example = "27701")
    private Long userId;

    @Schema(description = "部门领导批复")
    private String reply;

    @Schema(description = "0:工作进度,1:工作计划", example = "2")
    private Integer type;

    @Schema(description = "工作进度、计划id", example = "17207")
    private Long jobId;

    @Schema(description = "关联待跟进的事项工作内容")
    private String connectContent;

    @Schema(description = "部门id", example = "15598")
    private Long deptId;

    @Schema(description = "汇报日期")
    private LocalDate dateReport;

    @Schema(description = "关注事项工作内容")
    private String content;

    @Schema(description = "转交备注", example = "你猜")
    private String transferRemark;

    @Schema(description = "跟进人id", example = "11207")
    private Long replyUserId;

    @Schema(description = "跟进的完成情况")
    private String situation;

    @Schema(description = "跟进状态,0:未跟进,1:已跟进", example = "1")
    private String replyStatus;

    @Schema(description = "跟进最新结果,新增记录,关联工作进度表", example = "19256")
    private Long jobScheduleId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}