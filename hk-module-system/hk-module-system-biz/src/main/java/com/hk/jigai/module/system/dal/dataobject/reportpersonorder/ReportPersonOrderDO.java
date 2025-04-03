package com.hk.jigai.module.system.dal.dataobject.reportpersonorder;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 个人工单处理月报 DO
 *
 * @author 邵志伟
 */
@TableName("hk_report_person_order")
@KeySequence("hk_report_person_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportPersonOrderDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 报表月份
     */
    private String reportMonth;
    /**
     * 运维人员Id
     */
    private Long userId;
    /**
     * 运维人员姓名
     */
    private String username;
    /**
     * 处置工单总数量
     */
    private Integer totalOrderCount;
    /**
     * 已完成的工单数量
     */
    private Integer completeOrderCount;
    /**
     * 处理中的工单数量
     */
    private Integer processingOrderCount;
    /**
     * 平均处理时长
     */
    private Long aht;
    /**
     * 处理总时长
     */
    private Long totalHandleTime;
    /**
     * 处理-工作时间占比
     */
    private BigDecimal timeProportion;
    /**
     * 紧急程度-工单数量分布
     */
    private String urgencyLevelDistribution;
    /**
     * 接单类型-工单数量分布
     */
    private String orderTypeDistribution;
    /**
     * 主动接单占比
     */
    private BigDecimal orderAcceptedProportion;
    /**
     * 挂起的工单数量
     */
    private Integer pendingOrderCount;
    /**
     * 挂起总时长
     */
    private Long pendingTotalTime;
    /**
     * 平均挂起时长
     */
    private Long apt;
    /**
     * 按时完成率
     */
    private BigDecimal onTimeCompletionRate;
    /**
     * 单日最大处理量
     */
    private String dailyHandleMax;
    /**
     * 环比增长量
     */
    private Integer monthOnMonthGrowth;
    /**
     * 环比增长率
     */
    private BigDecimal monthOnMonthGrowthRate;

    /**
     * 处置超时次数
     */
    private Integer handleTimeoutTimes;

    /**
     * 处置超时率
     */
    private BigDecimal handleTimeoutRate;

}