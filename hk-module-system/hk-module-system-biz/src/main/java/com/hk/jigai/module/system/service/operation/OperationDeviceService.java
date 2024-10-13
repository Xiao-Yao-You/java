package com.hk.jigai.module.system.service.operation;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;

/**
 * 运维设备 Service 接口
 *
 * @author 超级管理员
 */
public interface OperationDeviceService {

    /**
     * 创建运维设备
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOperationDevice(@Valid OperationDeviceSaveReqVO createReqVO);

    /**
     * 更新运维设备
     *
     * @param updateReqVO 更新信息
     */
    void updateOperationDevice(@Valid OperationDeviceSaveReqVO updateReqVO);

    /**
     * 删除运维设备
     *
     * @param id 编号
     */
    void deleteOperationDevice(Long id);

    /**
     * 获得运维设备
     *
     * @param id 编号
     * @return 运维设备
     */
    OperationDeviceRespVO getOperationDevice(Long id);

    /**
     * 根据标签号获得运维设备
     *
     * @param labelCode 标签号
     * @return 运维设备
     */
    OperationDeviceRespVO getOperationDeviceByLabelCode(String labelCode);

    /**
     * 获得运维设备分页
     *
     * @param pageReqVO 分页查询
     * @return 运维设备分页
     */
    PageResult<OperationDeviceDO> getOperationDevicePage(OperationDevicePageReqVO pageReqVO);

    /**
     * 设备分配
     * @param registerReqVO
     */
    void register(OperationDeviceRegisterReqVO registerReqVO);

    /**
     * 设备报废
     * @param scrapReqVO
     */
    void scrap(OperationDeviceScrapReqVO scrapReqVO);

}