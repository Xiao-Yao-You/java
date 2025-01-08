package com.hk.jigai.module.system.dal.dataobject.prizedrawactivity;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 抽奖活动 DO
 *
 * @author 邵志伟
 */
@TableName("hk_prize_draw_activity")
@KeySequence("hk_prize_draw_activity_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrizeDrawActivityDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 参与人数
     */
    private Integer participantsQuantity;

    /**
     * 二维码ticket
     */
    private String ticket;
    @TableField(exist = false)
    private String status;

}