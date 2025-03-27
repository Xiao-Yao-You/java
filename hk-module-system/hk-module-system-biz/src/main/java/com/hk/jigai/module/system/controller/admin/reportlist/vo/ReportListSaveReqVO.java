package com.hk.jigai.module.system.controller.admin.reportlist.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 月报表列新增/修改 Request VO")
@Data
public class ReportListSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "29585")
    private Long id;

    @Schema(description = "报表名称")
    private String reportTitle;

    @Schema(description = "报表月份")
    private String reportMonth;

}