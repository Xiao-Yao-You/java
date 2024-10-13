package com.hk.jigai.module.system.service.permission;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * wechat发送消息封装 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class WeChatSendMessageServiceImpl implements WeChatSendMessageService {

    /**
     * 公众号发送消息
     */
    @Override
    public void sendModelMessage() {

    }

    private String getServiceAccessToken() {
        return null;
    }

}