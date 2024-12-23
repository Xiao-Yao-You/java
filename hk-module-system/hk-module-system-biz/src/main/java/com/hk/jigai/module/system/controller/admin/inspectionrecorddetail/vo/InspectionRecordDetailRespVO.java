package com.hk.jigai.module.system.controller.admin.inspectionrecorddetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 巡检记录详情 Response VO")
@Data
@ExcelIgnoreUnannotated
public class InspectionRecordDetailRespVO {

    @Schema(description = "记录id", example = "4312")
    @ExcelProperty("记录id")
    private Long recordId;

    @Schema(description = "巡检项目id", example = "31649")
    @ExcelProperty("巡检项目id")
    private Long inspectionProjectId;

    @Schema(description = "巡检项目")
    @ExcelProperty("巡检项目")
    private String inspectionProject;

    @Schema(description = "巡检项目指标")
    @ExcelProperty("巡检项目指标")
    private String inspectionIndicators;

    @Schema(description = "巡检结构")
    @ExcelProperty("巡检结构")
    private Integer result;

    @Schema(description = "说明/原因", example = "你猜")
    @ExcelProperty("说明/原因")
    private String remark;

    @Schema(description = "附件")
    @ExcelProperty("附件")
    private String filePath;

    @Schema(description = "工单id", example = "1548")
    @ExcelProperty("工单id")
    private Long orderId;

    @Schema(description = "整改状态", example = "2")
    @ExcelProperty("整改状态")
    private Integer 
correctionStatus;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}