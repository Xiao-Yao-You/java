package com.hk.jigai.module.system.service.permission;

import com.hk.jigai.module.system.dal.redis.RedisKeyConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * wechat发送消息封装 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class WeChatSendMessageServiceImpl implements WeChatSendMessageService {

    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 公众号发送消息
     */
    @Override
    public void sendModelMessage() {
        //获取微信公众号token
        String serviceAccessToken = getServiceAccessToken();
    }

    private String getServiceAccessToken() {
        // 尝试先从redis中获取token
        String authToken = (String) redisTemplate.opsForValue().get(RedisKeyConstants.WECHAT_AUTHTOKEN);
        // 当没有获取到token的时候重新获取token
        if (StringUtils.isBlank(authToken)) {
            Map authMap = restTemplate.exchange("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx145112d98bdbfbfe&secret=3d82310d78a1a2e54b2232ca97bbfdb7", HttpMethod.POST, null,
                    new ParameterizedTypeReference<HashMap>() {
                    }).getBody();
            if (authMap != null && authMap.get("access_token") != null) {
                authToken = (String) authMap.get("access_token");
                redisTemplate.opsForValue().set(RedisKeyConstants.WECHAT_AUTHTOKEN, authToken);
            } else {
                authToken = getServiceAccessToken();
            }
        }
        return authToken;
    }

}