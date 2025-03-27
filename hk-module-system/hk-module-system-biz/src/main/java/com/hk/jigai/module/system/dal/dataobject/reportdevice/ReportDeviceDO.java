package com.hk.jigai.module.system.dal.dataobject.reportdevice;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备资产报 DO
 *
 * @author 邵志伟
 */
@TableName("hk_report_device")
@KeySequence("hk_report_device_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDeviceDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 报表日期
     */
    private String reportMonth;
    /**
     * 公司id
     */
    private Long companyId;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 设备数量
     */
    private Integer deviceCount;
    /**
     * 设备状态分布
     */
    private String stateDistribution;
    /**
     * 影响程度分布
     */
    private String effectDistribution;
    /**
     * 设备部门分布
     */
    private String deptDistribution;
    /**
     * 故障率
     */
    private BigDecimal failureRate;

}