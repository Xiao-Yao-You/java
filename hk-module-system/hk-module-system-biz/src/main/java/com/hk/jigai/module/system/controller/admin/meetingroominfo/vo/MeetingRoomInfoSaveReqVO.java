package com.hk.jigai.module.system.controller.admin.meetingroominfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 会议室基本信息新增/修改 Request VO")
@Data
public class MeetingRoomInfoSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21588")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "位置(0:指挥部,1:厂区,2:生活区)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "位置(0:指挥部,1:厂区,2:生活区)不能为空")
    private Integer position;

    @Schema(description = "房间号")
    private String roomNo;

    @Schema(description = "容纳人数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "容纳人数不能为空")
    private Integer capacity;

    @Schema(description = "状态(0:开启,1:禁用)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态(0:开启,1:禁用)不能为空")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}