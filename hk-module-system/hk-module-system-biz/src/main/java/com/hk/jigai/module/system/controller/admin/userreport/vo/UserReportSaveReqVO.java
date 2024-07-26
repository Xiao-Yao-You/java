package com.hk.jigai.module.system.controller.admin.userreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 用户汇报新增/修改 Request VO")
@Data
public class UserReportSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "7668")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21060")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @Schema(description = "部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8060")
    @NotNull(message = "部门id不能为空")
    private Long deptId;

    @Schema(description = "汇报日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "汇报日期不能为空")
    private LocalDate dateReport;

    @Schema(description = "提交时间")
    private LocalDateTime commitTime;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "用户昵称", example = "王五")
    private String userNikeName;

    @Schema(description = "领导查看状态(00:未查看,01已查看)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "领导查看状态(00:未查看,01已查看)不能为空")
    private String checkSatus;

    @Schema(description = "类型(00:正常,01:补交,02:缺)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "类型(00:正常,01:补交,02:缺)不能为空")
    private String type;

}