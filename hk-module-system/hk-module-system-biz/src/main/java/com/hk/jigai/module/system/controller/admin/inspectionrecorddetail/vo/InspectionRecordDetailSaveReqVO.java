package com.hk.jigai.module.system.controller.admin.inspectionrecorddetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 巡检记录详情新增/修改 Request VO")
@Data
public class InspectionRecordDetailSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30269")
    private Long id;

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

}