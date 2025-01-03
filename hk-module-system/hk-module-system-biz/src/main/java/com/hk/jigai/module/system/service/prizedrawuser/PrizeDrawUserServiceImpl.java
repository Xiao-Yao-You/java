package com.hk.jigai.module.system.service.prizedrawuser;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.exception.ErrorCode;
import com.hk.jigai.module.system.controller.admin.prizedrawuser.vo.PrizeDrawUserPageReqVO;
import com.hk.jigai.module.system.controller.admin.prizedrawuser.vo.PrizeDrawUserRespVO;
import com.hk.jigai.module.system.controller.admin.prizedrawuser.vo.PrizeDrawUserSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawuser.PrizeDrawUserDO;
import com.hk.jigai.module.system.dal.mysql.prizedrawuser.PrizeDrawUserMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import org.springframework.web.client.RestTemplate;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;


/**
 * 抽奖用户 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class PrizeDrawUserServiceImpl implements PrizeDrawUserService {

    @Resource
    private PrizeDrawUserMapper prizeDrawUserMapper;

    @Resource
    private RestTemplate restTemplate;

    private final String appId = "wx145112d98bdbfbfe";

    private final String secret = "3d82310d78a1a2e54b2232ca97bbfdb7";

    @Override
    public String getTokenByRefreshToken(String refreshToken) {
        //        https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
        String refreshStr = restTemplate.exchange("https://api.weixin.qq.com/sns/oauth2/refresh_token?" +
                        "appid=wx145112d98bdbfbfe&grant_type=refresh_token&refresh_token="+
                        refreshToken, HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                }).getBody();
        Map refreshTokenMap = (Map) JSON.parse(refreshStr);
        if (refreshTokenMap == null || refreshTokenMap.get("access_token") == null) {
            throw exception(PRIZE_DRAW_TOKEN);
        }
        String accessToken = (String) refreshTokenMap.get("access_token");
        return accessToken;
    }

    @Override
    public PrizeDrawUserRespVO createPrizeDrawUser(String code,String activityId) {
        PrizeDrawUserRespVO prizeDrawUserRespVO = new PrizeDrawUserRespVO();
        String authStr = restTemplate.exchange("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx145112d98bdbfbfe&secret=3d82310d78a1a2e54b2232ca97bbfdb7&code="
                        + code + "&grant_type=authorization_code", HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                }).getBody();
        Map authMap = (Map) JSON.parse(authStr);
        if (authMap == null || authMap.get("openid") == null) {
            throw exception(PRIZE_DRAW_TOKEN);
        }
        String openid = (String) authMap.get("openid");
        String accessToken = (String) authMap.get("access_token");
        String refreshToken = (String) authMap.get("refresh_token");
        prizeDrawUserRespVO.setOpenid(openid);
        prizeDrawUserRespVO.setAccessToken(accessToken);
        prizeDrawUserRespVO.setRefreshToken(refreshToken);
        PrizeDrawUserDO prizeDrawUserDO = prizeDrawUserMapper.selectOne(new QueryWrapper<PrizeDrawUserDO>().lambda()
                .eq(PrizeDrawUserDO::getOpenid, openid)    //openId相同
                .eq(PrizeDrawUserDO::getActivityBatch, activityId)); //活动批次相同
        if (prizeDrawUserDO != null) {
//            throw exception(PRIZE_DRAW_USER_EXISTS);
            PrizeDrawUserRespVO prizeDrawUserResp = BeanUtils.toBean(prizeDrawUserDO, PrizeDrawUserRespVO.class);
            prizeDrawUserResp.setOpenid(openid);
            prizeDrawUserResp.setAccessToken(accessToken);
            prizeDrawUserResp.setRefreshToken(refreshToken);
            return prizeDrawUserResp;
        }

        PrizeDrawUserDO prizeDrawUser = new PrizeDrawUserDO();
//        https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        String infoStr = restTemplate.exchange("https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openid+"&lang=zh_CN", HttpMethod.GET, null,
                new ParameterizedTypeReference<String>() {
                }).getBody();
        Map infoMap = (Map) JSON.parse(infoStr);
        if (infoMap == null) {
            throw exception(PRIZE_DRAW_NOT_INFO);
        }
        prizeDrawUser.setOpenid((String) authMap.get("openid"));
        prizeDrawUserRespVO.setOpenid((String) authMap.get("openid"));
        prizeDrawUser.setNickname((String) authMap.get("nickname"));
        prizeDrawUserRespVO.setNickname((String) authMap.get("nickname"));
        prizeDrawUser.setHeadimgurl((String) authMap.get("headimgurl"));
        prizeDrawUserRespVO.setHeadimgurl((String) authMap.get("headimgurl"));
        prizeDrawUser.setStatus(1); //默认“1”未中奖
        prizeDrawUser.setWinningRate(1.00);//默认权重为1

        prizeDrawUserMapper.insert(prizeDrawUser);
        Long aLong = prizeDrawUserMapper.selectCount(new QueryWrapper<PrizeDrawUserDO>().lambda().eq(PrizeDrawUserDO::getActivityBatch, activityId));
        prizeDrawUserRespVO.setCurrentNum(aLong);
        // 返回
        return prizeDrawUserRespVO;
    }

    @Override
    @Transactional
    public List<PrizeDrawUserDO> prizeDraw(Long activityId, Long prizePool, Integer winNum) {
        //所有可以参与抽奖的人员
        List<PrizeDrawUserDO> prizeDrawUserDOS = prizeDrawUserMapper.selectList(new QueryWrapper<PrizeDrawUserDO>().lambda()
                .eq(PrizeDrawUserDO::getActivityBatch, activityId)
                .eq(PrizeDrawUserDO::getStatus, 1)
                        .and(wrapper->wrapper.isNull(PrizeDrawUserDO::getPrizePool).or().eq(PrizeDrawUserDO::getPrizePool , prizePool)));
        if (CollectionUtil.isEmpty(prizeDrawUserDOS) || prizeDrawUserDOS.size() < winNum) {
            throw exception(PRIZE_DRAW_USER_NOT_ENOUGH);
        }
        //开始抽奖
        List<PrizeDrawUserDO> winners = drawWinners(prizeDrawUserDOS, winNum);
        winners.stream().map(p->{
            p.setStatus(2);
            p.setPrizeLevel(prizePool.intValue());
            return p;
        }).collect(Collectors.toList());
        prizeDrawUserMapper.updateBatch(winners);
        return winners;
    }

    public static List<PrizeDrawUserDO> drawWinners(List<PrizeDrawUserDO> participants, int numberOfWinners) {
        List<PrizeDrawUserDO> winners = new ArrayList<>();

        // 计算累积概率
        double[] cumulativeProbabilities = new double[participants.size()];
        double sum = 0.0;
        for (int i = 0; i < participants.size(); i++) {
            sum += participants.get(i).getWinningRate();
            cumulativeProbabilities[i] = sum;
        }

        // 使用随机数生成器选择中奖者
        Random random = new Random();
        while (winners.size() < numberOfWinners) {
            double rand = random.nextDouble() * sum;
            int winnerIndex = binarySearch(cumulativeProbabilities, rand);
            PrizeDrawUserDO winner = participants.get(winnerIndex);

            // 如果该参与者已经被抽中过，则继续抽，否则将其添加到中奖者列表
            if (!winners.contains(winner)) {
                winners.add(winner);
            }
        }

        return winners;
    }

    private static int binarySearch(double[] cumulativeProbabilities, double rand) {
        int low = 0, high = cumulativeProbabilities.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            double midVal = cumulativeProbabilities[mid];

            if (midVal < rand) {
                low = mid + 1;
            } else if (midVal > rand) {
                high = mid - 1;
            } else {
                return mid; // 这种情况几乎不会发生，因为概率是浮点数
            }
        }
        return low;
    }

    @Override
    public List<PrizeDrawUserDO> getAllWinnerList(Long activityId, Long prizeLevel) {

        List<PrizeDrawUserDO> prizeDrawUserDOS = prizeDrawUserMapper.selectList(new QueryWrapper<PrizeDrawUserDO>().lambda()
                .eq(PrizeDrawUserDO::getActivityBatch, activityId)
                .eq(PrizeDrawUserDO::getPrizeLevel, prizeLevel));

        return prizeDrawUserDOS;
    }

}