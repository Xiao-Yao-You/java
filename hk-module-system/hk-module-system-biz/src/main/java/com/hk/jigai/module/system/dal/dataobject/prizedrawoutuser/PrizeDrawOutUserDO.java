package com.hk.jigai.module.system.dal.dataobject.prizedrawoutuser;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 场外参与人员 DO
 *
 * @author 邵志伟
 */
@TableName("hk_prize_draw_out_user")
@KeySequence("hk_prize_draw_out_user_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrizeDrawOutUserDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 姓名
     */
    private String nickname;
    /**
     * 部门名称
     */
    private String dept;
    /**
     * 工号
     */
    private String workNum;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 中奖状态
     */
    private Integer status;
    /**
     * 奖品等级
     */
    private Integer prizeLevel;

    private String prizePool;

    private Double winningRate;

    private Long activityBatch;

}