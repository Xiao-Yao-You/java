package com.hk.jigai.module.system.dal.dataobject.mapcoordinateinfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 厂区地图反馈 DO
 *
 * @author 恒科信改
 */
@TableName("hk_map_feedback")
@KeySequence("hk_map_feedback_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapFeedbackDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 地图id
     */
    private Integer mapId;

    /**
     * 地图名称
     */
    private String mapName;
    /**
     * 描述
     */
    private String description;

}