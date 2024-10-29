package com.hk.jigai.module.system.controller.admin.operationdevicepicturehistory.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 运维设备照片表_快照 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperationDevicePictureHistoryRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "326")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7499")
    @ExcelProperty("设备id")
    private Long deviceId;

    @Schema(description = "类型0:设备照片,1:现场照片,2:报废照片", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("类型0:设备照片,1:现场照片,2:报废照片")
    private String type;

    @Schema(description = "图片URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @ExcelProperty("图片URL")
    private String url;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "快照Id", example = "31866")
    @ExcelProperty("快照Id")
    private Long historyId;

}