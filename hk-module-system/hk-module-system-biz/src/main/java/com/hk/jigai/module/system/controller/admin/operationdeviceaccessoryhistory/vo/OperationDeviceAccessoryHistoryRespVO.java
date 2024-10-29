package com.hk.jigai.module.system.controller.admin.operationdeviceaccessoryhistory.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 运维设备配件表_快照 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperationDeviceAccessoryHistoryRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28254")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18439")
    @ExcelProperty("设备id")
    private Long deviceId;

    @Schema(description = "配件描述")
    @ExcelProperty("配件描述")
    private String accessoryDesc;

    @Schema(description = "型号")
    @ExcelProperty("型号")
    private String model;

    @Schema(description = "数量")
    @ExcelProperty("数量")
    private String num;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "快照Id", example = "25814")
    @ExcelProperty("快照Id")
    private Long historyId;

}