package com.hk.jigai.module.system.controller.admin.permission.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - wechat菜单权限新增/修改 Request VO")
@Data
public class WechatMenuSaveReqVO {

    @Schema(description = "菜单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23893")
    private Long id;

    @Schema(description = "菜单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "菜单名称不能为空")
    private String name;

    @Schema(description = "权限标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "权限标识不能为空")
    private String permission;

    @Schema(description = "菜单类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @Schema(description = "父菜单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15829")
    @NotNull(message = "父菜单ID不能为空")
    private Long parentId;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "组件名", example = "赵六")
    private String componentName;

    @Schema(description = "菜单状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "菜单状态不能为空")
    private Integer status;

    @Schema(description = "是否可见", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否可见不能为空")
    private Boolean visible;

    @Schema(description = "是否缓存", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否缓存不能为空")
    private Boolean keepAlive;

    @Schema(description = "是否总是显示", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否总是显示不能为空")
    private Boolean alwaysShow;

}