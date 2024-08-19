package com.hk.jigai.module.system.controller.admin.permission.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - wechat菜单权限 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WechatMenuRespVO {

    @Schema(description = "菜单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23893")
    @ExcelProperty("菜单ID")
    private Long id;

    @Schema(description = "菜单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("菜单名称")
    private String name;

    @Schema(description = "权限标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("权限标识")
    private String permission;

    @Schema(description = "菜单类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("菜单类型")
    private Integer type;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("显示顺序")
    private Integer sort;

    @Schema(description = "父菜单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15829")
    @ExcelProperty("父菜单ID")
    private Long parentId;

    @Schema(description = "路由地址")
    @ExcelProperty("路由地址")
    private String path;

    @Schema(description = "菜单图标")
    @ExcelProperty("菜单图标")
    private String icon;

    @Schema(description = "组件路径")
    @ExcelProperty("组件路径")
    private String component;

    @Schema(description = "组件名", example = "赵六")
    @ExcelProperty("组件名")
    private String componentName;

    @Schema(description = "背景色")
    @ExcelProperty("背景色")
    private String bgcolor;

    @Schema(description = "菜单状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("菜单状态")
    private Integer status;

    @Schema(description = "是否可见", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否可见")
    private Boolean visible;

    @Schema(description = "是否缓存", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否缓存")
    private Boolean keepAlive;

    @Schema(description = "是否总是显示", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否总是显示")
    private Boolean alwaysShow;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;



}