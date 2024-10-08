package com.hk.jigai.module.system.controller.admin.operation.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 运维地点分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OperationAddressPageReqVO extends PageParam {

    @Schema(description = "名称", example = "赵六")
    private String addressName;

    @Schema(description = "上级地址id", example = "23754")
    private Long parentAddressId;

    @Schema(description = "区域软件负责人", example = "22328")
    private Long softUserId;

    @Schema(description = "区域软件负责人昵称", example = "张三")
    private String softUserNickName;

    @Schema(description = "区域硬件负责人", example = "3479")
    private Long hardwareUserId;

    @Schema(description = "区域硬件负责人昵称", example = "张三")
    private String hardwareUserNickName;

    @Schema(description = "状态 0:已启用,1:未启用", example = "2")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}