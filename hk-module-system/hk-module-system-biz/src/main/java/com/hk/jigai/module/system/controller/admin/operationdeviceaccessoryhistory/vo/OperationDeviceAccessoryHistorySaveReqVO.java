package com.hk.jigai.module.system.controller.admin.operationdeviceaccessoryhistory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 运维设备配件表_快照新增/修改 Request VO")
@Data
public class OperationDeviceAccessoryHistorySaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28254")
    private Long id;

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18439")
    @NotNull(message = "设备id不能为空")
    private Long deviceId;

    @Schema(description = "配件描述")
    private String accessoryDesc;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "数量")
    private String num;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "快照Id", example = "25814")
    private Long historyId;

}