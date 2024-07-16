package com.hk.jigai.module.system.controller.admin.meetingroominfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户预定会议记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class UserBookMeetingRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28028")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15793")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "用户昵称", example = "15793")
    @ExcelProperty("用户昵称")
    private Long userNickName;

    @Schema(description = "用户联系电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("用户联系电话")
    private String userPhone;

    @Schema(description = "会议室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21506")
    @ExcelProperty("会议室ID")
    private Long meetingRoomId;

    @Schema(description = "会议室名称",  example = "21506")
    @ExcelProperty("会议室名称")
    private Long meetingRoomName;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("名称")
    private String subject;

    @Schema(description = "会议日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("会议日期")
    private LocalDate dateMeeting;

    @Schema(description = "会议开始", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("会议开始")
    private Integer startTime;

    @Schema(description = "会议结束", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("会议结束")
    private Integer endTime;

    @Schema(description = "设备")
    @ExcelProperty("设备")
    private Set<Long> equipment;

    @Schema(description = "总人数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("总人数")
    private Integer capacity;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "参会人员列表")
    private List<Long> joinUserIdList;

    @Schema(description = "状态")
    private Integer status;

}