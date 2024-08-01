package com.hk.jigai.module.system.controller.admin.permission.vo.menu;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - wechat菜单权限分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WechatMenuPageReqVO extends PageParam {

    @Schema(description = "菜单名称", example = "李四")
    private String name;

    @Schema(description = "权限标识")
    private String permission;

    @Schema(description = "菜单类型", example = "2")
    private Integer type;

    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "父菜单ID", example = "15829")
    private Long parentId;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "组件名", example = "赵六")
    private String componentName;

    @Schema(description = "菜单状态", example = "2")
    private Integer status;

    @Schema(description = "是否可见")
    private Boolean visible;

    @Schema(description = "是否缓存")
    private Boolean keepAlive;

    @Schema(description = "是否总是显示")
    private Boolean alwaysShow;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}