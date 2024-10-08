package com.hk.jigai.module.system.controller.admin.operation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hk.jigai.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 批量生成标签 Request VO")
@Data
@ToString(callSuper = true)
public class OperationBatchCreateLabelReqVO  {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13379")
    private Long id;

    @Schema(description = "批量生成的数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private int num;
}