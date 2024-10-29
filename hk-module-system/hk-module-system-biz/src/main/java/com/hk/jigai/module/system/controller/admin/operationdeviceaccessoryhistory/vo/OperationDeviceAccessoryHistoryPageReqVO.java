package com.hk.jigai.module.system.controller.admin.operationdeviceaccessoryhistory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 运维设备配件表_快照分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OperationDeviceAccessoryHistoryPageReqVO extends PageParam {

    @Schema(description = "设备id", example = "18439")
    private Long deviceId;

    @Schema(description = "配件描述")
    private String accessoryDesc;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "数量")
    private String num;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "快照Id", example = "25814")
    private Long historyId;

}