package com.hk.jigai.module.system.controller.admin.userreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 汇报转交记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReportTransferRecordRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "14701")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "关注主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "31498")
    @ExcelProperty("关注主键")
    private Long reportAttentionId;

    @Schema(description = "操作转交人员id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28309")
    @ExcelProperty("操作转交人员id")
    private Long operatorUserId;

    @Schema(description = "操作转交人员姓名", example = "张三")
    @ExcelProperty("操作转交人员姓名")
    private String operatorNickName;

    @Schema(description = "转交备注", example = "你猜")
    @ExcelProperty("转交备注")
    private String transferRemark;

    @Schema(description = "跟进人id", example = "22464")
    @ExcelProperty("跟进人id")
    private Long replyUserId;

    @Schema(description = "跟进人姓名", example = "张三")
    @ExcelProperty("跟进人姓名")
    private String replyUserNickName;

    @Schema(description = "转交时间")
    @ExcelProperty("转交时间")
    private LocalDateTime transferTime;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}