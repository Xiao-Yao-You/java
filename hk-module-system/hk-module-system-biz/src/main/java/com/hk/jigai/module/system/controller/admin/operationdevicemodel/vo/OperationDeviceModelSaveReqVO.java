package com.hk.jigai.module.system.controller.admin.operationdevicemodel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 设备型号新增/修改 Request VO")
@Data
public class OperationDeviceModelSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12398")
    private Long id;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    private String remark;

    private Long deviceTypeId;

}