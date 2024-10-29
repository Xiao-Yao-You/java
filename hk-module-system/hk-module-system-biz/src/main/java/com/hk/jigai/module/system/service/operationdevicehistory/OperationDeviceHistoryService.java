package com.hk.jigai.module.system.service.operationdevicehistory;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.module.system.controller.admin.operationdevicehistory.vo.OperationDeviceHistoryPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationdevicehistory.vo.OperationDeviceHistorySaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicehistory.OperationDeviceHistoryDO;

import javax.validation.Valid;

/**
 * 运维设备 Service 接口
 *
 * @author 邵志伟
 */
public interface OperationDeviceHistoryService {

    /**
     * 创建运维设备
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOperationDeviceHistory(@Valid OperationDeviceHistorySaveReqVO createReqVO);

    /**
     * 更新运维设备
     *
     * @param updateReqVO 更新信息
     */
    void updateOperationDeviceHistory(@Valid OperationDeviceHistorySaveReqVO updateReqVO);

    /**
     * 删除运维设备
     *
     * @param id 编号
     */
    void deleteOperationDeviceHistory(Long id);

    /**
     * 获得运维设备
     *
     * @param id 编号
     * @return 运维设备
     */
    OperationDeviceHistoryDO getOperationDeviceHistory(Long id);

    /**
     * 获得运维设备分页
     *
     * @param pageReqVO 分页查询
     * @return 运维设备分页
     */
    PageResult<OperationDeviceHistoryDO> getOperationDeviceHistoryPage(OperationDeviceHistoryPageReqVO pageReqVO);

}