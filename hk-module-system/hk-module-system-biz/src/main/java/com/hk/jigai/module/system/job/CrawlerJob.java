package com.hk.jigai.module.system.job;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.hk.jigai.framework.tenant.core.aop.TenantIgnore;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.HashMap;


@Slf4j
//@Component
public class CrawlerJob {

    private final String EQM_BASE_URL = "http://192.168.95.21:4434";
//    http://192.168.95.21:4434/api/oauth/pc/token

    @Scheduled(cron = "0 0 1 * * ?")
    @TenantIgnore
    @Test
    public void crawlerScheduled() throws IOException {
        String pcCareUrl = EQM_BASE_URL + "/api/pcCare/list";
        String token = getToken();
        HashMap<String, Object> loginParam = new HashMap<>();
        if (StringUtil.isBlank(token)) {
            token = getToken();
        }
        String endDate = "2024-08-06 23:59:59";
        String startDate = "2024-07-06 00:00:00";
        String jsonStr = "{\"endDate\":\" " + endDate + "\",\"pageNum\":1,\"pageSize\":20,\"searchValue\":\"\",\"startDate\":\"" + startDate + "\"," +
                "\"factoryAreaCode\":\"1017\",\"factoryCode\":\"115000\",\"deviceCodeList\":[],\"statusList\":[],\"updateName\":\"\"}";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建HttpPost实例，指定URL
        HttpPost httpPost = new HttpPost(pcCareUrl);
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "bearer " + token);

        // 设置参数到raw格式的body
        StringEntity entity = new StringEntity(jsonStr);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(responseBody);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    public String getToken() {
        String eqmLoginUrl = EQM_BASE_URL + "/api/oauth/pc/token";
        HashMap<String, Object> loginParam = new HashMap<>();
        loginParam.put("username", "10039651");
        loginParam.put("password", "123456");
        loginParam.put("randstr", "");
        loginParam.put("ticket", "");
        loginParam.put("scope", "pc");
        loginParam.put("grant_type", "password");
        String loginRes = HttpUtil.post(eqmLoginUrl, loginParam);
        System.out.println(loginRes);
        JSONObject jsonObject = new JSONObject(loginRes);
        String data = jsonObject.getStr("data");
        String accessToken = "";
        if (StringUtil.isNotBlank(data)) {
            JSONObject dataJson = new JSONObject(data);
            accessToken = dataJson.getStr("accessToken");
        }
        return accessToken;
    }

}
