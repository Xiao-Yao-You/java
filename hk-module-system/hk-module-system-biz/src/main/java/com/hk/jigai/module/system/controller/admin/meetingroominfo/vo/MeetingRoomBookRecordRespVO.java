package com.hk.jigai.module.system.controller.admin.meetingroominfo.vo;

import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingRoomBookRecordDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 会议室预定记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MeetingRoomBookRecordRespVO {
    @Schema(description = "会议室ID", example = "18037")
    private Long meetingRoomId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "位置(0:指挥部,1:厂区,2:生活区)")
    private Integer position;

    @Schema(description = "容纳人数")
    private Integer capacity;

    @Schema(description = "列表")
    private List<MeetingRoomBookRecordDO> list;
}