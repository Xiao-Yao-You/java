package com.hk.jigai.module.system.controller.admin.operation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 运维地点 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperationAddressRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1123")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("名称")
    private String addressName;

    @Schema(description = "上级地址id", example = "23754")
    @ExcelProperty("上级地址id")
    private Long parentAddressId;

    @Schema(description = "区域软件负责人", requiredMode = Schema.RequiredMode.REQUIRED, example = "22328")
    @ExcelProperty("区域软件负责人")
    private Long softUserId;

    @Schema(description = "区域软件负责人昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("区域软件负责人昵称")
    private String softUserNickName;

    @Schema(description = "区域硬件负责人", requiredMode = Schema.RequiredMode.REQUIRED, example = "3479")
    @ExcelProperty("区域硬件负责人")
    private Long hardwareUserId;

    @Schema(description = "区域硬件负责人昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("区域硬件负责人昵称")
    private String hardwareUserNickName;

    @Schema(description = "状态 0:已启用,1:未启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态 0:已启用,1:未启用")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}