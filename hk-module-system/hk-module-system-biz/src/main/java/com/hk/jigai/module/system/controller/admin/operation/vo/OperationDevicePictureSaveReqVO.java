package com.hk.jigai.module.system.controller.admin.operation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 运维设备照片表新增/修改 Request VO")
@Data
public class OperationDevicePictureSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13887")
    private Long id;

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "20959")
    @NotNull(message = "设备id不能为空")
    private Long deviceId;

    @Schema(description = "类型0:设备照片,1:现场照片,2:报废照片", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "类型0:设备照片,1:现场照片,2:报废照片不能为空")
    private String type;

    @Schema(description = "图片URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotEmpty(message = "图片URL不能为空")
    private String url;

}