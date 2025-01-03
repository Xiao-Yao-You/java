package com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 场外参与人员分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PrizeDrawOutUserPageReqVO extends PageParam {

    @Schema(description = "姓名", example = "李四")
    private String nickname;

    @Schema(description = "部门名称")
    private String dept;

    @Schema(description = "工号")
    private String workNum;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "中奖状态", example = "1")
    private Integer status;

    @Schema(description = "奖品等级")
    private Integer prizeLevel;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    private String prizePool;

    private Double winningRate;

    private Long activityBatch;

}