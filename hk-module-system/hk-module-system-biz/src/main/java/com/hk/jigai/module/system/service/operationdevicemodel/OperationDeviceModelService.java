package com.hk.jigai.module.system.service.operationdevicemodel;

import java.util.*;
import javax.validation.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.operationdevicemodel.vo.ModelImportExcelVO;
import com.hk.jigai.module.system.controller.admin.operationdevicemodel.vo.ModelImportRespVO;
import com.hk.jigai.module.system.controller.admin.operationdevicemodel.vo.OperationDeviceModelPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationdevicemodel.vo.OperationDeviceModelSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicemodel.OperationDeviceModelDO;

/**
 * 设备型号 Service 接口
 *
 * @author 邵志伟
 */
public interface OperationDeviceModelService {

    /**
     * 创建设备型号
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOperationDeviceModel(@Valid OperationDeviceModelSaveReqVO createReqVO);

    /**
     * 更新设备型号
     *
     * @param updateReqVO 更新信息
     */
    void updateOperationDeviceModel(@Valid OperationDeviceModelSaveReqVO updateReqVO);

    /**
     * 删除设备型号
     *
     * @param id 编号
     */
    void deleteOperationDeviceModel(Long id);

    /**
     * 获得设备型号
     *
     * @param id 编号
     * @return 设备型号
     */
    OperationDeviceModelDO getOperationDeviceModel(Long id);

    /**
     * 获得设备型号分页
     *
     * @param pageReqVO 分页查询
     * @return 设备型号分页
     */
    PageResult<OperationDeviceModelDO> getOperationDeviceModelPage(OperationDeviceModelPageReqVO pageReqVO);

    /**
     * 导入设备模型
     *
     * @param list
     * @param updateSupport
     * @return
     */
    ModelImportRespVO importModelList(List<ModelImportExcelVO> list, Boolean updateSupport);

    /**
     * 根据设备类型获取设备型号
     *
     * @param deviceId
     * @return
     */
    List<OperationDeviceModelDO> getByDeviceTypeId(Long deviceId);

    List<OperationDeviceModelDO> getAllModel();
}