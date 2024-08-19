package com.hk.jigai.module.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.hk.jigai.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import com.hk.jigai.module.system.controller.admin.permission.vo.menu.WechatMenuSaveReqVO;
import com.hk.jigai.module.system.dal.redis.RedisKeyConstants;
import com.hk.jigai.module.system.service.tenant.TenantService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.hk.jigai.module.system.dal.dataobject.permission.WechatMenuDO;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.permission.WechatMenuMapper;

import java.util.Collection;
import java.util.List;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.framework.common.util.collection.CollectionUtils.convertList;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * wechat菜单权限 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class WechatMenuServiceImpl implements WechatMenuService {

    @Resource
    private WechatMenuMapper wechatMenuMapper;
    @Resource
    @Lazy // 延迟，避免循环依赖报错
    private TenantService tenantService;
    @Resource
    private PermissionService permissionService;
    @Override
    @CacheEvict(value = RedisKeyConstants.PERMISSION_WECHAT_MENU_ID_LIST, key = "#createReqVO.permission",
            condition = "#createReqVO.permission != null")
    public Long createMenu(WechatMenuSaveReqVO createReqVO) {
        // 插入
        WechatMenuDO menu = BeanUtils.toBean(createReqVO, WechatMenuDO.class);
        wechatMenuMapper.insert(menu);
        // 返回
        return menu.getId();
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.PERMISSION_WECHAT_MENU_ID_LIST,
            allEntries = true)
    public void updateMenu(WechatMenuSaveReqVO updateReqVO) {
        // 校验存在
        validateMenuExists(updateReqVO.getId());
        // 更新
        WechatMenuDO updateObj = BeanUtils.toBean(updateReqVO, WechatMenuDO.class);
        wechatMenuMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.PERMISSION_WECHAT_MENU_ID_LIST,
            allEntries = true)
    public void deleteMenu(Long id) {
        // 校验存在
        validateMenuExists(id);
        // 删除
        wechatMenuMapper.deleteById(id);

        permissionService.processWechatMenuDeleted(id);
    }

    private void validateMenuExists(Long id) {
        if (wechatMenuMapper.selectById(id) == null) {
            throw exception(MENU_NOT_EXISTS);
        }
    }

    @Override
    public WechatMenuDO getMenu(Long id) {
        return wechatMenuMapper.selectById(id);
    }

    @Override
    public List<WechatMenuDO> getMenuList() {
        return wechatMenuMapper.selectList();
    }

    @Override
    public List<WechatMenuDO> getMenuList(Collection<Long> ids) {
        // 当 ids 为空时，返回一个空的实例对象
        if (CollUtil.isEmpty(ids)) {
            return Lists.newArrayList();
        }
        return wechatMenuMapper.selectBatchIds(ids);
    }

    @Override
    @Cacheable(value = RedisKeyConstants.PERMISSION_WECHAT_MENU_ID_LIST, key = "#permission")
    public List<Long> getMenuIdListByPermissionFromCache(String permission) {
        List<WechatMenuDO> menus = wechatMenuMapper.selectListByPermission(permission);
        return convertList(menus, WechatMenuDO::getId);
    }

    @Override
    public List<WechatMenuDO> getMenuListByTenant(MenuListReqVO reqVO) {
        List<WechatMenuDO> menus = getMenuList(reqVO);
        // 开启多租户的情况下，需要过滤掉未开通的菜单
        tenantService.handleTenantWechatMenu(menuIds -> menus.removeIf(menu -> !CollUtil.contains(menuIds, menu.getId())));
        return menus;
    }


    @Override
    public List<WechatMenuDO> getMenuList(MenuListReqVO reqVO) {
        return wechatMenuMapper.selectList(reqVO);
    }

}