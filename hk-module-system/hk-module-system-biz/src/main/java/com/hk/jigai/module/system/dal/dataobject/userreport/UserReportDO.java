package com.hk.jigai.module.system.dal.dataobject.userreport;

import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户汇报 DO
 *
 * @author 超级管理员
 */
@TableName("hk_user_report")
@KeySequence("hk_user_report_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReportDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 汇报日期
     */
    private LocalDate dateReport;
    /**
     * 类型(00:正常,01:补交)
     */
    private String type;

    /**
     * 领导查看状态(00:未查看,01:已查看)
     */
    private String checkSatus;
    /**
     * 提交时间
     */
    private LocalDateTime commitTime;
    /**
     * 备注
     */
    private String remark;



}