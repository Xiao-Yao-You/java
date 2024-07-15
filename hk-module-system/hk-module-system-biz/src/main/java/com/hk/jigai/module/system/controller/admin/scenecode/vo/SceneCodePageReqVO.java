package com.hk.jigai.module.system.controller.admin.scenecode.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 单据编码类型配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SceneCodePageReqVO extends PageParam {

    @Schema(description = "编码key")
    private String keyCode;

    @Schema(description = "描述", example = "随便")
    private String description;

    @Schema(description = "编码前缀")
    private String prefix;

    @Schema(description = "编码中缀")
    private String infix;

    @Schema(description = "编码后缀")
    private String suffix;

    @Schema(description = "编码规则", example = "2")
    private String type;

    @Schema(description = "起始值")
    private Integer start;

    @Schema(description = "步长")
    private Integer step;

    @Schema(description = "状态，0:启用,1:禁用")
    private Integer status;

    @Schema(description = "使用状态,0:未使用,1:已使用")
    private Integer useStatus;
}