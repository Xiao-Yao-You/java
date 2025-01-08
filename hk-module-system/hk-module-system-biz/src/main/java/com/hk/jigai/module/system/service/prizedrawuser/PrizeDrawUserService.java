package com.hk.jigai.module.system.service.prizedrawuser;

import com.hk.jigai.module.system.controller.admin.prizedrawuser.vo.PrizeDrawUserRespVO;
import com.hk.jigai.module.system.controller.admin.prizedrawuser.vo.PrizeDrawUserSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawuser.PrizeDrawUserDO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 抽奖用户 Service 接口
 *
 * @author 邵志伟
 */
public interface PrizeDrawUserService {

    /**
     * 创建抽奖用户
     *
     * @param code
     * @param activityId
     * @return 编号
     */
    PrizeDrawUserRespVO createPrizeDrawUser(String code, String activityId);


    /**
     * 查询所有中奖用户
     *
     * @param activityId
     * @return
     */
    List<PrizeDrawUserDO> getAllWinnerList(Long activityId, Long prizeLevel);

    /**
     * 现场人员抽奖
     *
     * @param activityId
     * @param prizePool
     * @param winNum
     * @return
     */
    List<PrizeDrawUserDO> prizeDraw(Long activityId, Long prizePool, Integer winNum);

    /**
     * 根据refreshToken获取token
     *
     * @param refreshToken
     * @return
     */
    String getTokenByRefreshToken(String refreshToken);

    /**
     * 微信公众号回调事件
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    String receive(String signature, String timestamp, String nonce, String echostr, HttpServletRequest request);
}