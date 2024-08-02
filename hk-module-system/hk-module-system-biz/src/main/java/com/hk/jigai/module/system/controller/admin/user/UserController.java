package com.hk.jigai.module.system.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;
import com.hk.jigai.framework.common.enums.CommonStatusEnum;
import com.hk.jigai.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.framework.common.util.collection.MapUtils;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.excel.core.util.ExcelUtils;
import com.hk.jigai.framework.security.core.LoginUser;
import com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils;
import com.hk.jigai.module.system.controller.admin.user.vo.profile.UserProfileTenantRespVO;
import com.hk.jigai.module.system.controller.admin.user.vo.user.*;
import com.hk.jigai.module.system.convert.user.UserConvert;
import com.hk.jigai.module.system.dal.dataobject.dept.DeptDO;
import com.hk.jigai.module.system.dal.dataobject.tenant.UserTenantDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.enums.ErrorCodeConstants;
import com.hk.jigai.module.system.enums.common.SexEnum;
import com.hk.jigai.module.system.enums.logger.LoginResultEnum;
import com.hk.jigai.module.system.service.dept.DeptService;
import com.hk.jigai.module.system.service.permission.PermissionService;
import com.hk.jigai.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.hk.jigai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.framework.common.pojo.CommonResult.success;
import static com.hk.jigai.framework.common.util.collection.CollectionUtils.convertList;
import static com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.AUTH_LOGIN_BAD_CREDENTIALS;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.TENANT_DUPLICATE;

@Tag(name = "管理后台 - 用户")
@RestController
@RequestMapping("/system/user")
@Validated
public class UserController {

    @Resource
    private AdminUserService userService;

    @Resource
    private PermissionService permissionService;

    @PostMapping("/create")
    @Operation(summary = "新增用户")
    @PreAuthorize("@ss.hasPermission('system:user:create')")
    public CommonResult<Long> createUser(@Valid @RequestBody UserSaveReqVO reqVO) {
        Long id = userService.createUser(reqVO);
        return success(id);
    }

    @PutMapping("update")
    @Operation(summary = "修改用户")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<Boolean> updateUser(@Valid @RequestBody UserSaveReqVO reqVO) {
        userService.updateUser(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:user:delete')")
    public CommonResult<Boolean> deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "重置用户密码")
    @PreAuthorize("@ss.hasPermission('system:user:update-password')")
    public CommonResult<Boolean> updateUserPassword(@Valid @RequestBody UserUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(reqVO.getId(), reqVO.getPassword());
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "修改用户状态")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<Boolean> updateUserStatus(@Valid @RequestBody UserUpdateStatusReqVO reqVO) {
        userService.updateUserStatus(reqVO.getId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户分页列表")
    @PreAuthorize("@ss.hasPermission('system:user:list')")
    public CommonResult<PageResult<UserRespVO>> getUserPage(@Valid UserPageReqVO pageReqVO) {
        // 获得用户分页列表
        PageResult<AdminUserDO> pageResult = userService.getUserPage(pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }
        List<UserRespVO> returnList = CollectionUtils.convertList(pageResult.getList(), user -> {
            UserRespVO userVO = BeanUtils.toBean(user, UserRespVO.class);
            return userVO;
        });
        return success(new PageResult<>(returnList, pageResult.getTotal()));
    }

    @GetMapping({"/list-all-simple", "/simple-list"})
    @Operation(summary = "获取用户精简信息列表", description = "只包含被开启的用户，主要用于前端的下拉选项")
    public CommonResult<List<UserSimpleRespVO>> getSimpleUserList() {
        List<AdminUserDO> list = userService.getUserListByStatus(CommonStatusEnum.ENABLE.getStatus());
        List<UserSimpleRespVO> returnList = CollectionUtils.convertList(list, user -> {
            UserSimpleRespVO userVO = BeanUtils.toBean(user, UserSimpleRespVO.class);
            return userVO;
        });
        return success(returnList);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public CommonResult<UserRespVO> getUser(@RequestParam("id") Long id) {
        AdminUserDO user = userService.getUser(id);
        return success(BeanUtils.toBean(user, UserRespVO.class));
    }

    @GetMapping("/export")
    @Operation(summary = "导出用户")
    @PreAuthorize("@ss.hasPermission('system:user:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportUserList(@Validated UserPageReqVO exportReqVO,
                               HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AdminUserDO> list = userService.getUserPage(exportReqVO).getList();
        List<UserRespVO> returnList = CollectionUtils.convertList(list, user -> {
            UserRespVO userVO = BeanUtils.toBean(user, UserRespVO.class);
            return userVO;
        });
        // 输出 Excel
        ExcelUtils.write(response, "用户数据.xls", "数据", UserRespVO.class, returnList);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入用户模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<UserImportExcelVO> list = Arrays.asList(
                UserImportExcelVO.builder().username("yunai").deptIds("1,2").email("yunai@iocoder.cn").mobile("15601691300")
                        .nickname("恒科").status(CommonStatusEnum.ENABLE.getStatus()).sex(SexEnum.MALE.getSex()).build(),
                UserImportExcelVO.builder().username("yuanma").deptIds("1").email("yuanma@iocoder.cn").mobile("15601701300")
                        .nickname("源码").status(CommonStatusEnum.DISABLE.getStatus()).sex(SexEnum.FEMALE.getSex()).build()
        );
        // 输出
        ExcelUtils.write(response, "用户导入模板.xls", "用户列表", UserImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入用户")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('system:user:import')")
    public CommonResult<UserImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                      @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<UserImportExcelVO> list = ExcelUtils.read(file, UserImportExcelVO.class);
        return success(userService.importUserList(list, updateSupport));
    }

    @GetMapping("/ignoreTenant/page")
    @Operation(summary = "获得用户分页列表忽略租户过滤")
    @PreAuthorize("@ss.hasPermission('system:user:list')")
    public CommonResult<PageResult<UserRespVO>> ignoreTenantGetUserPage(@Valid UserPageReqVO pageReqVO) {
        //权限校验,只有超级管理员才有该权限，加菜单也能控制，可以删除该判断
        Set<Long> roleIds = permissionService.getUserRoleIdListByUserId(getLoginUserId());
        if (CollectionUtils.isAnyEmpty() || !roleIds.contains(1)) {
            throw exception(GlobalErrorCodeConstants.FORBIDDEN);
        }
        // 获得用户分页列表
        PageResult<AdminUserDO> pageResult = userService.getUserPage(pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }
        List<UserRespVO> returnList = CollectionUtils.convertList(pageResult.getList(), user -> {
            UserRespVO userVO = BeanUtils.toBean(user, UserRespVO.class);
            return userVO;
        });
        return success(new PageResult<>(returnList, pageResult.getTotal()));
    }

    @PutMapping("/ignoreTenant/updateUserTenant")
    @Operation(summary = "修改用户租户信息")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<Boolean> updateUserTenant(@Valid @RequestBody UserTenantReqVO reqVO) {
        //权限校验,只有超级管理员才有该权限，加菜单也能控制，可以删除该判断
        Set<Long> roleIds = permissionService.getUserRoleIdListByUserId(getLoginUserId());
        if (CollectionUtils.isAnyEmpty(roleIds) || !roleIds.contains(new Long(1))) {
            throw exception(GlobalErrorCodeConstants.FORBIDDEN);
        }
        userService.updateUserTenant(reqVO);
        return success(true);
    }


    @GetMapping("/queryUserTenantByName")
    @PermitAll
    @Operation(summary = "根据用户账号查询个人租户信息")
    public CommonResult<UserProfileTenantRespVO> queryUserTenant(@RequestParam("userName") String userName) {
        //先校验userName是否存在
         AdminUserDO user = userService.getUserByUsername(userName);
        if (user == null) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
        //查询
        UserProfileTenantRespVO result = userService.queryUserTenantByName(userName);
        return success(result);
    }

    @GetMapping("/getAllUser")
    @Operation(summary = "获取所有用户")
    public CommonResult<List<UserRespVO>> getAllUser(@RequestParam("nickname") String nickname) {
        List<UserRespVO> allUser = userService.getAllUser(nickname);
        return success(allUser);
    }
}
