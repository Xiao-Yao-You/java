package com.hk.jigai.module.system.dal.mysql.user;

import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminUserMapper extends BaseMapperX<AdminUserDO> {

    AdminUserDO selectById(Long id);

    AdminUserDO selectByUsername(String username);

    AdminUserDO selectByEmail(String email);

    AdminUserDO selectByMobile(String mobile);

    List<AdminUserDO> selectPage(Map<String, Object> requestMap);

    List<AdminUserDO> selectListByNickname(Map<String, Object> requestMap);

    List<AdminUserDO> selectListByStatus(Integer status);

    List<AdminUserDO> selectListByDeptIds(Collection<Long> deptIds);

    Integer selectCount1(Map<String, Object> requestMap);

    Integer selectCount2();

    Long getUserIdByNickNameAndDeptId(String nickName,Long deptId);

    Long queryMaxId();
}
