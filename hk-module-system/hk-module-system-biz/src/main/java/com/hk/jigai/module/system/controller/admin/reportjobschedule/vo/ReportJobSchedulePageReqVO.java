package com.hk.jigai.module.system.controller.admin.reportjobschedule.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 汇报工作进度分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReportJobSchedulePageReqVO extends PageParam {

    @Schema(description = "汇报id", example = "22586")
    private Long userReportId;

    @Schema(description = "序号")
    private Integer sort;

    @Schema(description = "工作内容")
    private String content;

    @Schema(description = "完成情况")
    private String situation;

    @Schema(description = "关联待跟进的事项工作内容")
    private String connectContent;

    @Schema(description = "关联待跟进的事项id", example = "24794")
    private Long connectId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}