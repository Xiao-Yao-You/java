package com.hk.jigai.module.system.dal.dataobject.operation;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 运维问题类型 DO
 *
 * @author 超级管理员
 */
@TableName("hk_operation_question_type")
@KeySequence("hk_operation_question_type_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationQuestionTypeDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 父级问题类型
     */
    private Long parentId;
    /**
     * 类型(0:软件,1:硬件,2:其他)
     */
    private String type;
    /**
     * 设备类型id
     */
    private Long deviceTypeId;
    /**
     * 设备类型名称
     */
    private String deviceTypeName;
    /**
     * 描述
     */
    private String description;
    /**
     * 解决方案
     */
    private String solution;

}