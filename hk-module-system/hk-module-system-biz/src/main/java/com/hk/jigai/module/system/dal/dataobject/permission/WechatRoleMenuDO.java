package com.hk.jigai.module.system.dal.dataobject.permission;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 角色和菜单关联 DO
 *
 * @author 超级管理员
 */
@TableName("wechat_role_menu")
@KeySequence("wechat_role_menu_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatRoleMenuDO extends BaseDO {

    /**
     * 自增编号
     */
    @TableId
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 微信菜单ID
     */
    private Long menuId;

}