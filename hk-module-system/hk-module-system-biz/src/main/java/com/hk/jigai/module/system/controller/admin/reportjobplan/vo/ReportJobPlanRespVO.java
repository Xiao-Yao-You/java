package com.hk.jigai.module.system.controller.admin.reportjobplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 汇报工作计划 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportJobPlanRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "4665")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "汇报id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9060")
    @ExcelProperty("汇报id")
    private Long userReportId;

    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("序号")
    private Integer sort;

    @Schema(description = "工作内容")
    @ExcelProperty("工作内容")
    private String content;

    @Schema(description = "预计时间")
    @ExcelProperty("预计时间")
    private String estimatedTime;

    @Schema(description = "需要的资源")
    @ExcelProperty("需要的资源")
    private String needSource;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}