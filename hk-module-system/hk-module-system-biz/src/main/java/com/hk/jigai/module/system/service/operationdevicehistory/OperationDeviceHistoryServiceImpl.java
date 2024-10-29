package com.hk.jigai.module.system.service.operationdevicehistory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.module.system.controller.admin.operationdevicehistory.vo.OperationDeviceHistoryPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationdevicehistory.vo.OperationDeviceHistorySaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdeviceaccessoryhistory.OperationDeviceAccessoryHistoryDO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicehistory.OperationDeviceHistoryDO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicepicturehistory.OperationDevicePictureHistoryDO;
import com.hk.jigai.module.system.dal.mysql.operationdeviceaccessoryhistory.OperationDeviceAccessoryHistoryMapper;
import com.hk.jigai.module.system.dal.mysql.operationdevicehistory.OperationDeviceHistoryMapper;
import com.hk.jigai.module.system.dal.mysql.operationdevicepicturehistory.OperationDevicePictureHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.List;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.OPERATION_DEVICE_HISTORY_NOT_EXISTS;

/**
 * 运维设备 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class OperationDeviceHistoryServiceImpl implements OperationDeviceHistoryService {

    @Resource
    private OperationDeviceHistoryMapper operationDeviceHistoryMapper;
    @Resource
    private OperationDevicePictureHistoryMapper operationDevicePictureHistoryMapper;
    @Resource
    private OperationDeviceAccessoryHistoryMapper operationDeviceAccessoryHistoryMapper;

    @Override
    public Long createOperationDeviceHistory(OperationDeviceHistorySaveReqVO createReqVO) {
        // 插入
        OperationDeviceHistoryDO operationDeviceHistory = BeanUtils.toBean(createReqVO, OperationDeviceHistoryDO.class);
        operationDeviceHistoryMapper.insert(operationDeviceHistory);
        // 返回
        return operationDeviceHistory.getId();
    }

    @Override
    public void updateOperationDeviceHistory(OperationDeviceHistorySaveReqVO updateReqVO) {
        // 校验存在
        validateOperationDeviceHistoryExists(updateReqVO.getId());
        // 更新
        OperationDeviceHistoryDO updateObj = BeanUtils.toBean(updateReqVO, OperationDeviceHistoryDO.class);
        operationDeviceHistoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteOperationDeviceHistory(Long id) {
        // 校验存在
        validateOperationDeviceHistoryExists(id);
        // 删除
        operationDeviceHistoryMapper.deleteById(id);
    }

    private void validateOperationDeviceHistoryExists(Long id) {
        if (operationDeviceHistoryMapper.selectById(id) == null) {
            throw exception(OPERATION_DEVICE_HISTORY_NOT_EXISTS);
        }
    }

    @Override
    public OperationDeviceHistoryDO getOperationDeviceHistory(Long id) {
        OperationDeviceHistoryDO operationDeviceHistoryDO = operationDeviceHistoryMapper.selectById(id);
        List<OperationDevicePictureHistoryDO> operationDevicePictureHistoryDOS = operationDevicePictureHistoryMapper.selectList(new QueryWrapper<OperationDevicePictureHistoryDO>().lambda().eq(OperationDevicePictureHistoryDO::getHistoryId, id));
        List<OperationDeviceAccessoryHistoryDO> operationDeviceAccessoryHistoryDOS = operationDeviceAccessoryHistoryMapper.selectList(new QueryWrapper<OperationDeviceAccessoryHistoryDO>().lambda().eq(OperationDeviceAccessoryHistoryDO::getHistoryId, id));
        operationDeviceHistoryDO.setOperationDevicePictureHistoryDOS(operationDevicePictureHistoryDOS);
        operationDeviceHistoryDO.setOperationDeviceAccessoryHistoryDOS(operationDeviceAccessoryHistoryDOS);
        return operationDeviceHistoryDO;
    }

    @Override
    public PageResult<OperationDeviceHistoryDO> getOperationDeviceHistoryPage(OperationDeviceHistoryPageReqVO pageReqVO) {
        return operationDeviceHistoryMapper.selectPage(pageReqVO);
    }

}