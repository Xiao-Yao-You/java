package com.hk.jigai.module.system.service.operationdevicepicturehistory;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.module.system.controller.admin.operationdevicepicturehistory.vo.OperationDevicePictureHistoryPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationdevicepicturehistory.vo.OperationDevicePictureHistorySaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicepicturehistory.OperationDevicePictureHistoryDO;
import com.hk.jigai.module.system.dal.mysql.operationdevicepicturehistory.OperationDevicePictureHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.OPERATION_DEVICE_PICTURE_HISTORY_NOT_EXISTS;

/**
 * 运维设备照片表_快照 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class OperationDevicePictureHistoryServiceImpl implements OperationDevicePictureHistoryService {

    @Resource
    private OperationDevicePictureHistoryMapper operationDevicePictureHistoryMapper;

    @Override
    public Long createOperationDevicePictureHistory(OperationDevicePictureHistorySaveReqVO createReqVO) {
        // 插入
        OperationDevicePictureHistoryDO operationDevicePictureHistory = BeanUtils.toBean(createReqVO, OperationDevicePictureHistoryDO.class);
        operationDevicePictureHistoryMapper.insert(operationDevicePictureHistory);
        // 返回
        return operationDevicePictureHistory.getId();
    }

    @Override
    public void updateOperationDevicePictureHistory(OperationDevicePictureHistorySaveReqVO updateReqVO) {
        // 校验存在
        validateOperationDevicePictureHistoryExists(updateReqVO.getId());
        // 更新
        OperationDevicePictureHistoryDO updateObj = BeanUtils.toBean(updateReqVO, OperationDevicePictureHistoryDO.class);
        operationDevicePictureHistoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteOperationDevicePictureHistory(Long id) {
        // 校验存在
        validateOperationDevicePictureHistoryExists(id);
        // 删除
        operationDevicePictureHistoryMapper.deleteById(id);
    }

    private void validateOperationDevicePictureHistoryExists(Long id) {
        if (operationDevicePictureHistoryMapper.selectById(id) == null) {
            throw exception(OPERATION_DEVICE_PICTURE_HISTORY_NOT_EXISTS);
        }
    }

    @Override
    public OperationDevicePictureHistoryDO getOperationDevicePictureHistory(Long id) {
        return operationDevicePictureHistoryMapper.selectById(id);
    }

    @Override
    public PageResult<OperationDevicePictureHistoryDO> getOperationDevicePictureHistoryPage(OperationDevicePictureHistoryPageReqVO pageReqVO) {
        return operationDevicePictureHistoryMapper.selectPage(pageReqVO);
    }

}