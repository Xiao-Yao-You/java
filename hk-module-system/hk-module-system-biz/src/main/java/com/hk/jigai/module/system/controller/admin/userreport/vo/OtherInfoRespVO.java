package com.hk.jigai.module.system.controller.admin.userreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 用户汇报其他信息 Response VO")
@Data
public class OtherInfoRespVO {

    @Schema(description = "待跟进项", example = "6")
    private int needFollow = 0;

    @Schema(description = "汇总数量，有值为true", example = "10")
    private boolean summaryFlag = false;

}

