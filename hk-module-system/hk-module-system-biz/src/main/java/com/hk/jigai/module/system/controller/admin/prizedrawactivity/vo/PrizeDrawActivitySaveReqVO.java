package com.hk.jigai.module.system.controller.admin.prizedrawactivity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 抽奖活动新增/修改 Request VO")
@Data
public class PrizeDrawActivitySaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "活动名称", example = "年会")
    private String activityName;

    @Schema(description = "开始时间")
    private LocalDateTime beginTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "参与人数")
    private Integer participantsQuantity;

}