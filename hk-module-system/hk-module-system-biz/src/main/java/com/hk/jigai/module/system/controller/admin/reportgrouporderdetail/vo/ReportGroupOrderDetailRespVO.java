package com.hk.jigai.module.system.controller.admin.reportgrouporderdetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 小组工单处理月报详情 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportGroupOrderDetailRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "16956")
    @ExcelProperty("主键")
    private Long id;

//    @Schema(description = "公司id", example = "27354")
//    @ExcelProperty("公司id")
//    private Long companyId;
//
//    @Schema(description = "公司名称", example = "王五")
//    @ExcelProperty("公司名称")
//    private String companyName;

    @Schema(description = "分组Id", example = "362")
    @ExcelProperty("分组Id")
    private Long groupId;

    @Schema(description = "分组名称", example = "芋艿")
    @ExcelProperty("分组名称")
    private String groupName;

    @Schema(description = "报表月份")
    @ExcelProperty("报表月份")
    private String reportMonth;

    @Schema(description = "问题类型Id", example = "10835")
    @ExcelProperty("问题类型Id")
    private Long questionTypeId;

    @Schema(description = "问题类型", example = "芋艿")
    @ExcelProperty("问题类型")
    private String questionTypeName;

    @Schema(description = "工单数量", example = "32408")
    @ExcelProperty("工单数量")
    private Integer orderCount;

    @Schema(description = "组内占比")
    @ExcelProperty("组内占比")
    private BigDecimal groupProportion;

    @Schema(description = "紧急程度-工单数量分布")
    @ExcelProperty("紧急程度-工单数量分布")
    private String urgencyLevelDistribution;

    @Schema(description = "接单类型-工单数量分布")
    @ExcelProperty("接单类型-工单数量分布")
    private String orderTypeDistribution;

    @Schema(description = "主动接单占比")
    @ExcelProperty("主动接单占比")
    private BigDecimal orderAcceptedProportion;

    @Schema(description = "挂起的工单数量", example = "23617")
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

}