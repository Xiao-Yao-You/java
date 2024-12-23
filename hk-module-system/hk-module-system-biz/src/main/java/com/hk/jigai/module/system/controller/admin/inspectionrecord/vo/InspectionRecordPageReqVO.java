package com.hk.jigai.module.system.controller.admin.inspectionrecord.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备巡检记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InspectionRecordPageReqVO extends PageParam {

    @Schema(description = "设备编码")
    private String deviceCode;

    @Schema(description = "设备名称", example = "王五")
    private String deviceName;

    @Schema(description = "设备序列号")
    private String deviceSerial;

    @Schema(description = "设备二维码")
    private String deviceLabelCode;

    @Schema(description = "设备资产编号")
    private String deviceAssetNum;

    @Schema(description = "任务状态", example = "2")
    private Integer taskStatus;

    @Schema(description = "计划巡检日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] checkPlanTime;

    @Schema(description = "巡检人姓名", example = "李四")
    private String checkExecutorName;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}