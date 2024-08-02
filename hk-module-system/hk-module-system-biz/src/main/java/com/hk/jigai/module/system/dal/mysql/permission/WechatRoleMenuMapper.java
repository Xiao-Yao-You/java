package com.hk.jigai.module.system.dal.mysql.permission;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.permission.WechatRoleMenuDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色和菜单关联 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface WechatRoleMenuMapper extends BaseMapperX<WechatRoleMenuDO> {
    default List<WechatRoleMenuDO> selectListByRoleId(Long roleId) {
        return selectList(WechatRoleMenuDO::getRoleId, roleId);
    }

    default List<WechatRoleMenuDO> selectListByRoleId(Collection<Long> roleIds) {
        return selectList(WechatRoleMenuDO::getRoleId, roleIds);
    }

    default List<WechatRoleMenuDO> selectListByMenuId(Long menuId) {
        return selectList(WechatRoleMenuDO::getMenuId, menuId);
    }

    default void deleteListByRoleIdAndMenuIds(Long roleId, Collection<Long> menuIds) {
        delete(new LambdaQueryWrapper<WechatRoleMenuDO>()
                .eq(WechatRoleMenuDO::getRoleId, roleId)
                .in(WechatRoleMenuDO::getMenuId, menuIds));
    }

    default void deleteListByMenuId(Long menuId) {
        delete(new LambdaQueryWrapper<WechatRoleMenuDO>().eq(WechatRoleMenuDO::getMenuId, menuId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new LambdaQueryWrapper<WechatRoleMenuDO>().eq(WechatRoleMenuDO::getRoleId, roleId));
    }
}