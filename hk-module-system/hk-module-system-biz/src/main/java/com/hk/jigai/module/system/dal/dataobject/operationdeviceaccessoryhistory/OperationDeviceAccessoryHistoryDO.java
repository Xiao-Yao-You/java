package com.hk.jigai.module.system.dal.dataobject.operationdeviceaccessoryhistory;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 运维设备配件表_快照 DO
 *
 * @author 邵志伟
 */
@TableName("hk_operation_device_accessory_history")
@KeySequence("hk_operation_device_accessory_history_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDeviceAccessoryHistoryDO extends BaseDO {

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
    /**
     * 快照Id
     */
    private Long historyId;

}