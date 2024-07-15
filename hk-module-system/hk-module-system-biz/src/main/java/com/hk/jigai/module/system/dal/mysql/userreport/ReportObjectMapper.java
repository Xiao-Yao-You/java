package com.hk.jigai.module.system.dal.mysql.userreport;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportObjectDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.userreport.vo.*;

/**
 * 汇报对象 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ReportObjectMapper extends BaseMapperX<ReportObjectDO> {


}