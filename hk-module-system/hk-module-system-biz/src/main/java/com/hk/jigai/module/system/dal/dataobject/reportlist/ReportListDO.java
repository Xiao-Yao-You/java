package com.hk.jigai.module.system.dal.dataobject.reportlist;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 月报表列 DO
 *
 * @author 邵志伟
 */
@TableName("hk_report_list")
@KeySequence("hk_report_list_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportListDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 报表名称
     */
    private String reportTitle;
    /**
     * 报表月份
     */
    private String reportMonth;

}