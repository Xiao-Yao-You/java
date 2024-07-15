package com.hk.jigai.module.system.dal.mysql.userreport;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportJobScheduleDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.userreport.vo.*;

/**
 * 汇报工作进度 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ReportJobScheduleMapper extends BaseMapperX<ReportJobScheduleDO> {



}