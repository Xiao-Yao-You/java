package com.hk.jigai.module.system.dal.dataobject.operation;

import com.hk.jigai.framework.mybatis.core.type.JsonLongSetTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工单 DO
 *
 * @author 超级管理员
 */
@TableName(value = "hk_operation_order", autoResultMap = true)
@KeySequence("hk_operation_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationOrderDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 工单编号
     */
    private String code;
    /**
     * 状态,00 待分配,01 待处理,02 进行中,03 挂起中,04 已处理,05 已完成,0501:无需处理,0502:无法排除故障,06 已撤销
     */
    private String status;
    /**
     * 设备id
     */
    private Long deviceId;
    /**
     * 标签code
     */
    private String labelCode;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备地点
     */
    private String addressId;

    @TableField(exist = false)
    private String address;

    /**
     * 设备位置
     */
    private String location;
    /**
     * 提交人id
     */
    private Long submitUserId;
    /**
     * 提交人姓名
     */
    private String submitUserNickName;
    /**
     * 提交人手机号
     */
    private String submitUserMobile;
    /**
     * 请求类型
     */
    private String requestType;
    /**
     * 问题类型
     */
    private Long questionType;
    /**
     * 紧急程度
     */
    private String level;
    /**
     * 描述
     */
    private String description;
    /**
     * 类型,00 主动接单,01 指派工单,02 转交工单
     */
    private Integer type;
    /**
     * 来源,1 线上报修,2 线下报修
     */
    private Integer sourceType;
    /**
     * 处理人id
     */
    private Long dealUserId;
    /**
     * 处理人名称
     */
    private String dealUserNickName;
    /**
     * 分配人员id
     */
    private Long allocationUserId;
    /**
     * 分配人员昵称
     */
    private String allocationUserNickName;
    /**
     * 分配时间
     */
    private LocalDateTime allocationTime;
    /**
     * 分配耗时
     */
    private Long allocationConsume;
    /**
     * 现成确认时间
     */
    private LocalDateTime siteConfirmTime;
    /**
     * 现成确认耗时
     */
    private Long siteDonfirmConsume;
    /**
     * 处理完成时间
     */
    private LocalDateTime dealTime;
    /**
     * 运维人员处理耗时
     */
    private Long dealConsume;
    /**
     * 挂起时间
     */
    private LocalDateTime hangUpTime;
    /**
     * 挂起耗时
     */
    private Long hangUpConsume;
    /**
     * 完成时间
     */
    private LocalDateTime completeTime;
    /**
     * 完成耗时
     */
    private Long completeConsume;

    @TableField(exist = false)
    private String questionTypeStr;

    /**
     * 完成结构
     */
    private Integer completeResult;

    @TableField(exist = false)
    private List<OperationOrderOperateRecordDO> recordList;

    /**
     * 日间还是夜间
     */
    private Integer dayNight;

    @TableField(exist = false)
    private String picture;

    @TableField(exist = false)
    private String dealUserMobile;
}