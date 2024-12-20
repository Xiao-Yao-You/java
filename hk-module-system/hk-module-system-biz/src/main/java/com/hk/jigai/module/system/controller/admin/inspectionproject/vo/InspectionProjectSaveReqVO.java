package com.hk.jigai.module.system.controller.admin.inspectionproject.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 巡检项目指标新增/修改 Request VO")
@Data
public class InspectionProjectSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30269")
    private Long id;

    @Schema(description = "巡检项目")
    private String inspectionProject;

    @Schema(description = "项目指标")
    private String inspectionIndicators;

    @Schema(description = "状态：0可用，1禁用", example = "1")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;


    /**
     * 父级id
     */
    @Schema(description = "备注", example = "父级id")
    private Long parentId;

}