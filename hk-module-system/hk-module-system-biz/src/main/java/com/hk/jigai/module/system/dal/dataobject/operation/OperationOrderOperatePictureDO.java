package com.hk.jigai.module.system.dal.dataobject.operation;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工单操作图片记录 DO
 *
 * @author 超级管理员
 */
@TableName("hk_operation_order_operate_picture")
@KeySequence("hk_operation_order_operate_picture_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationOrderOperatePictureDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 工单id
     */
    private Long operateRecordId;
    /**
     * 图片类型，00:现场确认
     */
    private String type;
    /**
     * 图片url
     */
    private String url;

}