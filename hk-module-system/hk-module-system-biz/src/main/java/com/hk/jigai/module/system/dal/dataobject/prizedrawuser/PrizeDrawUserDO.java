package com.hk.jigai.module.system.dal.dataobject.prizedrawuser;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 抽奖用户 DO
 *
 * @author 邵志伟
 */
@TableName("hk_prize_draw_user")
@KeySequence("hk_prize_draw_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrizeDrawUserDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 用户头像地址
     */
    private String headimgurl;
    /**
     * openid
     */
    private String openid;
    /**
     * 活动批次
     */
    private Long activityBatch;
    /**
     * 中奖率
     */
    private Double winningRate;

    private String prizePool;

    private Integer status;

    private Integer prizeLevel;

    private Long currentNum;



}