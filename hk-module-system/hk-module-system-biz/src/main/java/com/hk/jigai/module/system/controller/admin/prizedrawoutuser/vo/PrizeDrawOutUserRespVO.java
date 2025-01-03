package com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 场外参与人员 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PrizeDrawOutUserRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11300")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "姓名", example = "李四")
    @ExcelProperty("姓名")
    private String nickname;

    @Schema(description = "部门名称")
    @ExcelProperty("部门名称")
    private String dept;

    @Schema(description = "工号")
    @ExcelProperty("工号")
    private String workNum;

    @Schema(description = "手机号")
    @ExcelProperty("手机号")
    private String mobile;

    @Schema(description = "中奖状态", example = "1")
    @ExcelProperty("中奖状态")
    private Integer status;

    @Schema(description = "奖品等级")
    @ExcelProperty("奖品等级")
    private Integer prizeLevel;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    private String prizePool;

    private Double winningRate;

    private Long activityBatch;

}