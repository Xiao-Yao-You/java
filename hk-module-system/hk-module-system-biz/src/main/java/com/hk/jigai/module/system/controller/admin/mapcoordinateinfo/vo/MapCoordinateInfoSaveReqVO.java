package com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 厂区地图定位详细信息新增/修改 Request VO")
@Data
public class MapCoordinateInfoSaveReqVO {

    @Schema(description = "主键", example = "24389")
    private Integer id;

    @Schema(description = "园区，00:南通产业园", requiredMode = Schema.RequiredMode.REQUIRED, example = "00")
    @NotEmpty(message = "园区，00:南通产业园不能为空")
    private String zoneType;

    @Schema(description = "厂区，00:恒科01:轩达", requiredMode = Schema.RequiredMode.REQUIRED, example = "00")
    @NotEmpty(message = "厂区，00:恒科01:轩达,不能为空")
    private String factoryCode;

    @Schema(description = "类型：00:仓库,01:车间,02:办公楼,03:餐厅,04:大门", example = "1")
    private String type;

    @Schema(description = "名称", example = "你猜", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "别名", example = "你猜")
    private String alias;

    @Schema(description = "建筑物上名字", example = "你猜")
    private String markName;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "GPS经度", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal longitude;

    @Schema(description = "GPS纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal latitude;

    @Schema(description = "排序")
    private int sort;

    @Schema(description = "状态", example = "0")
    private Integer status;

}