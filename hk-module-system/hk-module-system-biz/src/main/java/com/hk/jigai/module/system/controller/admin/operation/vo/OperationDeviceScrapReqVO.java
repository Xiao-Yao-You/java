package com.hk.jigai.module.system.controller.admin.operation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 运维设备新增/修改 Request VO")
@Data
public class OperationDeviceScrapReqVO {

    @Schema(description = "设备主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "报废时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate scrapDate;

    @Schema(description = "报废类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String scrapType;

    @Schema(description = "报废处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "10844")
    private Long scrapUserId;

    @Schema(description = "报废处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "10844")
    private String scrapUserName;

    @Schema(description = "报废处理方式", requiredMode = Schema.RequiredMode.REQUIRED)
    private String scrapDealType;

    @Schema(description = "报废说明", example = "你说的对", requiredMode = Schema.RequiredMode.REQUIRED)
    private String scrapRemark;

    @Schema(description = "设备照片", requiredMode = Schema.RequiredMode.REQUIRED)
    List<OperationDevicePictureSaveReqVO> pictureList;
}