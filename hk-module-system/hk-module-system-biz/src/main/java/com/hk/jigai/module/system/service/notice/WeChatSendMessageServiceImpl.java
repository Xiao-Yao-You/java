package com.hk.jigai.module.system.service.notice;

import com.hk.jigai.module.system.controller.admin.notice.vo.WechatNoticeVO;
import com.hk.jigai.module.system.dal.redis.RedisKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * wechat发送消息封装 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
@Slf4j
public class WeChatSendMessageServiceImpl implements WeChatSendMessageService {

    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final String appId = "wx145112d98bdbfbfe";

    private final String secret = "3d82310d78a1a2e54b2232ca97bbfdb7";

    /**
     * 公众号发送消息
     */
    @Override
    public void sendModelMessage(List<String> openIdList, Map wechatNoticeVO) {
        try {
            //获取微信公众号token
            String serviceAccessToken = getServiceAccessToken();
            //处理消息的接收对象
            Set<String> openidSet = new HashSet<String>(openIdList);
            //封装请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            for (String openId : openIdList) {
                wechatNoticeVO.put("touser", openId);
                HttpEntity<HashMap> requestEntity = new HttpEntity(wechatNoticeVO, headers);
                //调用请求
                HashMap result = restTemplate.exchange("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + serviceAccessToken,
                        HttpMethod.POST, requestEntity, new ParameterizedTypeReference<HashMap>() {
                        }).getBody();
                Integer successode = new Integer("0");
                if (result == null || !successode.equals((Integer) result.get("errcode"))) {
                    log.info("发送消息异常" + (Integer) result.get("errcode") + ":" + (String) result.get("errmsg"));
                    Integer tokenExpired = new Integer("42001");
                    if (tokenExpired.equals((Integer) result.get("errcode"))) {
                        redisTemplate.delete(RedisKeyConstants.WECHAT_AUTHTOKEN);
                    }
                }
            }

        } catch (Exception e) {
            log.info("发送会议模板消息异常！", e);
        }

    }

    private String getServiceAccessToken() {
        // 尝试先从redis中获取token
        String authToken = (String) redisTemplate.opsForValue().get(RedisKeyConstants.WECHAT_AUTHTOKEN);
        // 当没有获取到token的时候重新获取token
        if (StringUtils.isBlank(authToken)) {
            Map authMap = restTemplate.exchange("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + secret,
                    HttpMethod.POST, null,
                    new ParameterizedTypeReference<HashMap>() {
                    }).getBody();
            if (authMap != null && authMap.get("access_token") != null) {
                authToken = (String) authMap.get("access_token");
//                redisTemplate.opsForValue().set(RedisKeyConstants.WECHAT_AUTHTOKEN, authToken);
                redisTemplate.opsForValue().set(RedisKeyConstants.WECHAT_AUTHTOKEN, authToken, 1000, TimeUnit.SECONDS);
            } else {
                authToken = getServiceAccessToken();
            }
        }
        return authToken;
    }

}