package com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import javax.validation.constraints.NotEmpty;

@Schema(description = "管理后台 - 厂区地图定位详细信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MapCoordinateInfoRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "24389")
    @ExcelProperty("主键")
    private Integer id;

    @Schema(description = "园区，00:南通产业园", requiredMode = Schema.RequiredMode.REQUIRED, example = "00")
    @ExcelProperty("园区，00:南通产业园")
    private String zoneType;

    @Schema(description = "厂区，00:恒科01:轩达", example = "00")
    @ExcelProperty("厂区，00:恒科01:轩达")
    private String factoryCode;

    @Schema(description = "编码", example = "HK001")
    @ExcelProperty("编码")
    private String code;

    @Schema(description = "类型：00:仓库,01:车间,02:办公楼,03:餐厅,04:大门", example = "1")
    @ExcelProperty("类型：00:仓库,01:车间,02:办公楼,03:餐厅,04:大门")
    private String type;

    @Schema(description = "名称", example = "你猜")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "别名", example = "你猜")
    @ExcelProperty("别名")
    private String alias;

    @Schema(description = "建筑物上名字", example = "你猜")
    @ExcelProperty("建筑物上名字")
    private String markName;

    @Schema(description = "图片")
    @ExcelProperty("图片")
    private String image;

    @Schema(description = "GPS经度")
    @ExcelProperty("GPS经度")
    private BigDecimal longitude;

    @Schema(description = "GPS纬度")
    @ExcelProperty("GPS纬度")
    private BigDecimal latitude;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "序号")
    @ExcelProperty("序号")
    private int sort;

    @Schema(description = "状态", example = "0")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "创建人id", example = "id")
    @ExcelProperty("创建人id")
    private String creator;

    @Schema(description = "创建人姓名", example = "张三")
    @ExcelProperty("创建人姓名")
    private String creatorName;

}