package com.hk.jigai.module.system.framework.datapermission.config;

import com.hk.jigai.module.system.dal.dataobject.dept.DeptDO;
import com.hk.jigai.module.system.dal.dataobject.dept.UserDeptDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的数据权限 Configuration
 *
 * @author 恒科技改
 */
@Configuration(proxyBeanMethods = false)
public class DataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer sysDeptDataPermissionRuleCustomizer() {
        return rule -> {
            //dept
            //rule.addDeptColumn(UserDeptDO.class);  //该字段调整为user_dept表
            //rule.addDeptColumn(DeptDO.class, "id");
            // user
            rule.addUserColumn(AdminUserDO.class, "id");
        };
    }

}
