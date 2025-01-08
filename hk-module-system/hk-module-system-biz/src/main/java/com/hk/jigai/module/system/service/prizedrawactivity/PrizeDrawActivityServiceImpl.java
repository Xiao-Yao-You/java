package com.hk.jigai.module.system.service.prizedrawactivity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.module.system.controller.admin.prizedrawactivity.vo.PrizeDrawActivityPageReqVO;
import com.hk.jigai.module.system.controller.admin.prizedrawactivity.vo.PrizeDrawActivitySaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawactivity.PrizeDrawActivityDO;
import com.hk.jigai.module.system.dal.mysql.prizedrawactivity.PrizeDrawActivityMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import org.springframework.web.client.RestTemplate;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 抽奖活动 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class PrizeDrawActivityServiceImpl implements PrizeDrawActivityService {

    @Resource
    private PrizeDrawActivityMapper prizeDrawActivityMapper;

    @Resource
    private RestTemplate restTemplate;

    private final String appId = "wx145112d98bdbfbfe";

    private final String secret = "3d82310d78a1a2e54b2232ca97bbfdb7";

    @Override
    public Long createPrizeDrawActivity(PrizeDrawActivitySaveReqVO createReqVO) {
        // 插入
        PrizeDrawActivityDO prizeDrawActivity = BeanUtils.toBean(createReqVO, PrizeDrawActivityDO.class);

        //获取二维码token
        String tokenStr = restTemplate.exchange("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                        + appId + "&secret=" + secret, HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                }).getBody();
        Map authMap = (Map) JSON.parse(tokenStr);
        if (authMap == null || authMap.get("access_token") == null) {
            throw exception(PRIZE_DRAW_TOKEN);
        }
        String token = (String) authMap.get("access_token");

//        long between = ChronoUnit.MILLIS.between(createReqVO.getBeginTime(), createReqVO.getEndTime());
        long between = 100l;

        // 准备请求参数，通常是一个Map
        Map params = new HashMap<>();
        params.put("expire_seconds", between);
        params.put("action_name", "QR_SCENE");
        Map<String, Map> actionInfo = new HashMap<>();
        Map<String, String> scene = new HashMap<>();
        scene.put("scene_str", "prizeDraw");
        actionInfo.put("scene", scene);
        params.put("action_info", actionInfo);

        // 创建HttpEntity，它包装了参数和头信息
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(params, null);
        String ticketStr = restTemplate.exchange("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token, HttpMethod.POST, requestEntity,
                new ParameterizedTypeReference<String>() {
                }).getBody();
        Map ticketMap = (Map) JSON.parse(ticketStr);
        if (ticketMap == null || ticketMap.get("ticket") == null) {
            throw exception(PRIZE_DRAW_NO_TICKET);
        }

        prizeDrawActivity.setTicket((String) ticketMap.get("ticket"));
        prizeDrawActivityMapper.insert(prizeDrawActivity);
        // 返回
        return prizeDrawActivity.getId();
    }

    @Override
    public void updatePrizeDrawActivity(PrizeDrawActivitySaveReqVO updateReqVO) {
        // 校验存在
        validatePrizeDrawActivityExists(updateReqVO.getId());
        // 更新
        PrizeDrawActivityDO updateObj = BeanUtils.toBean(updateReqVO, PrizeDrawActivityDO.class);
        prizeDrawActivityMapper.updateById(updateObj);
    }

    @Override
    public void deletePrizeDrawActivity(Long id) {
        // 校验存在
        validatePrizeDrawActivityExists(id);
        // 删除
        prizeDrawActivityMapper.deleteById(id);
    }

    private void validatePrizeDrawActivityExists(Long id) {
        if (prizeDrawActivityMapper.selectById(id) == null) {
            throw exception(PRIZE_DRAW_ACTIVITY_NOT_EXISTS);
        }
    }

    @Override
    public PrizeDrawActivityDO getPrizeDrawActivity(Long id) {
        PrizeDrawActivityDO p = prizeDrawActivityMapper.selectById(id);
        Date currentDate = new Date(); // 当前日期
        if (currentDate.compareTo(p.getBeginTime()) < 0) {
            p.setStatus("未开始");
        }

        if (currentDate.compareTo(p.getEndTime()) > 0) {
            p.setStatus("已结束");
        }

        if (currentDate.compareTo(p.getBeginTime()) > 0 && currentDate.compareTo(p.getEndTime()) < 0) {
            p.setStatus("进行中");
        }
        return p;
    }

    @Override
    public PageResult<PrizeDrawActivityDO> getPrizeDrawActivityPage(PrizeDrawActivityPageReqVO pageReqVO) {
        PageResult<PrizeDrawActivityDO> prizeDrawActivityDOPageResult = prizeDrawActivityMapper.selectPage(pageReqVO);
        List<PrizeDrawActivityDO> list = prizeDrawActivityDOPageResult.getList();
        List<PrizeDrawActivityDO> collect = list.stream().map(p -> {
            Date currentDate = new Date(); // 当前日期
            if (currentDate.compareTo(p.getBeginTime()) < 0) {
                p.setStatus("未开始");
            }

            if (currentDate.compareTo(p.getEndTime()) > 0) {
                p.setStatus("已结束");
            }

            if (currentDate.compareTo(p.getBeginTime()) > 0 && currentDate.compareTo(p.getEndTime()) < 0) {
                p.setStatus("进行中");
            }
            return p;
        }).collect(Collectors.toList());
        return prizeDrawActivityDOPageResult.setList(collect);
    }

    @Override
    public List<PrizeDrawActivityDO> getAllActivities() {
        return prizeDrawActivityMapper.selectList();
    }

}