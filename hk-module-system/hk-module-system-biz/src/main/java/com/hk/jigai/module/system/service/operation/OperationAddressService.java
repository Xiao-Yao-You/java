package com.hk.jigai.module.system.service.operation;

import java.util.*;
import javax.validation.*;

import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationAddressDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;

/**
 * 运维地点 Service 接口
 *
 * @author 超级管理员
 */
public interface OperationAddressService {

    /**
     * 创建运维地点
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOperationAddress(@Valid OperationAddressSaveReqVO createReqVO);

    /**
     * 更新运维地点
     *
     * @param updateReqVO 更新信息
     */
    void updateOperationAddress(@Valid OperationAddressSaveReqVO updateReqVO);

    /**
     * 删除运维地点
     *
     * @param id 编号
     */
    void deleteOperationAddress(Long id);

    /**
     * 获得运维地点
     *
     * @param id 编号
     * @return 运维地点
     */
    OperationAddressDO getOperationAddress(Long id);

    /**
     * 获得运维地点分页
     *
     * @param pageReqVO 分页查询
     * @return 运维地点分页
     */
    PageResult<OperationAddressDO> getOperationAddressPage(OperationAddressPageReqVO pageReqVO);

    /**
     * 所有地点
     *
     * @return
     */
    List<OperationAddressDO> getAll(OperationAddressRespVO reqVO);

    /**
     * 导入厂区地点
     *
     * @param list
     * @param updateSupport
     * @return
     */
    AddressImportRespVO importAddressList(List<AddressImportExcelVO> list, Boolean updateSupport);

    OperationAddressDO getAddressByAddress(String address);
}