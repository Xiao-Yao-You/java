package com.hk.jigai.module.system.controller.admin.operation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 运维地点新增/修改 Request VO")
@Data
public class OperationAddressSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1123")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "名称不能为空")
    private String addressName;

    @Schema(description = "上级地址id", example = "23754")
    private Long parentAddressId;

    @Schema(description = "区域软件负责人", requiredMode = Schema.RequiredMode.REQUIRED, example = "22328")
    @NotNull(message = "区域软件负责人不能为空")
    private Long softUserId;

    @Schema(description = "区域软件负责人昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "区域软件负责人昵称不能为空")
    private String softUserNickName;

    @Schema(description = "区域硬件负责人", requiredMode = Schema.RequiredMode.REQUIRED, example = "3479")
    @NotNull(message = "区域硬件负责人不能为空")
    private Long hardwareUserId;

    @Schema(description = "区域硬件负责人昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "区域硬件负责人昵称不能为空")
    private String hardwareUserNickName;

    @Schema(description = "状态 0:已启用,1:未启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态 0:已启用,1:未启用不能为空")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}