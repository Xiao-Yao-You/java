package com.hk.jigai.module.system.controller.admin.meetingroominfo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hk.jigai.framework.mybatis.core.type.JsonLongSetTypeHandler;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 用户预定会议记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserBookMeetingPageReqVO extends PageParam {

    @Schema(description = "用户ID", example = "15793")
    private Long userId;

    @Schema(description = "用户联系电话")
    private String userPhone;

    @Schema(description = "会议室ID", example = "21506")
    private Long meetingRoomId;

    @Schema(description = "名称")
    private String subject;

    @Schema(description = "会议日期")
    private LocalDate dateMeeting;

    @Schema(description = "会议开始")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] startTime;

    @Schema(description = "会议结束")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] endTime;

    @Schema(description = "设备")
    private Set<Long> equipment;

    @Schema(description = "总人数")
    private Integer capacity;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}