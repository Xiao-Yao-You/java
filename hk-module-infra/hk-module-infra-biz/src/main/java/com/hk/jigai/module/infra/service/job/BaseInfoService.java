package com.hk.jigai.module.infra.service.job;

import com.hk.jigai.module.infra.controller.admin.job.vo.job.BaseInfoVO;
import org.springframework.http.HttpMethod;

import java.util.List;

public interface BaseInfoService {
    /**
     * 调用接口查询用户基本信息
     * @param url
     * @param requestType
     * @return
     */
    List<BaseInfoVO> callPerson(String url, HttpMethod requestType);


}
