package com.hk.jigai.module.system.dal.dataobject.operation;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 运维设备配件 DO
 *
 * @author 超级管理员
 */
@TableName("hk_operation_device_accessory")
@KeySequence("hk_operation_device_accessory_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDeviceAccessoryDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 设备id
     */
    private Long deviceId;
    /**
     * 配件描述
     */
    private String accessoryDesc;
    /**
     * 型号
     */
    private String model;
    /**
     * 数量
     */
    private String num;
    /**
     * 备注
     */
    private String remark;

}