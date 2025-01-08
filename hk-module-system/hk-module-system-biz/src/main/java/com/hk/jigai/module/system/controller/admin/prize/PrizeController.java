package com.hk.jigai.module.system.controller.admin.prize;

import com.hk.jigai.module.system.controller.admin.prize.vo.PrizePageReqVO;
import com.hk.jigai.module.system.controller.admin.prize.vo.PrizeRespVO;
import com.hk.jigai.module.system.controller.admin.prize.vo.PrizeSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.prize.PrizeDO;
import com.hk.jigai.module.system.service.prize.PrizeService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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


@Tag(name = "管理后台 - 奖品信息")
@RestController
@RequestMapping("/prize")
@Validated
public class PrizeController {

    @Resource
    private PrizeService prizeService;

    @PostMapping("/create")
    @Operation(summary = "创建奖品信息")
    public CommonResult<Long> createPrize(@Valid @RequestBody PrizeSaveReqVO createReqVO) {
        return success(prizeService.createPrize(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新奖品信息")
    public CommonResult<Boolean> updatePrize(@Valid @RequestBody PrizeSaveReqVO updateReqVO) {
        prizeService.updatePrize(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除奖品信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deletePrize(@RequestParam("id") Long id) {
        prizeService.deletePrize(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得奖品信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<PrizeRespVO> getPrize(@RequestParam("id") Long id) {
        PrizeDO prize = prizeService.getPrize(id);
        return success(BeanUtils.toBean(prize, PrizeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得奖品信息分页")
    public CommonResult<PageResult<PrizeRespVO>> getPrizePage(@Valid PrizePageReqVO pageReqVO) {
        PageResult<PrizeDO> pageResult = prizeService.getPrizePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PrizeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出奖品信息 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPrizeExcel(@Valid PrizePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PrizeDO> list = prizeService.getPrizePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "奖品信息.xls", "数据", PrizeRespVO.class,
                        BeanUtils.toBean(list, PrizeRespVO.class));
    }

    @GetMapping("/getAllPrizeByActivityId")
    @Operation(summary = "根据活动ID查询活动缉奖品")
    public CommonResult<List<PrizeDO>> getAllPrizeByActivityId(@RequestParam("activityId") Long activityId){
        return success(prizeService.getAllPrizeByActivityId(activityId));
    }

}