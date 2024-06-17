package com.hk.jigai.module.system.dal.mysql.user;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.module.system.controller.admin.user.vo.user.UserPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    List<AdminUserDO> selectListByNickname(String nickname);

    List<AdminUserDO> selectListByStatus(Integer status);

    List<AdminUserDO> selectListByDeptIds(Collection<Long> deptIds);

    Integer selectCount(Map<String, Object> requestMap);


}
