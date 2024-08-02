package com.hk.jigai.module.system.service.permission;

import javax.validation.*;

import com.hk.jigai.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import com.hk.jigai.module.system.controller.admin.permission.vo.menu.WechatMenuPageReqVO;
import com.hk.jigai.module.system.controller.admin.permission.vo.menu.WechatMenuSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.permission.MenuDO;
import com.hk.jigai.module.system.dal.dataobject.permission.WechatMenuDO;
import com.hk.jigai.framework.common.pojo.PageResult;

import java.util.Collection;
import java.util.List;

/**
 * wechat菜单权限 Service 接口
 *
 * @author 超级管理员
 */
public interface WechatMenuService {

    /**
     * 创建wechat菜单权限
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMenu(@Valid WechatMenuSaveReqVO createReqVO);

    /**
     * 更新wechat菜单权限
     *
     * @param updateReqVO 更新信息
     */
    void updateMenu(@Valid WechatMenuSaveReqVO updateReqVO);

    /**
     * 删除wechat菜单权限
     *
     * @param id 编号
     */
    void deleteMenu(Long id);

    /**
     * 获得wechat菜单权限
     *
     * @param id 编号
     * @return wechat菜单权限
     */
    WechatMenuDO getMenu(Long id);

    /**
     * 获得wechat菜单权限分页
     *
     * @param pageReqVO 分页查询
     * @return wechat菜单权限分页
     */
    PageResult<WechatMenuDO> getMenuPage(WechatMenuPageReqVO pageReqVO);

    /**
     * 获得所有菜单列表
     *
     * @return 菜单列表
     */
    List<WechatMenuDO> getMenuList();

    /**
     * 获得菜单数组
     *
     * @param ids 菜单编号数组
     * @return 菜单数组
     */
    List<WechatMenuDO> getMenuList(Collection<Long> ids);

    /**
     * 获得权限对应的菜单编号数组
     *
     * @param permission 权限标识
     * @return 数组
     */
    List<Long> getMenuIdListByPermissionFromCache(String permission);

}