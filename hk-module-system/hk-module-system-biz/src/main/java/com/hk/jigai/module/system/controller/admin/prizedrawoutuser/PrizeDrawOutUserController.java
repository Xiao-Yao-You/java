package com.hk.jigai.module.system.controller.admin.prizedrawoutuser;

import com.hk.jigai.framework.tenant.core.aop.TenantIgnore;
import com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo.PrizeDrawOutUserPageReqVO;
import com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo.PrizeDrawOutUserRespVO;
import com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo.PrizeDrawOutUserSaveReqVO;
import com.hk.jigai.module.system.controller.admin.prizedrawuser.vo.PrizeDrawUserRespVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawoutuser.PrizeDrawOutUserDO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawuser.PrizeDrawUserDO;
import com.hk.jigai.module.system.service.prizedrawoutuser.PrizeDrawOutUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.security.PermitAll;
import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import static com.hk.jigai.framework.common.pojo.CommonResult.success;

import com.hk.jigai.framework.excel.core.util.ExcelUtils;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;

import static com.hk.jigai.framework.apilog.core.enums.OperateTypeEnum.*;


@Tag(name = "管理后台 - 场外参与人员")
@RestController
@RequestMapping("/prize-draw-out-user")
@Validated
public class PrizeDrawOutUserController {

    @Resource
    private PrizeDrawOutUserService prizeDrawOutUserService;

    @GetMapping("/prizeDraw")
    @PermitAll
    @TenantIgnore
    @Operation(summary = "现场人员抽奖,activityId活动批次，prizeLevel奖品等级,winNum中奖数量")
    public CommonResult<Set<PrizeDrawOutUserRespVO>> prizeDraw(@RequestParam("activityId") Long activityId, @RequestParam("prizePool") Long prizePool, @RequestParam("winNum") Integer winNum){
        List<PrizeDrawOutUserDO> list = prizeDrawOutUserService.prizeDraw(activityId,prizePool,winNum);
        List<PrizeDrawOutUserRespVO> prizeDrawOutUserRespVOS = BeanUtils.toBean(list, PrizeDrawOutUserRespVO.class);
        Set<PrizeDrawOutUserRespVO> drawResult = new HashSet<PrizeDrawOutUserRespVO>(prizeDrawOutUserRespVOS);
        return success(drawResult);
    }

    @GetMapping("/getAllWinner")
    @PermitAll
    @Operation(summary = "获取所有现场中奖人员,activityId活动批次，prizeLevel奖品等级")
    public CommonResult<List<PrizeDrawOutUserRespVO>> getAllWinnerList(@RequestParam("activityId") Long activityId,@RequestParam("prizeLevel") Long prizeLevel){
        List<PrizeDrawOutUserDO> list = prizeDrawOutUserService.getAllWinnerList(activityId,prizeLevel);
        return success(BeanUtils.toBean(list, PrizeDrawOutUserRespVO.class));
    }

}