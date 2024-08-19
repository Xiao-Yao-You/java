package com.hk.jigai.module.system.controller.admin.userreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 汇报关注跟进 转交 Request VO")
@Data
public class ReportAttentionFollowReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "24197")
    private Long id;

    @Schema(description = "跟进的完成情况", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    private String situation;

    private String replyStatus;

}