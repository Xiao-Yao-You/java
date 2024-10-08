package com.hk.jigai.module.system.dal.dataobject.operation;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 运维地点 DO
 *
 * @author 超级管理员
 */
@TableName("hk_operation_address")
@KeySequence("hk_operation_address_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationAddressDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String addressName;
    /**
     * 上级地址id
     */
    private Long parentAddressId;
    /**
     * 区域软件负责人
     */
    private Long softUserId;
    /**
     * 区域软件负责人昵称
     */
    private String softUserNickName;
    /**
     * 区域硬件负责人
     */
    private Long hardwareUserId;
    /**
     * 区域硬件负责人昵称
     */
    private String hardwareUserNickName;
    /**
     * 状态 0:已启用,1:未启用
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}