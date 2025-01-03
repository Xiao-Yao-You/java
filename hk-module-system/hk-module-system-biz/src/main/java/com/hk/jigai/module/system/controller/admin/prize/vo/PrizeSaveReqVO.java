package com.hk.jigai.module.system.controller.admin.prize.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 奖品信息新增/修改 Request VO")
@Data
public class PrizeSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "24141")
    private Long id;

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