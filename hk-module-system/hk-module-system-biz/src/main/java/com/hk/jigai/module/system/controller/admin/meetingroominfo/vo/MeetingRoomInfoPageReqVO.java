package com.hk.jigai.module.system.controller.admin.meetingroominfo.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 会议室基本信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MeetingRoomInfoPageReqVO extends PageParam {

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "位置(0:指挥部,1:厂区,2:生活区)")
    private Integer position;

    @Schema(description = "房间号")
    private String roomNo;

    @Schema(description = "容纳人数")
    private Integer[] capacityList;

    @Schema(description = "状态(0:开启,1:禁用)", example = "1")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}