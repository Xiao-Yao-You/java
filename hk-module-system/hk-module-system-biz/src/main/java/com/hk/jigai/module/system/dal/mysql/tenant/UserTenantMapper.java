package com.hk.jigai.module.system.dal.mysql.tenant;

import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.module.system.dal.dataobject.dept.UserDeptDO;
import com.hk.jigai.module.system.dal.dataobject.tenant.UserTenantDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserTenantMapper extends BaseMapperX<UserTenantDO> {
    List<UserTenantDO> selectListByUserId(Long userId);

    default void deleteByUserId(Long userId) {
        delete(new LambdaQueryWrapperX<UserTenantDO>().eq(UserTenantDO::getUserId, userId));
    }

    default List<UserTenantDO> selectListByTenantIds(Collection<Long> tenantIds) {
        return selectList(UserTenantDO::getTenantId, tenantIds);
    }
}