package com.hk.jigai.module.system.dal.mysql.userreport;

import java.sql.Wrapper;
import java.util.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.userreport.UserReportDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.userreport.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户汇报 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface UserReportMapper extends BaseMapperX<UserReportDO> {

    PageResult<UserReportDO> selectPage(UserReportPageReqVO reqVO);

    Long selectCount(Map<String, Object> requestMap);
}