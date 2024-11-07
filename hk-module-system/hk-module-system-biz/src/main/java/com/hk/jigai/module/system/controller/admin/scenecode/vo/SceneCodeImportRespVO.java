package com.hk.jigai.module.system.controller.admin.scenecode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 单据编码导入 Response VO")
@Data
@Builder
public class SceneCodeImportRespVO {

    @Schema(description = "创建成功的数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createList;

    @Schema(description = "更新成功的数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateList;

    @Schema(description = "导入失败的集合，key 为用户名，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureList;

}
