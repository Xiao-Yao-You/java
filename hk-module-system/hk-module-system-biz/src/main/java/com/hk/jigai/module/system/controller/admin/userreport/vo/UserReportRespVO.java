package com.hk.jigai.module.system.controller.admin.userreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户汇报 Response VO")
@Data
@ExcelIgnoreUnannotated
public class UserReportRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "7668")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21060")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8060")
    @ExcelProperty("部门id")
    private Long deptId;

    @Schema(description = "汇报日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("汇报日期")
    private LocalDate dateReport;

    @Schema(description = "类型(00:正常,01:补交)", requiredMode = Schema.RequiredMode.REQUIRED, example = "00")
    @ExcelProperty("类型(00:正常,01:补交)")
    private String type;

    @Schema(description = "领导查看状态(00:未查看,01:已查看)", example = "00")
    @ExcelProperty("领导查看状态(00:未查看,01:已查看)")
    private String checkSatus;

    @Schema(description = "提交时间")
    @ExcelProperty("提交时间")
    private LocalDateTime commitTime;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}