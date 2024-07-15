package com.hk.jigai.module.system.controller.admin.scenecode.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 单据编码类型配置新增/修改 Request VO")
@Data
public class SceneCodeSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19424")
    private Integer id;

    @Schema(description = "编码key", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "编码key不能为空")
    private String keyCode;

    @Schema(description = "描述", example = "随便")
    private String description;

    @Schema(description = "编码前缀", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "编码前缀不能为空")
    private String prefix;

    @Schema(description = "编码中缀", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "编码中缀不能为空")
    private String infix;

    @Schema(description = "编码后缀", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "编码后缀不能为空")
    private String suffix;

    @Schema(description = "编码规则", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "编码规则不能为空")
    private String type;

    @Schema(description = "起始值", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "起始值不能为空")
    private Integer start;

    @Schema(description = "步长", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "步长不能为空")
    private Integer step;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "使用状态,0:未使用,1:已使用")
    private Integer useStatus;

}