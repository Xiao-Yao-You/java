package com.hk.jigai.module.system.controller.admin.reportlist.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 月报表列 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportListRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29585")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "报表名称")
    @ExcelProperty("报表名称")
    private String reportTitle;

    @Schema(description = "报表月份")
    @ExcelProperty("报表月份")
    private String reportMonth;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}