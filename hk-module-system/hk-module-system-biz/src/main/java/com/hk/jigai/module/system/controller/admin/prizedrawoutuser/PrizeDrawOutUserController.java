package com.hk.jigai.module.system.controller.admin.prizedrawoutuser;

import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.excel.core.util.ExcelUtils;
import com.hk.jigai.framework.tenant.core.aop.TenantIgnore;
import com.hk.jigai.module.system.controller.admin.operation.vo.QuestionTypeImportExcelVO;
import com.hk.jigai.module.system.controller.admin.operation.vo.QuestionTypeImportRespVO;
import com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo.PrizeDrawOutUserImportExcelVO;
import com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo.PrizeDrawOutUserImportRespVO;
import com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo.PrizeDrawOutUserPageReqVO;
import com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo.PrizeDrawOutUserRespVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawoutuser.PrizeDrawOutUserDO;
import com.hk.jigai.module.system.service.prizedrawoutuser.PrizeDrawOutUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.hk.jigai.framework.common.pojo.CommonResult.success;


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

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 空白表单
        List<PrizeDrawOutUserImportExcelVO> list = Arrays.asList();
        // 输出
        ExcelUtils.write(response, "场外人员导入模板.xls", "场外人员", PrizeDrawOutUserImportExcelVO.class, list);
    }

    @PostMapping("/import-excel")
    @Operation(summary = "场外人员导入导入")
    public CommonResult<PrizeDrawOutUserImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                                  @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<PrizeDrawOutUserImportExcelVO> list = ExcelUtils.read(file, PrizeDrawOutUserImportExcelVO.class);
        return success(prizeDrawOutUserService.importPrizeDrawOutUserList(list, updateSupport));
    }

    @GetMapping("/page")
    @Operation(summary = "获得场外参与人员分页")
    public CommonResult<PageResult<PrizeDrawOutUserRespVO>> getPrizeDrawOutUserPage(@Valid PrizeDrawOutUserPageReqVO pageReqVO) {
        PageResult<PrizeDrawOutUserDO> pageResult = prizeDrawOutUserService.getPrizeDrawOutUserPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PrizeDrawOutUserRespVO.class));
    }

    @GetMapping("/getRandOutUser")
    @PermitAll
    @TenantIgnore
    public CommonResult<List<PrizeDrawOutUserDO>> getRandOutUser(@RequestParam("activityId") Long activityId){
        return success(prizeDrawOutUserService.getRandOutUser(activityId));
    }

    @GetMapping("/getPrizeDrawOutUserCount")
    @PermitAll
    @TenantIgnore
    @Operation(summary = "查询场外参与人数")
    public CommonResult<Long> getPrizeDrawOutUserCount(@RequestParam("activityId") Long activityId) {
        return success(prizeDrawOutUserService.getPrizeDrawOutUserCount(activityId));
    }
}