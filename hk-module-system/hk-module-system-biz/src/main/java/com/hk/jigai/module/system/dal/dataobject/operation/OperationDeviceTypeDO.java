package com.hk.jigai.module.system.dal.dataobject.operation;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 运维设备类型 DO
 *
 * @author 超级管理员
 */
@TableName("hk_operation_device_type")
@KeySequence("hk_operation_device_type_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDeviceTypeDO extends BaseDO {

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
     * 编码规则
     */
    private Long sceneCodeId;

    /**
     * 编码名称
     */
    private String sceneName;
    /**
     * 状态 0:已启用,1:未启用
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 当前编号，为空则是还未使用
     */
    private String currentCode;

    /**
     * 标签编码规则
     */
    private Long labelSceneCodeId;

    /**
     * 标签编码名称
     */
    private String labelSceneName;

    /**
     * 标签当前编码
     */
    private String labelCurrentCode;
}