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

    @PostMapping("/create")
    @PermitAll
    @Operation(summary = "活动签到，创建微信签到用户，微信code，活动批次activityId")
    public CommonResult<PrizeDrawUserRespVO> createPrizeDrawUser(@RequestParam("code") String code,@RequestParam("activityId") String activityId) {
        rateLimiter.acquire();
        return success(prizeDrawUserService.createPrizeDrawUser(code,activityId));
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
    @Operation(summary = "现场人员抽奖,activityId活动批次，prizeLevel奖品等级,winNum中奖数量")
    public CommonResult<Set<PrizeDrawUserRespVO>> prizeDraw(@RequestParam("activityId") Long activityId,@RequestParam("prizePool") Long prizePool,@RequestParam("winNum") Integer winNum){
        List<PrizeDrawUserDO> list = prizeDrawUserService.prizeDraw(activityId,prizePool,winNum);
        List<PrizeDrawUserRespVO> prizeDrawUserRespVOS = BeanUtils.toBean(list, PrizeDrawUserRespVO.class);
        Set<PrizeDrawUserRespVO> drawResult = new HashSet<PrizeDrawUserRespVO>(prizeDrawUserRespVOS);
        return success(drawResult);
    }

    @GetMapping("/getAllWinner")
    @PermitAll
    @Operation(summary = "获取所有现场中奖人员,activityId活动批次，prizeLevel奖品等级")
    public CommonResult<List<PrizeDrawUserRespVO>> getAllWinnerList(@RequestParam("activityId") Long activityId,@RequestParam("prizeLevel") Long prizeLevel){
        List<PrizeDrawUserDO> list = prizeDrawUserService.getAllWinnerList(activityId,prizeLevel);
        return success(BeanUtils.toBean(list, PrizeDrawUserRespVO.class));
    }

}