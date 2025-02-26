package com.hk.jigai.module.system.controller.admin.operationgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.*;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 运维小组 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperationGroupRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21056")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "用户id集合")
    @ExcelProperty("用户id集合")
    private Set<Long> userIds;

    @Schema(description = "分组")
    @ExcelProperty("分组")
    private String group;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}