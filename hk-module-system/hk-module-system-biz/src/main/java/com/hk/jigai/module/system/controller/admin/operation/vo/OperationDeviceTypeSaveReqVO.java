package com.hk.jigai.module.system.controller.admin.operation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 运维设备类型新增/修改 Request VO")
@Data
public class OperationDeviceTypeSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "15459")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "编码规则", requiredMode = Schema.RequiredMode.REQUIRED, example = "14994")
    @NotNull(message = "编码规则不能为空")
    private Long sceneCodeId;

    @Schema(description = "编码名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    @NotNull(message = "编码名称不能为空")
    private String sceneName;

    @Schema(description = "状态 0:已启用,1:未启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态 0:已启用,1:未启用不能为空")
    private Integer status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "标签编码规则", requiredMode = Schema.RequiredMode.REQUIRED, example = "14994")
    private Long labelSceneCodeId;

    @Schema(description = "标签编码名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    private String labelSceneName;



}