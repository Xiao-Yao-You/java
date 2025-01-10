package com.hk.jigai.module.system.controller.admin.prizedrawuser;

import com.google.common.util.concurrent.RateLimiter;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.tenant.core.aop.TenantIgnore;
import com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo.PrizeDrawOutUserRespVO;
import com.hk.jigai.module.system.controller.admin.prizedrawuser.vo.PrizeDrawUserRespVO;
import com.hk.jigai.module.system.controller.admin.prizedrawuser.vo.PrizeDrawUserSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawuser.PrizeDrawUserDO;
import com.hk.jigai.module.system.service.prizedrawuser.PrizeDrawUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.hk.jigai.framework.common.pojo.CommonResult.success;
import static net.sf.jsqlparser.parser.feature.Feature.set;


@Tag(name = "管理后台 - 抽奖用户")
@RestController
@RequestMapping("/prize-draw-user")
@Validated
public class PrizeDrawUserController {

    @Resource
    private PrizeDrawUserService prizeDrawUserService;

    private final RateLimiter rateLimiter = RateLimiter.create(150.0);

    @GetMapping(path = "/event", produces = "text/plain;charset=utf-8")
    @PermitAll
    public String getHandleEvent(@RequestParam(value = "signature", required = false) String signature,
                                 @RequestParam(value = "timestamp", required = false) String timestamp,
                                 @RequestParam(value = "nonce", required = false) String nonce,
                                 @RequestParam(value = "echostr", required = false) String echostr,
                                 HttpServletRequest request) {
        String result = prizeDrawUserService.receive(signature, timestamp, nonce, echostr,request);
        return result;
    }


    @PostMapping(path = "/event", produces = "text/plain;charset=utf-8")
    @PermitAll
    public String postHandleEvent(@RequestParam(value = "signature", required = false) String signature,
                                  @RequestParam(value = "timestamp", required = false) String timestamp,
                                  @RequestParam(value = "nonce", required = false) String nonce,
                                  @RequestParam(value = "echostr", required = false) String echostr,
                                  HttpServletRequest request) {
        String result = prizeDrawUserService.receive(signature, timestamp, nonce, echostr,request);
        return result;
    }

    @PostMapping("/create")
    @PermitAll
    @Operation(summary = "活动签到，创建微信签到用户，微信code，活动批次activityId")
    public CommonResult<PrizeDrawUserRespVO> createPrizeDrawUser(@RequestParam("code") String code, @RequestParam("activityId") Long activityId) {
        rateLimiter.acquire();
        return success(prizeDrawUserService.createPrizeDrawUser(code, activityId));
    }

    @PostMapping("/getTokenByRefreshToken")
    @PermitAll
    @Operation(summary = "根据refreshToken获取token")
    public CommonResult<String> getTokenByRefreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(prizeDrawUserService.getTokenByRefreshToken(refreshToken));
    }

    @GetMapping("/prizeDraw")
    @PermitAll
    @TenantIgnore
    @Operation(summary = "现场人员抽奖,activityId活动批次，prizePool奖品等级,winNum中奖数量")
    public CommonResult<Set<PrizeDrawUserRespVO>> prizeDraw(@RequestParam("activityId") Long activityId, @RequestParam("prizePool") Long prizePool, @RequestParam("winNum") Integer winNum) {
        List<PrizeDrawUserDO> list = prizeDrawUserService.prizeDraw(activityId, prizePool, winNum);
        List<PrizeDrawUserRespVO> prizeDrawUserRespVOS = BeanUtils.toBean(list, PrizeDrawUserRespVO.class);
        Set<PrizeDrawUserRespVO> drawResult = new HashSet<PrizeDrawUserRespVO>(prizeDrawUserRespVOS);
        return success(drawResult);
    }

    @GetMapping("/getAllPrizeDraUser")
    @PermitAll
    @TenantIgnore
    public CommonResult<List<PrizeDrawUserDO>> getAllPrizeDraUser(@RequestParam("activityId") Long activityId) {
        List<PrizeDrawUserDO> list = prizeDrawUserService.getAllPrizeDraUser(activityId);
        return success(list);
    }

    @GetMapping("/getAllWinner")
    @PermitAll
    @Operation(summary = "获取所有现场中奖人员,activityId活动批次，prizeLevel奖品等级")
    public CommonResult<List<PrizeDrawUserRespVO>> getAllWinnerList(@RequestParam("activityId") Long activityId, @RequestParam("prizeLevel") Long prizeLevel) {
        List<PrizeDrawUserDO> list = prizeDrawUserService.getAllWinnerList(activityId, prizeLevel);
        return success(BeanUtils.toBean(list, PrizeDrawUserRespVO.class));
    }

    @GetMapping("/checkWinner")
    @PermitAll
    @Operation(summary = "获取所有现场中奖人员,activityId活动批次，prizeLevel奖品等级")
    public CommonResult<String> checkWinner(@RequestParam("openId") String openId,@RequestParam("activityId") String activityId) {
        String winnerResult = prizeDrawUserService.checkWinner(openId,activityId);
        return success(winnerResult);
    }

}