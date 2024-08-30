package com.hk.jigai.module.system.dal.dataobject.userreport;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 汇报转交记录 DO
 *
 * @author 超级管理员
 */
@TableName("hk_report_transfer_record")
@KeySequence("hk_report_transfer_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportTransferRecordDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 关注主键
     */
    private Long reportAttentionId;
    /**
     * 操作转交人员id
     */
    private Long operatorUserId;
    /**
     * 操作转交人员姓名
     */
    private String operatorNickName;
    /**
     * 转交备注
     */
    private String transferRemark;
    /**
     * 跟进人id
     */
    private Long replyUserId;
    /**
     * 跟进人姓名
     */
    private String replyUserNickName;
    /**
     * 转交时间
     */
    private LocalDateTime transferTime;

}