package com.hk.jigai.module.system.service.operation;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceTypeDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationLabelCodeDO;

/**
 * 运维设备类型 Service 接口
 *
 * @author 超级管理员
 */
public interface OperationDeviceTypeService {

    /**
     * 创建运维设备类型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOperationDeviceType(@Valid OperationDeviceTypeSaveReqVO createReqVO);

    /**
     * 更新运维设备类型
     *
     * @param updateReqVO 更新信息
     */
    void updateOperationDeviceType(@Valid OperationDeviceTypeSaveReqVO updateReqVO);

    /**
     * 删除运维设备类型
     *
     * @param id 编号
     */
    void deleteOperationDeviceType(Long id);

    /**
     * 获得运维设备类型
     *
     * @param id 编号
     * @return 运维设备类型
     */
    OperationDeviceTypeDO getOperationDeviceType(Long id);

    /**
     * 获得运维设备类型分页
     *
     * @param pageReqVO 分页查询
     * @return 运维设备类型分页
     */
    PageResult<OperationDeviceTypeDO> getOperationDeviceTypePage(OperationDeviceTypePageReqVO pageReqVO);

    /**
     * 查询所有设备类型
     * @return
     */
    List<OperationDeviceTypeDO> getAll();

    /**
     * 预览
     * @param id
     * @return
     */
    OperationLabelCodeDO preview(Long id);


    /**
     * 标签的批量生成
     * @param req
     */
    List<BatchPrintLabelRespVO> batchCreateLabelCode(OperationBatchCreateLabelReqVO req);

}