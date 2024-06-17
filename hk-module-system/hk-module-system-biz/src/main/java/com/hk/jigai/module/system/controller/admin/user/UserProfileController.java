package com.hk.jigai.module.system.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import com.hk.jigai.framework.common.enums.CommonStatusEnum;
import com.hk.jigai.framework.common.enums.UserTypeEnum;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.util.date.DateUtils;
import com.hk.jigai.framework.datapermission.core.annotation.DataPermission;
import com.hk.jigai.framework.security.core.LoginUser;
import com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils;
import com.hk.jigai.framework.tenant.core.context.TenantContextHolder;
import com.hk.jigai.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import com.hk.jigai.module.system.controller.admin.user.vo.profile.UserProfileRespVO;
import com.hk.jigai.module.system.controller.admin.user.vo.profile.UserProfileTenantRespVO;
import com.hk.jigai.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import com.hk.jigai.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import com.hk.jigai.module.system.controller.admin.user.vo.user.UserTenantReqVO;
import com.hk.jigai.module.system.convert.auth.AuthConvert;
import com.hk.jigai.module.system.convert.user.UserConvert;
import com.hk.jigai.module.system.dal.dataobject.dept.DeptDO;
import com.hk.jigai.module.system.dal.dataobject.dept.PostDO;
import com.hk.jigai.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.hk.jigai.module.system.dal.dataobject.permission.RoleDO;
import com.hk.jigai.module.system.dal.dataobject.social.SocialUserDO;
import com.hk.jigai.module.system.dal.dataobject.tenant.TenantDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.enums.oauth2.OAuth2ClientConstants;
import com.hk.jigai.module.system.service.dept.DeptService;
import com.hk.jigai.module.system.service.dept.PostService;
import com.hk.jigai.module.system.service.oauth2.OAuth2TokenService;
import com.hk.jigai.module.system.service.permission.PermissionService;
import com.hk.jigai.module.system.service.permission.RoleService;
import com.hk.jigai.module.system.service.social.SocialUserService;
import com.hk.jigai.module.system.service.tenant.TenantService;
import com.hk.jigai.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.framework.common.pojo.CommonResult.success;
import static com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.hk.jigai.module.infra.enums.ErrorCodeConstants.FILE_IS_EMPTY;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

@Tag(name = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/system/user/profile")
@Validated
@Slf4j
public class UserProfileController {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;
    @Resource
    private SocialUserService socialService;

    @Resource
    private TenantService tenantService;

    @Resource
    private OAuth2TokenService oAuth2TokenService;

    @GetMapping("/get")
    @Operation(summary = "获得登录用户信息")
    @DataPermission(enable = false) // 关闭数据权限，避免只查看自己时，查询不到部门。
    public CommonResult<UserProfileRespVO> getUserProfile() {
        // 获得用户基本信息
        AdminUserDO user = userService.getUser(getLoginUserId());
        // 获得用户角色
        List<RoleDO> userRoles = roleService.getRoleListFromCache(permissionService.getUserRoleIdListByUserId(user.getId()));
        // 获得岗位信息
        List<PostDO> posts = CollUtil.isNotEmpty(user.getPostIds()) ? postService.getPostList(user.getPostIds()) : null;
        // 获得社交用户信息
        List<SocialUserDO> socialUsers = socialService.getSocialUserList(user.getId(), UserTypeEnum.ADMIN.getValue());
        return success(UserConvert.INSTANCE.convert(user, userRoles, user.getDeptList(), posts, socialUsers));
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户个人信息")
    public CommonResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        userService.updateUserProfile(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "修改用户个人密码")
    public CommonResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @RequestMapping(value = "/update-avatar",
            method = {RequestMethod.POST, RequestMethod.PUT}) // 解决 uni-app 不支持 Put 上传文件的问题
    @Operation(summary = "上传用户个人头像")
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw exception(FILE_IS_EMPTY);
        }
        String avatar = userService.updateUserAvatar(getLoginUserId(), file.getInputStream());
        return success(avatar);
    }

    @GetMapping("/queryUserTenant")
    @Operation(summary = "查询个人租户信息")
    public CommonResult<UserProfileTenantRespVO> queryUserTenant() {
        UserProfileTenantRespVO result = userService.queryUserTenant(getLoginUserId());
        return success(result);
    }

    @GetMapping("/modifyTenant")
    @Operation(summary = "用户切换租户")
    public AuthLoginRespVO modifyTenant(@RequestParam("id") Long id) {
        //1.校验租户id 是否正确，以及当前用户是否为该租户
        TenantDO tenant = tenantService.getTenant(id);
        if (tenant == null) {
            throw exception(TENANT_NOT_EXISTS);
        }
        if (tenant.getStatus().equals(CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(TENANT_DISABLE, tenant.getName());
        }
        if (DateUtils.isExpired(tenant.getExpireTime())) {
            throw exception(TENANT_EXPIRE, tenant.getName());
        }
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if(loginUser.getTenantId().equals(id)){
            throw exception(TENANT_DUPLICATE, tenant.getName());
        }

        // 2. 重新生成token
        OAuth2AccessTokenDO accessTokenDO = oAuth2TokenService.createAccessToken(loginUser.getId(), getUserType().getValue(),
                OAuth2ClientConstants.CLIENT_ID_DEFAULT, null);
        //3.转换并返回
        return AuthConvert.INSTANCE.convert(accessTokenDO);
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.MEMBER;
    }


}
