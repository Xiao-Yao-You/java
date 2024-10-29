package com.hk.jigai.module.system.service.operationdeviceaccessoryhistory;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.module.system.controller.admin.operationdeviceaccessoryhistory.vo.OperationDeviceAccessoryHistoryPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationdeviceaccessoryhistory.vo.OperationDeviceAccessoryHistorySaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdeviceaccessoryhistory.OperationDeviceAccessoryHistoryDO;
import com.hk.jigai.module.system.dal.mysql.operationdeviceaccessoryhistory.OperationDeviceAccessoryHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.OPERATION_DEVICE_ACCESSORY_HISTORY_NOT_EXISTS;

/**
 * 运维设备配件表_快照 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class OperationDeviceAccessoryHistoryServiceImpl implements OperationDeviceAccessoryHistoryService {

    @Resource
    private OperationDeviceAccessoryHistoryMapper operationDeviceAccessoryHistoryMapper;

    @Override
    public Long createOperationDeviceAccessoryHistory(OperationDeviceAccessoryHistorySaveReqVO createReqVO) {
        // 插入
        OperationDeviceAccessoryHistoryDO operationDeviceAccessoryHistory = BeanUtils.toBean(createReqVO, OperationDeviceAccessoryHistoryDO.class);
        operationDeviceAccessoryHistoryMapper.insert(operationDeviceAccessoryHistory);
        // 返回
        return operationDeviceAccessoryHistory.getId();
    }

    @Override
    public void updateOperationDeviceAccessoryHistory(OperationDeviceAccessoryHistorySaveReqVO updateReqVO) {
        // 校验存在
        validateOperationDeviceAccessoryHistoryExists(updateReqVO.getId());
        // 更新
        OperationDeviceAccessoryHistoryDO updateObj = BeanUtils.toBean(updateReqVO, OperationDeviceAccessoryHistoryDO.class);
        operationDeviceAccessoryHistoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteOperationDeviceAccessoryHistory(Long id) {
        // 校验存在
        validateOperationDeviceAccessoryHistoryExists(id);
        // 删除
        operationDeviceAccessoryHistoryMapper.deleteById(id);
    }

    private void validateOperationDeviceAccessoryHistoryExists(Long id) {
        if (operationDeviceAccessoryHistoryMapper.selectById(id) == null) {
            throw exception(OPERATION_DEVICE_ACCESSORY_HISTORY_NOT_EXISTS);
        }
    }

    @Override
    public OperationDeviceAccessoryHistoryDO getOperationDeviceAccessoryHistory(Long id) {
        return operationDeviceAccessoryHistoryMapper.selectById(id);
    }

    @Override
    public PageResult<OperationDeviceAccessoryHistoryDO> getOperationDeviceAccessoryHistoryPage(OperationDeviceAccessoryHistoryPageReqVO pageReqVO) {
        return operationDeviceAccessoryHistoryMapper.selectPage(pageReqVO);
    }

}