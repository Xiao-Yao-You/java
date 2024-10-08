package com.hk.jigai.module.system.controller.admin.operation.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 运维问题类型 Request VO")
@Data
@ToString(callSuper = true)
public class OperationQuestionTypeReqVO {

    @Schema(description = "名称", example = "王五")
    private String name;

    @Schema(description = "父级问题类型", example = "12381")
    private Long parentId;

    @Schema(description = "类型(0:软件,1:硬件,2:其他)", example = "1")
    private String type;

    @Schema(description = "设备类型id")
    private Long deviceTypeId;

    @Schema(description = "设备类型名称")
    private String deviceTypeName;

    @Schema(description = "描述", example = "随便")
    private String description;

    @Schema(description = "解决方案")
    private String solution;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}