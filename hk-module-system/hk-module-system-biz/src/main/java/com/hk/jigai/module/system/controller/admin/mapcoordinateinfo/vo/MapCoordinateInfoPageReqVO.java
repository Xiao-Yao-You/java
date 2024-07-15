package com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 厂区地图定位详细信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MapCoordinateInfoPageReqVO extends PageParam {

    @Schema(description = "编码", example = "HK001")
    private String code;

    @Schema(description = "园区，00:南通产业园", example = "2")
    private String zoneType;

    @Schema(description = "厂区，00:恒科01:轩达", example = "00")
    private String factoryCode;

    @Schema(description = "类型：00:仓库,01:车间,02:办公楼,03:餐厅,04:大门", example = "1")
    private String type;

    @Schema(description = "名称", example = "你猜")
    private String name;

    @Schema(description = "别名", example = "你猜")
    private String alias;

    @Schema(description = "建筑物上名字", example = "你猜")
    private String markName;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "GPS经度")
    private BigDecimal longitude;

    @Schema(description = "GPS纬度")
    private BigDecimal latitude;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}