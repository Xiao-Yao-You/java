package com.hk.jigai.module.system.controller.admin.reportdevice.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备资产报分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReportDevicePageReqVO extends PageParam {

    @Schema(description = "报表日期")
    private String reportMonth;

    @Schema(description = "公司id", example = "6216")
    private Long companyId;

    @Schema(description = "公司名称", example = "王五")
    private String companyName;

    @Schema(description = "设备类型", example = "2")
    private String deviceType;

    @Schema(description = "设备数量", example = "31763")
    private Integer deviceCount;

    @Schema(description = "设备状态分布")
    private String stateDistribution;

    @Schema(description = "影响程度分布")
    private String effectDistribution;

    @Schema(description = "设备部门分布")
    private String deptDistribution;

    @Schema(description = "故障率")
    private BigDecimal failureRate;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}