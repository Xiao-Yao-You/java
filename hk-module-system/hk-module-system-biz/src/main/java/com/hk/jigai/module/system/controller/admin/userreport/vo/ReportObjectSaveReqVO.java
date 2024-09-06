package com.hk.jigai.module.system.controller.admin.userreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 汇报对象新增/修改 Request VO")
@Data
public class ReportObjectSaveReqVO {

    @Schema(description = "主键", example = "38")
    private Long id;

    @Schema(description = "被汇报用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21513")
    @NotNull(message = "被汇报用户id不能为空")
    private Long uesrId;

    @Schema(description = "被汇报用户name", example = "12702")
    private String userNickName;

    @Schema(description = "汇报用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12702")
    @NotNull(message = "汇报用户id不能为空")
    private Long reportUserId;

    @Schema(description = "汇报用户name", example = "12702")
    private String reportUserNickName;
}