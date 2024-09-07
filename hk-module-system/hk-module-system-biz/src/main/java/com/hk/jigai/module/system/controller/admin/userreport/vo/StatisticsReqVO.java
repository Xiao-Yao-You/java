package com.hk.jigai.module.system.controller.admin.userreport.vo;

import com.hk.jigai.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;;
import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;


@Schema(description = "管理后台 - 用户汇报汇总 Request VO")
@Data
public class StatisticsReqVO extends PageParam {
    @Schema(description = "汇报日期")
    private List<String> dateReport;

    private String userId;

    private Integer offset;

}
