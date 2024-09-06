package com.hk.jigai.module.system.controller.admin.userreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 汇报对象 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportObjectRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "38")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "汇报对象用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21513")
    @ExcelProperty("汇报对象用户id")
    private Long uesrId;

    @Schema(description = "被汇报用户name", example = "12702")
    @ExcelProperty("被汇报用户name")
    private String userNickName;

    @Schema(description = "汇报用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "12702")
    @ExcelProperty("汇报用户id")
    private Long reportUserId;

    @Schema(description = "汇报用户name", example = "12702")
    @ExcelProperty("汇报用户name")
    private String reportUserNickName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}