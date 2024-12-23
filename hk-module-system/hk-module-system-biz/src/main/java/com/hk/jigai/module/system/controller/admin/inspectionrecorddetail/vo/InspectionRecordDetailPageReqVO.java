package com.hk.jigai.module.system.controller.admin.inspectionrecorddetail.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 巡检记录详情分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InspectionRecordDetailPageReqVO extends PageParam {

    @Schema(description = "记录id", example = "4312")
    private Long recordId;

    @Schema(description = "巡检项目id", example = "31649")
    private Long inspectionProjectId;

    @Schema(description = "巡检项目")
    private String inspectionProject;

    @Schema(description = "巡检项目指标")
    private String inspectionIndicators;

    @Schema(description = "巡检结构")
    private Integer result;

    @Schema(description = "说明/原因", example = "你猜")
    private String remark;

    @Schema(description = "附件")
    private String filePath;

    @Schema(description = "工单id", example = "1548")
    private Long orderId;

    @Schema(description = "整改状态", example = "2")
    private Integer 
correctionStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}