package com.hk.jigai.module.system.controller.admin.operation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 运维设备配件新增/修改 Request VO")
@Data
public class OperationDeviceAccessorySaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "14512")
    private Long id;

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2013")
    @NotNull(message = "设备id不能为空")
    private Long deviceId;

    @Schema(description = "配件描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "配件描述不能为空")
    private String accessoryDesc;

    @Schema(description = "型号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "型号不能为空")
    private String model;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "数量不能为空")
    private String num;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "随便")
    @NotEmpty(message = "备注不能为空")
    private String remark;

}