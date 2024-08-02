package com.hk.jigai.module.system.controller.admin.wechat;

import com.hk.jigai.module.system.controller.admin.permission.vo.menu.WechatMenuPageReqVO;
import com.hk.jigai.module.system.controller.admin.permission.vo.menu.WechatMenuRespVO;
import com.hk.jigai.module.system.controller.admin.permission.vo.menu.WechatMenuSaveReqVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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

import com.hk.jigai.module.system.dal.dataobject.permission.WechatMenuDO;
import com.hk.jigai.module.system.service.permission.WechatMenuService;

@Tag(name = "管理后台 - wechat菜单权限")
@RestController
@RequestMapping("/wechat/menu")
@Validated
public class WechatMenuController {

    @Resource
    private WechatMenuService wechatMenuService;

    @PostMapping("/create")
    @Operation(summary = "创建wechat菜单权限")
    @PreAuthorize("@ss.hasPermission('wechat:menu:create')")
    public CommonResult<Long> createMenu(@Valid @RequestBody WechatMenuSaveReqVO createReqVO) {
        return success(wechatMenuService.createMenu(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新wechat菜单权限")
    @PreAuthorize("@ss.hasPermission('wechat:menu:update')")
    public CommonResult<Boolean> updateMenu(@Valid @RequestBody WechatMenuSaveReqVO updateReqVO) {
        wechatMenuService.updateMenu(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除wechat菜单权限")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wechat:menu:delete')")
    public CommonResult<Boolean> deleteMenu(@RequestParam("id") Long id) {
        wechatMenuService.deleteMenu(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得wechat菜单权限")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wechat:menu:query')")
    public CommonResult<WechatMenuRespVO> getMenu(@RequestParam("id") Long id) {
        WechatMenuDO menu = wechatMenuService.getMenu(id);
        return success(BeanUtils.toBean(menu, WechatMenuRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得wechat菜单权限分页")
    @PreAuthorize("@ss.hasPermission('wechat:menu:query')")
    public CommonResult<PageResult<WechatMenuRespVO>> getMenuPage(@Valid WechatMenuPageReqVO pageReqVO) {
        PageResult<WechatMenuDO> pageResult = wechatMenuService.getMenuPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, WechatMenuRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出wechat菜单权限 Excel")
    @PreAuthorize("@ss.hasPermission('wechat:menu:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMenuExcel(@Valid WechatMenuPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<WechatMenuDO> list = wechatMenuService.getMenuPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "wechat菜单权限.xls", "数据", WechatMenuRespVO.class,
                        BeanUtils.toBean(list, WechatMenuRespVO.class));
    }

}