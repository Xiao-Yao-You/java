package com.hk.jigai.module.system.dal.dataobject.operation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 运维设备 DO
 *
 * @author 超级管理员
 */
@TableName("hk_operation_device")
@KeySequence("hk_operation_device_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDeviceDO extends BaseDO {

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
     * 设备编码
     */
    private String code;
    /**
     * 设备类型
     */
    private Long deviceType;
    /**
     * 设备类型描述
     */
    private String deviceTypeName;
    /**
     * 型号
     */
    private String model;
    /**
     * 标签code
     */
    private String labelCode;
    /**
     * 状态 0:在用,1:闲置,2:报废
     */
    private Integer status;
    /**
     * 所属单位 0:恒科,1:轩达,2:其他
     */
    private Integer company;
    /**
     * 序列号
     */
    private String serialNumber;
    /**
     * 影响程度
     */
    private String effectLevel;
    /**
     * 编号名称
     */
    private Long numberName;
    /**
     * 影响程度
     */
    private String assetNumber;
    /**
     * mac地址1
     */
    private String macAddress1;
    /**
     * mac地址2
     */
    private String macAddress2;
    /**
     * 生产日期
     */
    private LocalDate manufactureDate;
    /**
     * 质保日期
     */
    private LocalDate warrantyDate;
    /**
     * 是否需要巡检，0:是 1:否
     */
    private Integer needCheckFlag;

    /**
     * 设备部门
     */
    private Long deptId;
    /**
     * 设备部门名称
     */
    private String deptName;
    /**
     * 使用人
     */
    private Long userId;

    private String userNickName;
    /**
     * 使用地点
     */
    private Long addressId;

    /**
     * 地址
     */
    private String address;
    /**
     * 设备位置
     */
    private String location;
    /**
     * ip1
     */
    private String ip1;
    /**
     * ip2
     */
    private String ip2;
    /**
     * 设备分配登记人
     */
    private Long registerUserId;

    /**
     * 设备分配登记人
     */
    private String registerUserName;
    /**
     * 设备分配登记时间
     */
    private LocalDateTime registerDate;
    /**
     * 报废时间
     */
    private LocalDate scrapDate;
    /**
     * 报废类型
     */
    private String scrapType;
    /**
     * 报废处理人
     */
    private Long scrapUserId;

    private String scrapUserName;
    /**
     * 报废处理方式
     */
    private String scrapDealType;
    /**
     * 报废说明
     */
    private String scrapRemark;

    @TableField(exist = false)
    private String questionTypeStr;

}