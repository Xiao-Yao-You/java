package com.hk.jigai.module.system.controller.admin.prizedrawuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 抽奖用户 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PrizeDrawUserRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13285")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "昵称", example = "李四")
    @ExcelProperty("昵称")
    private String nickname;

    @Schema(description = "用户头像地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @ExcelProperty("用户头像地址")
    private String headimgurl;

    @Schema(description = "openid", example = "21357")
    @ExcelProperty("openid")
    private String openid;

    @Schema(description = "活动批次")
    @ExcelProperty("活动批次")
    private Long activityBatch;

    @Schema(description = "中奖率")
    @ExcelProperty("中奖率")
    private Double winningRate;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    private String prizePool;

    private String accessToken;

    private String refreshToken;

    private Long currentNum;

}