package com.hk.jigai.module.system.controller.admin.scenecode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 单据编码类型配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SceneCodeRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19424")
    @ExcelProperty("主键")
    private Integer id;

    @Schema(description = "编码key", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("编码key")
    private String key;

    @Schema(description = "描述", example = "随便")
    @ExcelProperty("描述")
    private String description;

    @Schema(description = "编码前缀", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("编码前缀")
    private String prefix;

    @Schema(description = "编码中缀", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("编码中缀")
    private String infix;

    @Schema(description = "编码后缀", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("编码后缀")
    private String suffix;

    @Schema(description = "编码规则", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("编码规则")
    private String type;

    @Schema(description = "起始值", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("起始值")
    private Integer start;

    @Schema(description = "步长", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("步长")
    private Integer step;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态")
    private String status;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}