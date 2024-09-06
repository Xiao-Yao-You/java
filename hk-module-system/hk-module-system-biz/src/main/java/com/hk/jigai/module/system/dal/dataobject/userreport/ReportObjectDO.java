package com.hk.jigai.module.system.dal.dataobject.userreport;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

import javax.validation.constraints.NotNull;

/**
 * 汇报对象 DO
 *
 * @author 超级管理员
 */
@TableName("hk_report_object")
@KeySequence("hk_report_object_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportObjectDO extends BaseDO {
    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 被汇报对象用户id
     */
    private Long userId;

    /**
     * 被汇报用户昵称
     */
    private String userNickName;

    /**
     * 汇报对象用户id
     */
    private Long reportUserId;

    /**
     * 汇报用户昵称
     */
    private String reportUserNickName;
}