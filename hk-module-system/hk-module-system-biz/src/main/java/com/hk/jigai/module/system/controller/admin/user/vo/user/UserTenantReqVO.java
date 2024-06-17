package com.hk.jigai.module.system.controller.admin.user.vo.user;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hk.jigai.framework.common.validation.Mobile;
import com.hk.jigai.module.system.framework.operatelog.core.PostParseFunction;
import com.hk.jigai.module.system.framework.operatelog.core.SexParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Schema(description = "管理后台 - 用户创建/修改租户 Request VO")
@Data
public class UserTenantReqVO {

    @Schema(description = "用户编号", example = "1024")
    private Long userId;

    @Schema(description = "租户编号集", example = "{1024}")
    private Set<Long> tenantIdList;

    @Schema(description = "组合类型，0一般（自行选填） 1全部", example = "0")
    private Long tenantType;
}
