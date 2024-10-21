package com.hk.jigai.module.system.controller.admin.notice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

/**
 * @author SZW
 * @Date 2024/10/17 16:27
 */

@Schema
@Data
public class WechatNoticeVO {

    private String touser;

    private String template_id;

    private String url;

    private MiniProgram miniprogram;

    private String client_msg_id;

    private Map data;

    public MiniProgram createMiniProgram(String appId, String pagepath) {
        return new MiniProgram(appId, pagepath);
    }
    
    class MiniProgram {
        private String appid;
        private String pagepath;

        public MiniProgram(String appid, String pagepath) {
            this.appid = appid;
            this.pagepath = pagepath;
        }
    }


}
