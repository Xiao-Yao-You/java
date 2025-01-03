package com.hk.jigai.module.system.controller.admin.prizedrawactivity;

import com.hk.jigai.module.system.controller.admin.prizedrawactivity.vo.PrizeDrawActivityPageReqVO;
import com.hk.jigai.module.system.controller.admin.prizedrawactivity.vo.PrizeDrawActivityRespVO;
import com.hk.jigai.module.system.controller.admin.prizedrawactivity.vo.PrizeDrawActivitySaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawactivity.PrizeDrawActivityDO;
import com.hk.jigai.module.system.service.prizedrawactivity.PrizeDrawActivityService;
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


@Tag(name = "管理后台 - 抽奖活动")
@RestController
@RequestMapping("/prize-draw-activity")
@Validated
public class PrizeDrawActivityController {

    @Resource
    private PrizeDrawActivityService prizeDrawActivityService;

    @PostMapping("/create")
    @Operation(summary = "创建抽奖活动")
    public CommonResult<Long> createPrizeDrawActivity(@Valid @RequestBody PrizeDrawActivitySaveReqVO createReqVO) {
        return success(prizeDrawActivityService.createPrizeDrawActivity(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新抽奖活动")
    public CommonResult<Boolean> updatePrizeDrawActivity(@Valid @RequestBody PrizeDrawActivitySaveReqVO updateReqVO) {
        prizeDrawActivityService.updatePrizeDrawActivity(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除抽奖活动")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deletePrizeDrawActivity(@RequestParam("id") Long id) {
        prizeDrawActivityService.deletePrizeDrawActivity(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得抽奖活动")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<PrizeDrawActivityRespVO> getPrizeDrawActivity(@RequestParam("id") Long id) {
        PrizeDrawActivityDO prizeDrawActivity = prizeDrawActivityService.getPrizeDrawActivity(id);
        return success(BeanUtils.toBean(prizeDrawActivity, PrizeDrawActivityRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得抽奖活动分页")
    public CommonResult<PageResult<PrizeDrawActivityRespVO>> getPrizeDrawActivityPage(@Valid PrizeDrawActivityPageReqVO pageReqVO) {
        PageResult<PrizeDrawActivityDO> pageResult = prizeDrawActivityService.getPrizeDrawActivityPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PrizeDrawActivityRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出抽奖活动 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPrizeDrawActivityExcel(@Valid PrizeDrawActivityPageReqVO pageReqVO,
                                             HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PrizeDrawActivityDO> list = prizeDrawActivityService.getPrizeDrawActivityPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "抽奖活动.xls", "数据", PrizeDrawActivityRespVO.class,
                BeanUtils.toBean(list, PrizeDrawActivityRespVO.class));
    }

}