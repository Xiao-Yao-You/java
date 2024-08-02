package com.hk.jigai.module.system.dal.mysql.permission;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.permission.vo.menu.WechatMenuPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.permission.MenuDO;
import com.hk.jigai.module.system.dal.dataobject.permission.WechatMenuDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * wechat菜单权限 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface WechatMenuMapper extends BaseMapperX<WechatMenuDO> {

    default PageResult<WechatMenuDO> selectPage(WechatMenuPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WechatMenuDO>()
                .likeIfPresent(WechatMenuDO::getName, reqVO.getName())
                .eqIfPresent(WechatMenuDO::getPermission, reqVO.getPermission())
                .eqIfPresent(WechatMenuDO::getType, reqVO.getType())
                .eqIfPresent(WechatMenuDO::getSort, reqVO.getSort())
                .eqIfPresent(WechatMenuDO::getParentId, reqVO.getParentId())
                .eqIfPresent(WechatMenuDO::getPath, reqVO.getPath())
                .eqIfPresent(WechatMenuDO::getIcon, reqVO.getIcon())
                .eqIfPresent(WechatMenuDO::getComponent, reqVO.getComponent())
                .likeIfPresent(WechatMenuDO::getComponentName, reqVO.getComponentName())
                .eqIfPresent(WechatMenuDO::getStatus, reqVO.getStatus())
                .eqIfPresent(WechatMenuDO::getVisible, reqVO.getVisible())
                .eqIfPresent(WechatMenuDO::getKeepAlive, reqVO.getKeepAlive())
                .eqIfPresent(WechatMenuDO::getAlwaysShow, reqVO.getAlwaysShow())
                .betweenIfPresent(WechatMenuDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(WechatMenuDO::getId));
    }


    default List<WechatMenuDO> selectListByPermission(String permission) {
        return selectList(WechatMenuDO::getPermission, permission);
    }
}