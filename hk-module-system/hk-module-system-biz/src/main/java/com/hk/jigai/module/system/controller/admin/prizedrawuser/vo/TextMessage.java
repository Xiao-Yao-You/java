package com.hk.jigai.module.system.controller.admin.prizedrawuser.vo;

import lombok.Data;

/**
 * @author SZW
 * @Date 2025/1/6 16:15
 */
@Data
public class TextMessage {

    private String toUserName;
    private String fromUserName;
    private Long createTime;
    private String msgType;
    private String content;
}
