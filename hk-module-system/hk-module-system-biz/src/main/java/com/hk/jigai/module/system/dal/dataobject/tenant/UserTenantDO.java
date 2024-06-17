package com.hk.jigai.module.system.dal.dataobject.tenant;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;
import com.hk.jigai.module.system.dal.dataobject.dept.PostDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户和租户关联
 *
 * @author ruoyi
 */
@TableName("system_user_tenant")
@KeySequence("system_user_tenant_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
public class UserTenantDO extends BaseDO {

    /**
     * 自增主键
     */
    @TableId
    private Long id;
    /**
     * 用户 ID
     *
     * 关联 {@link AdminUserDO#getId()}
     */
    private Long userId;
    /**
     * 租户 ID
     *
     * 关联 {@link TenantDO#getId()}
     */
    private Long tenantId;
    /**
     * 租户名称
     */
    @TableField(exist = false)
    private String tenantName;

}
