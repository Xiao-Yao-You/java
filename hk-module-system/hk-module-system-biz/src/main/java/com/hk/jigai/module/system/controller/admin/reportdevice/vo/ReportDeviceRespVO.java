package com.hk.jigai.module.system.controller.admin.reportdevice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备资产报 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportDeviceRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "25868")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "报表日期")
    @ExcelProperty("报表日期")
    private String reportMonth;

    @Schema(description = "公司id", example = "6216")
    @ExcelProperty("公司id")
    private Long companyId;

    @Schema(description = "公司名称", example = "王五")
    @ExcelProperty("公司名称")
    private String companyName;

    @Schema(description = "设备类型", example = "2")
    @ExcelProperty("设备类型")
    private String deviceType;

    @Schema(description = "设备数量", example = "31763")
    @ExcelProperty("设备数量")
    private Integer deviceCount;

    @Schema(description = "设备状态分布")
    @ExcelProperty("设备状态分布")
    private String stateDistribution;

    @Schema(description = "影响程度分布")
    @ExcelProperty("影响程度分布")
    private String effectDistribution;

    @Schema(description = "设备部门分布")
    @ExcelProperty("设备部门分布")
    private String deptDistribution;

    @Schema(description = "故障率")
    @ExcelProperty("故障率")
    private BigDecimal failureRate;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}