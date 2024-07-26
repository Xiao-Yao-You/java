package com.hk.jigai.module.system.controller.admin.userreport;

import com.hk.jigai.module.system.controller.admin.userreport.vo.UserReportPageReqVO;
import com.hk.jigai.module.system.controller.admin.userreport.vo.UserReportRespVO;
import com.hk.jigai.module.system.controller.admin.userreport.vo.UserReportSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.userreport.UserReportDO;
import com.hk.jigai.module.system.service.userreport.UserReportService;
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



@Tag(name = "管理后台 - 用户汇报")
@RestController
@RequestMapping("/user-report")
@Validated
public class UserReportController {

    @Resource
    private UserReportService userReportService;

    @PostMapping("/create")
    @Operation(summary = "创建用户汇报")
    @PreAuthorize("@ss.hasPermission('hk:user-report:create')")
    public CommonResult<Long> createUserReport(@Valid @RequestBody UserReportSaveReqVO createReqVO) {
        return success(userReportService.createUserReport(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户汇报")
    @PreAuthorize("@ss.hasPermission('hk:user-report:update')")
    public CommonResult<Boolean> updateUserReport(@Valid @RequestBody UserReportSaveReqVO updateReqVO) {
        userReportService.updateUserReport(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户汇报")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:user-report:delete')")
    public CommonResult<Boolean> deleteUserReport(@RequestParam("id") Long id) {
        userReportService.deleteUserReport(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户汇报")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:user-report:query')")
    public CommonResult<UserReportRespVO> getUserReport(@RequestParam("id") Long id) {
        UserReportDO userReport = userReportService.getUserReport(id);
        return success(BeanUtils.toBean(userReport, UserReportRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户汇报分页")
    @PreAuthorize("@ss.hasPermission('hk:user-report:query')")
    public CommonResult<PageResult<UserReportRespVO>> getUserReportPage(@Valid UserReportPageReqVO pageReqVO) {
        PageResult<UserReportDO> pageResult = userReportService.getUserReportPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, UserReportRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户汇报 Excel")
    @PreAuthorize("@ss.hasPermission('hk:user-report:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportUserReportExcel(@Valid UserReportPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<UserReportDO> list = userReportService.getUserReportPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户汇报.xls", "数据", UserReportRespVO.class,
                        BeanUtils.toBean(list, UserReportRespVO.class));
    }

}