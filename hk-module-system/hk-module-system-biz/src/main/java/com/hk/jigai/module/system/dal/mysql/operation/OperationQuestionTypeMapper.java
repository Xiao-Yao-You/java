package com.hk.jigai.module.system.dal.mysql.operation;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationLabelCodeDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationQuestionTypeDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;

/**
 * 运维问题类型 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface OperationQuestionTypeMapper extends BaseMapperX<OperationQuestionTypeDO> {

    default List<OperationQuestionTypeDO> selectList(OperationQuestionTypeReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OperationQuestionTypeDO>()
                .likeIfPresent(OperationQuestionTypeDO::getName, reqVO.getName())
                .eqIfPresent(OperationQuestionTypeDO::getParentId, reqVO.getParentId())
                .eqIfPresent(OperationQuestionTypeDO::getType, reqVO.getType())
                .eqIfPresent(OperationQuestionTypeDO::getDeviceTypeId, reqVO.getDeviceTypeId())
                .eqIfPresent(OperationQuestionTypeDO::getDeviceTypeName, reqVO.getDeviceTypeName())
                .eqIfPresent(OperationQuestionTypeDO::getDescription, reqVO.getDescription())
                .eqIfPresent(OperationQuestionTypeDO::getSolution, reqVO.getSolution())
                .betweenIfPresent(OperationQuestionTypeDO::getCreateTime, reqVO.getCreateTime()));
    }


}