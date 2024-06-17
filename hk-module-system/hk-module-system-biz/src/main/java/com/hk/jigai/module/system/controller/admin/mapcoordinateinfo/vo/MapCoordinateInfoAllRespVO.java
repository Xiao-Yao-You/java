package com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 厂区地图定位详细全量信息 ResponseVO")
@Data
public class MapCoordinateInfoAllRespVO {

    @Schema(description = "厂区定位列表")
    private List<MapCoordinateInfoAllVO> list;

}