package com.hk.jigai.module.system.controller.admin.userreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "管理后台 - 汇报关注跟进 转交 Request VO")
@Data
public class ReportAttentionTransferReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "24197")
    private Long id;

    @Schema(description = "转交备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    private String transferRemark;

    @Schema(description = "跟进人id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11207")
    private Long replyUserId;

    @Schema(description = "跟进人昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "wft")
    private String replyUserNickName;

    @Schema(description = "跟进最新结果,新增记录,关联工作进度表", example = "19256")
    private Long jobScheduleId;

}