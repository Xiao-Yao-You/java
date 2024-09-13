package com.hk.jigai.module.system.dal.dataobject.userreport;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 汇报工作进度 DO
 *
 * @author 超级管理员
 */
@TableName("hk_report_job_schedule")
@KeySequence("hk_report_job_schedule_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportJobScheduleDO extends BaseDO {

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
     * 完成情况
     */
    private String situation;
    /**
     * 关联待跟进的事项工作内容
     */
    private String connectContent;
    /**
     * 关联待跟进的事项id
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long connectId;

    /**
     * 关注主键
     */
    @TableField(exist = false)
    private Long attentionId;

}