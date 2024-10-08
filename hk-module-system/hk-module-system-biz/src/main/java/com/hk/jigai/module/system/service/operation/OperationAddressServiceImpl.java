package com.hk.jigai.module.system.service.operation;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationAddressDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.operation.OperationAddressMapper;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 运维地点 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class OperationAddressServiceImpl implements OperationAddressService {

    @Resource
    private OperationAddressMapper operationAddressMapper;

    @Override
    public Long createOperationAddress(OperationAddressSaveReqVO createReqVO) {
        // 插入
        OperationAddressDO operationAddress = BeanUtils.toBean(createReqVO, OperationAddressDO.class);
        operationAddressMapper.insert(operationAddress);
        // 返回
        return operationAddress.getId();
    }

    @Override
    public void updateOperationAddress(OperationAddressSaveReqVO updateReqVO) {
        // 校验存在
        validateOperationAddressExists(updateReqVO.getId());
        // 更新
        OperationAddressDO updateObj = BeanUtils.toBean(updateReqVO, OperationAddressDO.class);
        operationAddressMapper.updateById(updateObj);
    }

    @Override
    public void deleteOperationAddress(Long id) {
        // 校验存在
        validateOperationAddressExists(id);
        // 删除
        operationAddressMapper.deleteById(id);
    }

    private void validateOperationAddressExists(Long id) {
        if (operationAddressMapper.selectById(id) == null) {
            throw exception(OPERATION_ADDRESS_NOT_EXISTS);
        }
    }

    @Override
    public OperationAddressDO getOperationAddress(Long id) {
        return operationAddressMapper.selectById(id);
    }

    @Override
    public PageResult<OperationAddressDO> getOperationAddressPage(OperationAddressPageReqVO pageReqVO) {
        return operationAddressMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OperationAddressDO> getAll() {
        return operationAddressMapper.selectList();
    }

}