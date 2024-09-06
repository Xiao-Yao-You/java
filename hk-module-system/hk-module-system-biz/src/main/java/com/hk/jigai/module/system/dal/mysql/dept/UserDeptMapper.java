package com.hk.jigai.module.system.dal.mysql.dept;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.module.system.dal.dataobject.dept.UserDeptDO;
import com.hk.jigai.module.system.dal.dataobject.dept.UserPostDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserDeptMapper extends BaseMapperX<UserDeptDO> {
    default List<UserDeptDO> selectListByUserId(Long userId) {
        return selectList(UserDeptDO::getUserId, userId);
    }

    default void deleteByUserId(Long userId) {
        delete(new LambdaQueryWrapperX<UserDeptDO>().eq(UserDeptDO::getUserId, userId));
    }

    default List<UserDeptDO> selectListByDeptIds(Collection<Long> deptIds) {
        return selectList(UserDeptDO::getDeptId, deptIds);
    }
    Long queryMaxId();
}