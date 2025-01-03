package com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 场外参与人员新增/修改 Request VO")
@Data
public class PrizeDrawOutUserSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11300")
    private Long id;

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

    private String prizePool;

    private Double winningRate;

    private Long activityBatch;

}