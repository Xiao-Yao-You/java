package com.hk.jigai.module.system.controller.admin.reportpersonorder.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 个人工单处理月报分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReportPersonOrderPageReqVO extends PageParam {

    @Schema(description = "报表月份")
    private String reportMonth;

    @Schema(description = "运维人员Id", example = "475")
    private Long userId;

    @Schema(description = "运维人员姓名", example = "芋艿")
    private String username;

    @Schema(description = "处置工单总数量", example = "23485")
    private Integer totalOrderCount;

    @Schema(description = "已完成的工单数量", example = "14964")
    private Integer completeOrderCount;

    @Schema(description = "处理中的工单数量", example = "18464")
    private Integer processingOrderCount;

    @Schema(description = "平均处理时长")
    private Long aht;

    @Schema(description = "处理总时长")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Long[] totalHandleTime;

    @Schema(description = "处理-工作时间占比")
    private BigDecimal timeProportion;

    @Schema(description = "紧急程度-工单数量分布")
    private String urgencyLevelDistribution;

    @Schema(description = "接单类型-工单数量分布")
    private String orderTypeDistribution;

    @Schema(description = "主动接单占比")
    private BigDecimal orderAcceptedProportion;

    @Schema(description = "挂起的工单数量", example = "30576")
    private Integer pendingOrderCount;

    @Schema(description = "挂起总时长")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Long[] pendingTotalTime;

    @Schema(description = "平均挂起时长")
    private Long apt;

    @Schema(description = "按时完成率")
    private BigDecimal onTimeCompletionRate;

    @Schema(description = "单日最大处理量")
    private String dailyHandleMax;

    @Schema(description = "环比增长量")
    private Integer monthOnMonthGrowth;

    @Schema(description = "环比增长率")
    private BigDecimal monthOnMonthGrowthRate;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}