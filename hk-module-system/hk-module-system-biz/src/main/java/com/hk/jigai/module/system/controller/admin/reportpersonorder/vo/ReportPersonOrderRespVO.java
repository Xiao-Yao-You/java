package com.hk.jigai.module.system.controller.admin.reportpersonorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 个人工单处理月报 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportPersonOrderRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "6714")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "报表月份")
    @ExcelProperty("报表月份")
    private String reportMonth;

    @Schema(description = "运维人员Id", example = "475")
    @ExcelProperty("运维人员Id")
    private Long userId;

    @Schema(description = "运维人员姓名", example = "芋艿")
    @ExcelProperty("运维人员姓名")
    private String username;

    @Schema(description = "处置工单总数量", example = "23485")
    @ExcelProperty("处置工单总数量")
    private Integer totalOrderCount;

    @Schema(description = "已完成的工单数量", example = "14964")
    @ExcelProperty("已完成的工单数量")
    private Integer completeOrderCount;

    @Schema(description = "处理中的工单数量", example = "18464")
    @ExcelProperty("处理中的工单数量")
    private Integer processingOrderCount;

    @Schema(description = "平均处理时长")
    @ExcelProperty("平均处理时长")
    private Long aht;

    @Schema(description = "处理总时长")
    @ExcelProperty("处理总时长")
    private Long totalHandleTime;

    @Schema(description = "处理-工作时间占比")
    @ExcelProperty("处理-工作时间占比")
    private BigDecimal timeProportion;

    @Schema(description = "紧急程度-工单数量分布")
    @ExcelProperty("紧急程度-工单数量分布")
    private String urgencyLevelDistribution;

    @Schema(description = "接单类型-工单数量分布")
    @ExcelProperty("接单类型-工单数量分布")
    private String orderTypeDistribution;

    @Schema(description = "主动接单占比")
    @ExcelProperty("主动接单占比")
    private BigDecimal orderAcceptedProportion;

    @Schema(description = "挂起的工单数量", example = "30576")
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

    @Schema(description = "单日最大处理量")
    @ExcelProperty("单日最大处理量")
    private String dailyHandleMax;

    @Schema(description = "环比增长量")
    @ExcelProperty("环比增长量")
    private Integer monthOnMonthGrowth;

    @Schema(description = "环比增长率")
    @ExcelProperty("环比增长率")
    private BigDecimal monthOnMonthGrowthRate;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}