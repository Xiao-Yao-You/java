package com.hk.jigai.module.system.controller.admin.userreport.vo;

import com.hk.jigai.module.system.dal.dataobject.userreport.UserSummaryReportDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 用户汇报统计 Response VO")
@Data
public class AttentionAlertRespVO {
    @Schema(description = "关注id")
    private Long id;

    @Schema(description = "内容")
    private String content;

}
