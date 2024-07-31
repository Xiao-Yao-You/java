package com.hk.jigai.module.system.controller.admin.reportjobschedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 汇报工作进度 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportJobScheduleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "10832")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "汇报id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22586")
    @ExcelProperty("汇报id")
    private Long userReportId;

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("序号")
    private Integer sort;

    @Schema(description = "工作内容")
    @ExcelProperty("工作内容")
    private String content;

    @Schema(description = "完成情况")
    @ExcelProperty("完成情况")
    private String situation;

    @Schema(description = "关联待跟进的事项工作内容")
    @ExcelProperty("关联待跟进的事项工作内容")
    private String connectContent;

    @Schema(description = "关联待跟进的事项id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24794")
    @ExcelProperty("关联待跟进的事项id")
    private Long connectId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}