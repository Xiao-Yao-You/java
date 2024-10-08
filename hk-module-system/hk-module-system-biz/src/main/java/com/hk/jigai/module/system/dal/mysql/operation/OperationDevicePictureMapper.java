package com.hk.jigai.module.system.dal.mysql.operation;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDevicePictureDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 运维设备照片表 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface OperationDevicePictureMapper extends BaseMapperX<OperationDevicePictureDO> {

}