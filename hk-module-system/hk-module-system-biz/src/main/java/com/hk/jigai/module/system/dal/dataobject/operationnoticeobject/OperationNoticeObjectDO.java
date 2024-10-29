package com.hk.jigai.module.system.dal.dataobject.operationnoticeobject;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 消息通知对象设置 DO
 *
 * @author 邵志伟
 */
@TableName("hk_operation_notice_object")
@KeySequence("hk_operation_notice_object_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationNoticeObjectDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 消息通知对象
     */
    private Long userId;

    private String nickname;

}