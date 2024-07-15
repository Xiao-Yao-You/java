package com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 厂区地图反馈 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MapFeedbackRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "8325")
    @ExcelProperty("主键")
    private Integer id;

    @Schema(description = "地图id", requiredMode = Schema.RequiredMode.REQUIRED, example = "29798")
    @ExcelProperty("地图id")
    private Integer mapId;

    @Schema(description = "地图名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "仓库1")
    @ExcelProperty("地图名称")
    private String mapName;

    @Schema(description = "描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "你说的对")
    @ExcelProperty("描述")
    private String description;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}