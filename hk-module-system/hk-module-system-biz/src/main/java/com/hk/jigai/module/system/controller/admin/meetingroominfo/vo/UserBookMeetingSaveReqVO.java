package com.hk.jigai.module.system.controller.admin.meetingroominfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 用户预定会议记录新增/修改 Request VO")
@Data
public class UserBookMeetingSaveReqVO {
    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28028")
    private Long id;

    @Schema(description = "用户ID", example = "15793")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "用户联系电话")
    @NotEmpty(message = "用户联系电话不能为空")
    private String userPhone;

    @Schema(description = "会议室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21506")
    @NotNull(message = "会议室ID不能为空")
    private Long meetingRoomId;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "名称不能为空")
    private String subject;

    @Schema(description = "会议日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "会议日期不能为空")
    private LocalDate dateMeeting;

    @Schema(description = "会议开始", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "会议开始不能为空")
    private Integer startTime;

    @Schema(description = "会议结束", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "会议结束不能为空")
    private Integer endTime;

    @Schema(description = "设备")
    private Set<Long> equipment;

    @Schema(description = "总人数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总人数不能为空")
    private Integer capacity;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "参会人员", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> joinUserId;

}