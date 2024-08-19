package com.hk.jigai.module.system.controller.admin.wechat;

import com.hk.jigai.framework.common.enums.CommonStatusEnum;
import com.hk.jigai.module.system.controller.admin.permission.vo.menu.*;
import com.hk.jigai.module.system.dal.dataobject.permission.MenuDO;
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

    @GetMapping("/list")
    @Operation(summary = "获取菜单列表", description = "用于【菜单管理】界面")
    @PreAuthorize("@ss.hasPermission('system:menu:query')")
    public CommonResult<List<WechatMenuRespVO>> getMenuList(MenuListReqVO reqVO) {
        List<WechatMenuDO> list = wechatMenuService.getMenuList(reqVO);
        list.sort(Comparator.comparing(WechatMenuDO::getSort));
        return success(BeanUtils.toBean(list, WechatMenuRespVO.class));
    }

    @GetMapping({"/list-all-simple", "simple-list"})
    @Operation(summary = "获取wechat菜单精简信息列表", description = "只包含被开启的菜单，用于【角色分配菜单】功能的选项。" +
            "在多租户的场景下，会只返回租户所在套餐有的菜单")
    public CommonResult<List<MenuSimpleRespVO>> getSimpleMenuList() {
        List<WechatMenuDO> list = wechatMenuService.getMenuListByTenant(
                new MenuListReqVO().setStatus(CommonStatusEnum.ENABLE.getStatus()));
        list.sort(Comparator.comparing(WechatMenuDO::getSort));
        return success(BeanUtils.toBean(list, MenuSimpleRespVO.class));
    }

}