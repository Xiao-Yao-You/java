package com.hk.jigai.module.system.service.operation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceTypeDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationLabelCodeDO;
import com.hk.jigai.module.system.dal.dataobject.scenecode.SceneCodeDO;
import com.hk.jigai.module.system.dal.mysql.operation.OperationDeviceTypeMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationQuestionTypeDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.operation.OperationQuestionTypeMapper;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 运维问题类型 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class OperationQuestionTypeServiceImpl implements OperationQuestionTypeService {

    @Resource
    private OperationQuestionTypeMapper operationQuestionTypeMapper;

    @Resource
    private OperationDeviceTypeMapper operationDeviceTypeMapper;

    @Override
    public Long createOperationQuestionType(OperationQuestionTypeSaveReqVO createReqVO) {
        // 插入
        OperationQuestionTypeDO operationQuestionType = BeanUtils.toBean(createReqVO, OperationQuestionTypeDO.class);
        OperationDeviceTypeDO operationDeviceTypeDO = operationDeviceTypeMapper.selectById(operationQuestionType.getDeviceTypeId());
        operationQuestionType.setDeviceTypeName(operationDeviceTypeDO.getName());
        operationQuestionTypeMapper.insert(operationQuestionType);
        // 返回
        return operationQuestionType.getId();
    }

    @Override
    @Transactional
    public void updateOperationQuestionType(OperationQuestionTypeSaveReqVO updateReqVO) {
        // 校验存在
        validateOperationQuestionTypeExists(updateReqVO.getId());
        // 更新
        OperationQuestionTypeDO updateObj = BeanUtils.toBean(updateReqVO, OperationQuestionTypeDO.class);
        OperationDeviceTypeDO operationDeviceTypeDO = operationDeviceTypeMapper.selectById(updateObj.getDeviceTypeId());
        updateObj.setDeviceTypeName(operationDeviceTypeDO.getName());
        operationQuestionTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteOperationQuestionType(Long id) {
        // 校验存在
        validateOperationQuestionTypeExists(id);
        // 删除
        operationQuestionTypeMapper.deleteById(id);
    }

    private void validateOperationQuestionTypeExists(Long id) {
        if (operationQuestionTypeMapper.selectById(id) == null) {
            throw exception(OPERATION_QUESTION_TYPE_NOT_EXISTS);
        }
    }

    @Override
    public OperationQuestionTypeDO getOperationQuestionType(Long id) {
        return operationQuestionTypeMapper.selectById(id);
    }

    @Override
    public List<OperationQuestionTypeDO> queryAll(OperationQuestionTypeReqVO req) {
        return operationQuestionTypeMapper.selectList(req);
    }

}