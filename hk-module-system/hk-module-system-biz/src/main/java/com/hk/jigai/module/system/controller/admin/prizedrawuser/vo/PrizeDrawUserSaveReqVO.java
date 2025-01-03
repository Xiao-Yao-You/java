package com.hk.jigai.module.system.controller.admin.prizedrawuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 抽奖用户新增/修改 Request VO")
@Data
public class PrizeDrawUserSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13285")
    private Long id;

    @Schema(description = "昵称", example = "李四")
    private String nickname;

    @Schema(description = "用户头像地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotEmpty(message = "用户头像地址不能为空")
    private String headimgurl;

    @Schema(description = "openid", example = "21357")
    private String openid;

    @Schema(description = "活动批次")
    private Long activityBatch;

    @Schema(description = "中奖率")
    private Double winningRate;

    private String prizePool;

    /**
     * 微信code
     */
    private String code;

}