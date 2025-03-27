package com.hk.jigai.module.system.controller.admin.reportdevice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 设备资产报新增/修改 Request VO")
@Data
public class ReportDeviceSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "25868")
    private Long id;

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

}