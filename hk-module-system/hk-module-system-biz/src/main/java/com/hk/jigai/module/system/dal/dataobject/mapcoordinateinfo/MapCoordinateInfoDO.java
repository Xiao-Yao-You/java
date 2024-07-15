package com.hk.jigai.module.system.dal.dataobject.mapcoordinateinfo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 厂区地图定位详细信息 DO
 *
 * @author 恒科信改
 */
@TableName("hk_map_coordinate_info")
@KeySequence("hk_map_coordinate_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapCoordinateInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 园区，00:南通产业园
     */
    private String zoneType;
    /**
     * 厂区 00 恒科 01 轩达
     */
    private String factoryCode;
    /**
     * 编码
     */
    private String code;

    /**
     * 类型：00:仓库,01:车间,02:办公楼,03:餐厅,04:大门
     */
    private String type;
    /**
     * 名称
     */
    private String name;
    /**
     * 别名
     */
    private String alias;

    /**
     * 建筑物上名字
     */
    private String markName;
    /**
     * 图片
     */
    private String image;
    /**
     * GPS经度
     */
    private BigDecimal longitude;
    /**
     * GPS纬度
     */
    private BigDecimal latitude;

    /**
     * 序号
     */
    private int sort;

    /**
     * 状态 0,1
     */
    private Integer status;

}