package com.hk.jigai.module.system.controller.admin.prize.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 奖品信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PrizeRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "24141")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "活动Id", example = "414")
    @ExcelProperty("活动Id")
    private Long activityId;

    @Schema(description = "奖品名称", example = "张三")
    @ExcelProperty("奖品名称")
    private String prizeName;

    @Schema(description = "奖品总数量")
    @ExcelProperty("奖品总数量")
    private Integer prizeQuantity;

    @Schema(description = "奖品图片地址", example = "https://www.iocoder.cn")
    @ExcelProperty("奖品图片地址")
    private String prizeUrl;

    @Schema(description = "奖品剩余数量")
    @ExcelProperty("奖品剩余数量")
    private String remainingQuantity;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "奖品等级")
    private int level;

    private String activityName;

}