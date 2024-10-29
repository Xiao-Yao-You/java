package com.hk.jigai.module.system.controller.admin.operationdevicepicturehistory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 运维设备照片表_快照分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OperationDevicePictureHistoryPageReqVO extends PageParam {

    @Schema(description = "设备id", example = "7499")
    private Long deviceId;

    @Schema(description = "类型0:设备照片,1:现场照片,2:报废照片", example = "1")
    private String type;

    @Schema(description = "图片URL", example = "https://www.iocoder.cn")
    private String url;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "快照Id", example = "31866")
    private Long historyId;

}