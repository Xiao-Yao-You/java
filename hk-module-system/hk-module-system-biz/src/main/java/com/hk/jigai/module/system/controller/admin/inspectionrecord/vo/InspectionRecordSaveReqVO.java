package com.hk.jigai.module.system.controller.admin.inspectionrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备巡检记录新增/修改 Request VO")
@Data
public class InspectionRecordSaveReqVO {
    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30269")
    private Long id;


    @Schema(description = "设备编码")
    private String deviceCode;

    @Schema(description = "设备名称", example = "王五")
    private String deviceName;

    @Schema(description = "设备id	", example = "21614")
    private Long deviceId;

    @Schema(description = "设备型号")
    private String deviceModel;

    @Schema(description = "设备型号id", example = "9572")
    private Long deviceModelId;

    @Schema(description = "设备序列号")
    private String deviceSerial;

    @Schema(description = "设备二维码")
    private String deviceLabelCode;

    @Schema(description = "设备资产编号")
    private String deviceAssetNum;

    @Schema(description = "设备地点Id", example = "25243")
    private Long deviceAddressId;

    @Schema(description = "设备地点")
    private String deviceAddrss;

    @Schema(description = "设备位置")
    private String deviceLocation;

    @Schema(description = "任务状态", example = "2")
    private Integer taskStatus;

    @Schema(description = "计划巡检日期")
    private LocalDateTime checkPlanTime;

    @Schema(description = "实际完成日期")
    private LocalDateTime completeTime;

    @Schema(description = "巡检人")
    private Long checkExecutor;

    @Schema(description = "巡检人姓名", example = "李四")
    private String checkExecutorName;

    @Schema(description = "巡检结果")
    private Integer checkResult;

    @Schema(description = "附件")
    private String filePath;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}