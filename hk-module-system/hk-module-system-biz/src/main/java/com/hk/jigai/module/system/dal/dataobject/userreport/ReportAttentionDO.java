package com.hk.jigai.module.system.dal.dataobject.userreport;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

import java.time.LocalDate;

/**
 * 汇报关注跟进 DO
 *
 * @author 超级管理员
 */
@TableName("hk_report_attention")
@KeySequence("hk_report_attention_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportAttentionDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 部门领导id
     */
    private Long userId;
    /**
     * 部门领导昵称
     */
    private String userNickName;
    /**
     * 部门领导批复
     */
    private String reply;
    /**
     * 0:工作进度,1:工作计划
     */
    private Integer type;
    /**
     * 工作进度、计划id
     */
    private Long jobId;
    /**
     * 关联待跟进的事项工作内容
     */
    private String connectContent;
    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 部门name
     */
    private String deptName;
    /**
     * 汇报日期
     */
    private LocalDate dateReport;

    /**
     * 汇报人id
     */
    private Long reportUserId;

    /**
     * 汇报人昵称
     */
    private String reportUserNickName;

    /**
     * 关注事项工作内容
     */
    private String content;
    /**
     * 转交备注
     */
    private String transferRemark;
    /**
     * 跟进人id
     */
    private Long replyUserId;

    /**
     * 跟进人昵称
     */
    private String replyUserNickName;
    /**
     * 跟进的完成情况
     */
    private String situation;
    /**
     * 跟进状态,0:未跟进,1:已跟进
     */
    private String replyStatus;
    /**
     * 跟进最新结果,新增记录,关联工作进度表
     */
    private Long jobScheduleId;

}