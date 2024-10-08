package com.hk.jigai.module.system.controller.admin.operation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 运维标签批量打印 Response VO")
@Data
public class BatchPrintLabelRespVO {
    @Schema(description = "设备类型名称", example = "电脑")
    private String name;

    @Schema(description = "标签code", example = "00001")
    private String labelCode;
}
