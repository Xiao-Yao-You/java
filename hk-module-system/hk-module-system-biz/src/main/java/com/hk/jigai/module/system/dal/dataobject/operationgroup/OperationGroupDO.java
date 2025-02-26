package com.hk.jigai.module.system.dal.dataobject.operationgroup;

import com.hk.jigai.framework.mybatis.core.type.JsonLongSetTypeHandler;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 运维小组 DO
 *
 * @author 邵志伟
 */
@TableName("hk_operation_group")
@KeySequence("hk_operation_group_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationGroupDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 用户id集合
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> userIds;
    /**
     * 分组
     */
    private String group;

}