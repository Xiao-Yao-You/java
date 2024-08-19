package com.hk.jigai.module.system.controller.admin.userreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 用户汇报添加关注 Request VO")
@Data
public class CreateAttentionReqVO {
    @Schema(description = "部门领导批复", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reply;

    @Schema(description = "关注类型 0:工作进度,1:工作计划", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer type;

    @Schema(description = "关注事项id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long jobId;

    @Schema(description = "关注的关联事项的内容")
    private String connectContent;

}
