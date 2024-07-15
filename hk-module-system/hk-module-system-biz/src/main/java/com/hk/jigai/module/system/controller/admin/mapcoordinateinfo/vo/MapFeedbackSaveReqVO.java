package com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 厂区地图反馈新增/修改 Request VO")
@Data
public class MapFeedbackSaveReqVO {

    @Schema(description = "主键", example = "8325")
    private Integer id;

    @Schema(description = "地图id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29798")
    @NotNull(message = "地图id不能为空")
    private Integer mapId;

    @Schema(description = "地图name", requiredMode = Schema.RequiredMode.REQUIRED, example = "29798")
    @NotEmpty(message = "名称不能为空")
    private String mapName;

    @Schema(description = "描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "你说的对")
    @NotEmpty(message = "描述不能为空")
    private String description;

}