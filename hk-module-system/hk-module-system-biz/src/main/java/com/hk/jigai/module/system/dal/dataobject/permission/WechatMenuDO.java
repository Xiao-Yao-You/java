package com.hk.jigai.module.system.dal.dataobject.permission;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * wechat菜单权限 DO
 *
 * @author 超级管理员
 */
@TableName("wechat_menu")
@KeySequence("wechat_menu_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatMenuDO extends BaseDO {

    /**
     * 菜单ID
     */
    @TableId
    private Long id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 权限标识
     */
    private String permission;
    /**
     * 菜单类型
     */
    private Integer type;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 组件名
     */
    private String componentName;
    /**
     * 背景色
     */
    private String bgcolor;
    /**
     * 菜单状态
     */
    private Integer status;
    /**
     * 是否可见
     */
    private Boolean visible;
    /**
     * 是否缓存
     */
    private Boolean keepAlive;
    /**
     * 是否总是显示
     */
    private Boolean alwaysShow;

}