package com.hk.jigai.module.system.controller.admin.userreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;;
import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;


@Schema(description = "管理后台 - 用户汇报汇总 Request VO")
@Data
public class StatisticsReqVO {

    @Schema(description = "部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8060")
    private Long deptId;

    @Schema(description = "汇报日期")
//    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private List<String> dateReport;

    private String userId;

}
