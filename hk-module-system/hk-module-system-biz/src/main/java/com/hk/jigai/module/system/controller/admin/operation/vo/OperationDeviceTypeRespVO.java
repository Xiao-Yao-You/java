package com.hk.jigai.module.system.controller.admin.operation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 运维设备类型 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperationDeviceTypeRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "15459")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "编码规则", requiredMode = Schema.RequiredMode.REQUIRED, example = "14994")
    @ExcelProperty("编码规则")
    private Long sceneCodeId;

    @Schema(description = "编码名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    @ExcelProperty("编码名称")
    private String sceneName;

    @Schema(description = "当前编码", example = "000001")
    @ExcelProperty("当前编码")
    private String currentCode;

    @Schema(description = "标签编码规则", requiredMode = Schema.RequiredMode.REQUIRED, example = "14994")
    @ExcelProperty("标签编码规则")
    private Long labelSceneCodeId;

    @Schema(description = "标签编码名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    @ExcelProperty("标签编码名称")
    private String labelSceneName;

    @Schema(description = "标签当前编码", example = "000001")
    @ExcelProperty("标签当前编码")
    private String labelCurrentCode;

    @Schema(description = "状态 0:已启用,1:未启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态 0:已启用,1:未启用")
    private Integer status;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建人")
    private String creator;



}