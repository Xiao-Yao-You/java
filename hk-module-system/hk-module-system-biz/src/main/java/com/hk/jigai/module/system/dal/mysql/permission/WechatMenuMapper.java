package com.hk.jigai.module.system.dal.mysql.permission;

import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
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

    default List<WechatMenuDO> selectList(MenuListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<WechatMenuDO>()
                .likeIfPresent(WechatMenuDO::getName, reqVO.getName())
                .eqIfPresent(WechatMenuDO::getStatus, reqVO.getStatus()));
    }
    default List<WechatMenuDO> selectListByPermission(String permission) {
        return selectList(WechatMenuDO::getPermission, permission);
    }
}