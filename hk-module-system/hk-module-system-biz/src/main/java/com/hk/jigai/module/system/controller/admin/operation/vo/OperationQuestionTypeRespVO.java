package com.hk.jigai.module.system.controller.admin.operation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 运维问题类型 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperationQuestionTypeRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1618")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "父级问题类型", example = "12381")
    @ExcelProperty("父级问题类型")
    private Long parentId;

    @Schema(description = "类型(0:软件,1:硬件,2:其他)", example = "1")
    @ExcelProperty("类型(0:软件,1:硬件,2:其他)")
    private String type;

    @Schema(description = "设备类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备类型")
    private Long deviceTypeId;

    @Schema(description = "设备类型名称")
    @ExcelProperty("设备类型名称")
    private String deviceTypeName;

    @Schema(description = "描述", example = "随便")
    @ExcelProperty("描述")
    private String description;

    @Schema(description = "解决方案")
    @ExcelProperty("解决方案")
    private String solution;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建人")
    private String creator;

}