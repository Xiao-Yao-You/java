package com.hk.jigai.module.infra.service.job;

import com.hk.jigai.module.infra.controller.admin.job.vo.job.BaseInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 基础信息同步
 */

@Service
@Validated
@Slf4j
public class BaseInfoServiceImpl implements BaseInfoService{

    @Resource
    private RestTemplate restTemplate;

    @Override
    public List<BaseInfoVO> callPerson(String url, HttpMethod requestType){
        return restTemplate.exchange(url, requestType, null,
                new ParameterizedTypeReference<List<BaseInfoVO>>() {}).getBody();
    }

}
