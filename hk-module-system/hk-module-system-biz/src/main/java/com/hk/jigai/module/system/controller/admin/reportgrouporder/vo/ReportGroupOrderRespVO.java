package com.hk.jigai.module.system.controller.admin.reportgrouporder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 小组工单处理月报 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportGroupOrderRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "25522")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "分组Id", example = "14995")
    @ExcelProperty("分组Id")
    private Long groupId;

    @Schema(description = "分组名称", example = "张三")
    @ExcelProperty("分组名称")
    private String groupName;

    @Schema(description = "报表月份")
    @ExcelProperty("报表月份")
    private String reportMonth;

    @Schema(description = "工单数量", example = "14160")
    @ExcelProperty("工单数量")
    private Integer orderCount;

    @Schema(description = "紧急程度-工单数量分布")
    @ExcelProperty("紧急程度-工单数量分布")
    private String urgencyLevelDistribution;

    @Schema(description = "接单类型-工单数量分布")
    @ExcelProperty("接单类型-工单数量分布")
    private String orderTypeDistribution;

    @Schema(description = "主动接单占比")
    @ExcelProperty("主动接单占比")
    private BigDecimal orderAcceptedProportion;

    @Schema(description = "挂起的工单数量", example = "24683")
    @ExcelProperty("挂起的工单数量")
    private Integer pendingOrderCount;

    @Schema(description = "挂起总时长")
    @ExcelProperty("挂起总时长")
    private Long pendingTotalTime;

    @Schema(description = "平均挂起时长")
    @ExcelProperty("平均挂起时长")
    private Long apt;

    @Schema(description = "按时完成率")
    @ExcelProperty("按时完成率")
    private BigDecimal onTimeCompletionRate;

    @Schema(description = "环比增长量")
    @ExcelProperty("环比增长量")
    private Integer monthOnMonthGrowth;

    @Schema(description = "环比增长率")
    @ExcelProperty("环比增长率")
    private BigDecimal monthOnMonthGrowthRate;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "处置超时次数")
    @ExcelProperty("处置超时次数")
    private Integer handleTimeoutTimes;

    @Schema(description = "处置超时率")
    @ExcelProperty("处置超时率")
    private BigDecimal handleTimeoutRate;

}