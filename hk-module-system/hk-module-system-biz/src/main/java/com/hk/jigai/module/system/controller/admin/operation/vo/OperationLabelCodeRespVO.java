package com.hk.jigai.module.system.controller.admin.operation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 运维标签code Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperationLabelCodeRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "32514")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "标签主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21277")
    @ExcelProperty("标签主键")
    private Long labelId;

    @Schema(description = "编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("编码")
    private String code;

    @Schema(description = "状态 0:空置,1:已关联设备", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态 0:空置,1:已关联设备")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}