package com.hk.jigai.module.system.service.operationdeviceaccessoryhistory;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.module.system.controller.admin.operationdeviceaccessoryhistory.vo.OperationDeviceAccessoryHistoryPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationdeviceaccessoryhistory.vo.OperationDeviceAccessoryHistorySaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdeviceaccessoryhistory.OperationDeviceAccessoryHistoryDO;

import javax.validation.Valid;

/**
 * 运维设备配件表_快照 Service 接口
 *
 * @author 邵志伟
 */
public interface OperationDeviceAccessoryHistoryService {

    /**
     * 创建运维设备配件表_快照
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOperationDeviceAccessoryHistory(@Valid OperationDeviceAccessoryHistorySaveReqVO createReqVO);

    /**
     * 更新运维设备配件表_快照
     *
     * @param updateReqVO 更新信息
     */
    void updateOperationDeviceAccessoryHistory(@Valid OperationDeviceAccessoryHistorySaveReqVO updateReqVO);

    /**
     * 删除运维设备配件表_快照
     *
     * @param id 编号
     */
    void deleteOperationDeviceAccessoryHistory(Long id);

    /**
     * 获得运维设备配件表_快照
     *
     * @param id 编号
     * @return 运维设备配件表_快照
     */
    OperationDeviceAccessoryHistoryDO getOperationDeviceAccessoryHistory(Long id);

    /**
     * 获得运维设备配件表_快照分页
     *
     * @param pageReqVO 分页查询
     * @return 运维设备配件表_快照分页
     */
    PageResult<OperationDeviceAccessoryHistoryDO> getOperationDeviceAccessoryHistoryPage(OperationDeviceAccessoryHistoryPageReqVO pageReqVO);

}