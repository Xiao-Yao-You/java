package com.hk.jigai.module.system.controller.admin.prizedrawuser.vo;

import lombok.*;

import java.util.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;

import java.math.BigDecimal;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 抽奖用户分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PrizeDrawUserPageReqVO extends PageParam {

    @Schema(description = "昵称", example = "李四")
    private String nickname;

    @Schema(description = "用户头像地址", example = "https://www.iocoder.cn")
    private String headimgurl;

    @Schema(description = "openid", example = "21357")
    private String openid;

    @Schema(description = "活动批次")
    private Long activityBatch;

    @Schema(description = "中奖率")
    private Double winningRate;

    private String prizePool;

}