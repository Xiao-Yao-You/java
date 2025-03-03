package com.hk.jigai.module.system.controller.admin.operationgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 运维小组新增/修改 Request VO")
@Data
public class OperationGroupSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21056")
    private Long id;

    @Schema(description = "用户id集合")
    private Set<Long> userIds;

    @Schema(description = "分组")
    private String groupId;

}