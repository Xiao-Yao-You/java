package com.hk.jigai.module.system.controller.admin.operation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 运维问题类型新增/修改 Request VO")
@Data
public class OperationQuestionTypeSaveReqVO {

    @Schema(description = "主键", example = "1618")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "父级问题类型", example = "12381")
    private Long parentId;

    @Schema(description = "类型(0:软件,1:硬件,2:其他)", example = "1")
    private String type;

    @Schema(description = "设备类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long deviceTypeId;

    @Schema(description = "设备类型名称")
    private String deviceTypeName;

    @Schema(description = "描述", example = "随便")
    private String description;

    @Schema(description = "解决方案")
    private String solution;

}