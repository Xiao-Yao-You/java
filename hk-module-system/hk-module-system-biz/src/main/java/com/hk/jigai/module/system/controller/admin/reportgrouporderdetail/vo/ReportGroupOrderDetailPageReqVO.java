package com.hk.jigai.module.system.controller.admin.reportgrouporderdetail.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 小组工单处理月报详情分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReportGroupOrderDetailPageReqVO extends PageParam {

//    @Schema(description = "公司id", example = "27354")
//    private Long companyId;
//
//    @Schema(description = "公司名称", example = "王五")
//    private String companyName;

    @Schema(description = "分组Id", example = "362")
    private Long groupId;

    @Schema(description = "分组名称", example = "芋艿")
    private String groupName;

    @Schema(description = "报表月份")
    private String reportMonth;

    @Schema(description = "问题类型Id", example = "10835")
    private Long questionTypeId;

    @Schema(description = "问题类型", example = "芋艿")
    private String questionTypeName;

    @Schema(description = "工单数量", example = "32408")
    private Integer orderCount;

    @Schema(description = "组内占比")
    private BigDecimal groupProportion;

    @Schema(description = "紧急程度-工单数量分布")
    private String urgencyLevelDistribution;

    @Schema(description = "接单类型-工单数量分布")
    private String orderTypeDistribution;

    @Schema(description = "主动接单占比")
    private BigDecimal orderAcceptedProportion;

    @Schema(description = "挂起的工单数量", example = "23617")
    private Integer pendingOrderCount;

    @Schema(description = "挂起总时长")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Long[] pendingTotalTime;

    @Schema(description = "平均挂起时长")
    private Long apt;

    @Schema(description = "按时完成率")
    private BigDecimal onTimeCompletionRate;

    @Schema(description = "环比增长量")
    private Integer monthOnMonthGrowth;

    @Schema(description = "环比增长率")
    private BigDecimal monthOnMonthGrowthRate;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}