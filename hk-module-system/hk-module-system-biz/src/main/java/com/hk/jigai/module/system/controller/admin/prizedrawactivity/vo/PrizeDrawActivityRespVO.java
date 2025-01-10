package com.hk.jigai.module.system.controller.admin.prizedrawactivity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 抽奖活动 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PrizeDrawActivityRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "活动名称", example = "年会")
    @ExcelProperty("活动名称")
    private String activityName;

    @Schema(description = "开始时间")
    @ExcelProperty("开始时间")
    private Date beginTime;

    @Schema(description = "结束时间")
    @ExcelProperty("结束时间")
    private Date endTime;

    @Schema(description = "参与人数")
    @ExcelProperty("参与人数")
    private Integer participantsQuantity;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
    private String status;
    @Schema(description = "二维码ticket")
    private String ticket;

}