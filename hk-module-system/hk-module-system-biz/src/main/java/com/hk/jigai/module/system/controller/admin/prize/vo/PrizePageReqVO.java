package com.hk.jigai.module.system.controller.admin.prize.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 奖品信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PrizePageReqVO extends PageParam {

    @Schema(description = "活动Id", example = "414")
    private Long activityId;

    @Schema(description = "奖品名称", example = "张三")
    private String prizeName;

    @Schema(description = "奖品总数量")
    private Integer prizeQuantity;

    @Schema(description = "奖品图片地址", example = "https://www.iocoder.cn")
    private String prizeUrl;

    @Schema(description = "奖品剩余数量")
    private String remainingQuantity;

    @Schema(description = "奖品等级")
    private int level;

}