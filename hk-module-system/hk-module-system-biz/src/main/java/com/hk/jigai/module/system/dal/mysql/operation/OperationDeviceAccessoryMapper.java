package com.hk.jigai.module.system.dal.mysql.operation;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceAccessoryDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;

/**
 * 运维设备配件 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface OperationDeviceAccessoryMapper extends BaseMapperX<OperationDeviceAccessoryDO> {

}