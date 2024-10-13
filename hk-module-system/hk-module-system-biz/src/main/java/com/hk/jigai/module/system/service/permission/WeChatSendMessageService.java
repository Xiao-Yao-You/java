package com.hk.jigai.module.system.service.permission;

import com.hk.jigai.module.system.controller.admin.permission.vo.menu.MenuListReqVO;
import com.hk.jigai.module.system.controller.admin.permission.vo.menu.WechatMenuSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.permission.WechatMenuDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * wechat发送消息封装 接口
 *
 * @author 超级管理员
 */
public interface WeChatSendMessageService {

    void sendModelMessage();

}