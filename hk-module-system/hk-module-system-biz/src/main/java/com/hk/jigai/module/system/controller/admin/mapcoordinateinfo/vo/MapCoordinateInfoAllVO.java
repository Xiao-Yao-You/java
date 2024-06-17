package com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 厂区地图定位详细全量信息 VO")
@Data
public class MapCoordinateInfoAllVO {

    @Schema(description = "类型：00:仓库,01:车间,02:办公楼,03:餐厅,04:大门", example = "1")
    private String type;

    @Schema(description = "厂区定位列表")
    private List<MapCoordinateInfoRespVO> MapCoordinateInfoList;

}