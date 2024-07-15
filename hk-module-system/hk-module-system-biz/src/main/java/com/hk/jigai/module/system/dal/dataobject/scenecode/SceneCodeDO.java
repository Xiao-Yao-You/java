package com.hk.jigai.module.system.dal.dataobject.scenecode;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 单据编码类型配置 DO
 *
 * @author 恒科信改
 */
@TableName("system_scene_code")
@KeySequence("system_scene_code_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SceneCodeDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 编码key
     */
    private String keyCode;
    /**
     * 描述
     */
    private String description;
    /**
     * 编码前缀
     */
    private String prefix;
    /**
     * 编码中缀
     */
    private String infix;
    /**
     * 编码后缀
     */
    private String suffix;
    /**
     * 编码规则
     */
    private String type;
    /**
     * 起始值
     */
    private Integer start;
    /**
     * 步长
     */
    private Integer step;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 状态
     */
    private Integer useStatus;

}