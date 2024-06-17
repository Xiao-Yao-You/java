package com.hk.jigai.module.system.controller.admin.user.vo.profile;


import com.hk.jigai.module.system.dal.dataobject.tenant.UserTenantDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Schema(description = "管理后台 - 用户个人中心查询租户 Response VO")
@Data
public class UserProfileTenantRespVO {

    @Schema(description = "租户信息列表", requiredMode = Schema.RequiredMode.REQUIRED, example = "{1,\"test\"}")
    private List<UserTenantDO> tenantDOList;

}
