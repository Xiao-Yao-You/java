package com.hk.jigai.module.system.controller.admin.inspectionrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备巡检记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class InspectionRecordRespVO {

    @Schema(description = "设备编码")
    @ExcelProperty("设备编码")
    private String deviceCode;

    @Schema(description = "设备名称", example = "王五")
    @ExcelProperty("设备名称")
    private String deviceName;

    @Schema(description = "设备id	", example = "21614")
    @ExcelProperty("设备id	")
    private Long deviceId;

    @Schema(description = "设备型号")
    @ExcelProperty("设备型号")
    private String deviceModel;

    @Schema(description = "设备型号id", example = "9572")
    @ExcelProperty("设备型号id")
    private Long deviceModelId;

    @Schema(description = "设备序列号")
    @ExcelProperty("设备序列号")
    private String deviceSerial;

    @Schema(description = "设备二维码")
    @ExcelProperty("设备二维码")
    private String deviceLabelCode;

    @Schema(description = "设备资产编号")
    @ExcelProperty("设备资产编号")
    private String deviceAssetNum;

    @Schema(description = "设备地点Id", example = "25243")
    @ExcelProperty("设备地点Id")
    private Long deviceAddressId;

    @Schema(description = "设备地点")
    @ExcelProperty("设备地点")
    private String deviceAddrss;

    @Schema(description = "设备位置")
    @ExcelProperty("设备位置")
    private String deviceLocation;

    @Schema(description = "任务状态", example = "2")
    @ExcelProperty("任务状态")
    private Integer taskStatus;

    @Schema(description = "计划巡检日期")
    @ExcelProperty("计划巡检日期")
    private LocalDateTime checkPlanTime;

    @Schema(description = "实际完成日期")
    @ExcelProperty("实际完成日期")
    private LocalDateTime completeTime;

    @Schema(description = "巡检人")
    @ExcelProperty("巡检人")
    private Long checkExecutor;

    @Schema(description = "巡检人姓名", example = "李四")
    @ExcelProperty("巡检人姓名")
    private String checkExecutorName;

    @Schema(description = "巡检结果")
    @ExcelProperty("巡检结果")
    private Integer checkResult;

    @Schema(description = "附件")
    @ExcelProperty("附件")
    private String filePath;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}