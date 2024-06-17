package com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 厂区地图定位详细信息新增/修改 Request VO")
@Data
public class MapCoordinateInfoSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "24389")
    private Integer id;

    @Schema(description = "园区，00:南通产业园", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "园区，00:南通产业园不能为空")
    private String zoneType;

    @Schema(description = "类型：00:仓库,01:车间,02:办公楼,03:餐厅,04:大门", example = "1")
    private String type;

    @Schema(description = "描述", example = "你猜")
    private String description;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "GPS经度")
    private BigDecimal longitude;

    @Schema(description = "GPS纬度")
    private BigDecimal latitude;

}