package com.hk.jigai.module.system.dal.dataobject.prize;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 奖品信息 DO
 *
 * @author 邵志伟
 */
@TableName("hk_prize")
@KeySequence("hk_prize_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrizeDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 活动Id
     */
    private Long activityId;
    /**
     * 奖品名称
     */
    private String prizeName;
    /**
     * 奖品总数量
     */
    private Integer prizeQuantity;
    /**
     * 奖品图片地址
     */
    private String prizeUrl;
    /**
     * 奖品剩余数量
     */
    private String remainingQuantity;

    /**
     * 奖品等级
     */
    private int level;

    /**
     * 活动名称
     */
    private String activityName;

}