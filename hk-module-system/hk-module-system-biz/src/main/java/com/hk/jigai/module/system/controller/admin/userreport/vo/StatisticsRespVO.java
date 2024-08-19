package com.hk.jigai.module.system.controller.admin.userreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 用户汇报统计 Response VO")
@Data
public class StatisticsRespVO {
    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21060")
    private Long userId;

    @Schema(description = "用户昵称", example = "王五")
    private String userNickName;

    @Schema(description = "提交次数", example = "10")
    private Integer submitNum;

    @Schema(description = "未提交次数", example = "20")
    private Integer unSubmitNum;

}
