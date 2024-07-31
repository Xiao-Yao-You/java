package com.hk.jigai.module.system.dal.dataobject.reportjobplan;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 汇报工作计划 DO
 *
 * @author 超级管理员
 */
@TableName("hk_report_job_plan")
@KeySequence("hk_report_job_plan_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportJobPlanDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 汇报id
     */
    private Long userReportId;
    /**
     * 序号
     */
    private Integer sort;
    /**
     * 工作内容
     */
    private String content;
    /**
     * 预计时间
     */
    private String estimatedTime;
    /**
     * 需要的资源
     */
    private String needSource;

}