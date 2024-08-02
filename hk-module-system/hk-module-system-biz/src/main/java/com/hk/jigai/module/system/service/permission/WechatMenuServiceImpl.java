package com.hk.jigai.module.system.service.permission;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.hk.jigai.module.system.controller.admin.permission.vo.menu.WechatMenuPageReqVO;
import com.hk.jigai.module.system.controller.admin.permission.vo.menu.WechatMenuSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.permission.MenuDO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.hk.jigai.module.system.dal.dataobject.permission.WechatMenuDO;
import com.hk.jigai.framework.common.pojo.PageResult;
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

    @Override
    public Long createMenu(WechatMenuSaveReqVO createReqVO) {
        // 插入
        WechatMenuDO menu = BeanUtils.toBean(createReqVO, WechatMenuDO.class);
        wechatMenuMapper.insert(menu);
        // 返回
        return menu.getId();
    }

    @Override
    public void updateMenu(WechatMenuSaveReqVO updateReqVO) {
        // 校验存在
        validateMenuExists(updateReqVO.getId());
        // 更新
        WechatMenuDO updateObj = BeanUtils.toBean(updateReqVO, WechatMenuDO.class);
        wechatMenuMapper.updateById(updateObj);
    }

    @Override
    public void deleteMenu(Long id) {
        // 校验存在
        validateMenuExists(id);
        // 删除
        wechatMenuMapper.deleteById(id);
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
    public PageResult<WechatMenuDO> getMenuPage(WechatMenuPageReqVO pageReqVO) {
        return wechatMenuMapper.selectPage(pageReqVO);
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
    public List<Long> getMenuIdListByPermissionFromCache(String permission) {
        List<WechatMenuDO> menus = wechatMenuMapper.selectListByPermission(permission);
        return convertList(menus, WechatMenuDO::getId);
    }

}