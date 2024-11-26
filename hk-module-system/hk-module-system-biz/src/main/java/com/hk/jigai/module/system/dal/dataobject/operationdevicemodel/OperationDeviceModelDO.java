package com.hk.jigai.module.system.dal.dataobject.operationdevicemodel;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备型号 DO
 *
 * @author 邵志伟
 */
@TableName("hk_operation_device_model")
@KeySequence("hk_operation_device_model_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDeviceModelDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 型号
     */
    private String model;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 设备类型id
     */
    private Long deviceTypeId;

}