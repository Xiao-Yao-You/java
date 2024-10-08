package com.hk.jigai.module.system.dal.dataobject.operation;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 运维标签code DO
 *
 * @author 超级管理员
 */
@TableName("hk_operation_label_code")
@KeySequence("hk_operation_label_code_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationLabelCodeDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 标签主键
     */
    private Long deviceId;
    /**
     * 编码
     */
    private String code;
    /**
     * 状态 0:空置,1:已关联设备
     */
    private Integer status;

}