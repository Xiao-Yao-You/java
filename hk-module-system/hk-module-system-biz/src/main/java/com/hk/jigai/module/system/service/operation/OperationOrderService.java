package com.hk.jigai.module.system.service.operation;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import javax.validation.*;

import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderOperateRecordDO;

/**
 * 工单 Service 接口
 *
 * @author 超级管理员
 */
public interface OperationOrderService {

    /**
     * 创建工单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    CreateVO createOperationOrder(@Valid OperationOrderSaveReqVO createReqVO);

    /**
     * 发送创建工单消息
     * @param openIdList
     * @param wechatNoticeVO
     */
    void sendCreateMsg(List<String> openIdList, Map wechatNoticeVO);

    /**
     * 更新工单
     *
     * @param updateReqVO 更新信息
     */
    void updateOperationOrder(@Valid OperationOrderSaveReqVO updateReqVO);

    /**
     * 删除工单
     *
     * @param id 编号
     */
    void deleteOperationOrder(Long id);

    /**
     * 获得工单
     *
     * @param id 编号
     * @return 工单
     */
    OperationOrderDO getOperationOrder(Long id);

    /**
     * 获得工单分页
     *
     * @param pageReqVO 分页查询
     * @return 工单分页
     */
    PageResult<OperationOrderDO> getOperationOrderPage(OperationOrderPageReqVO pageReqVO);

    PageResult<OperationOrderDO> getOperationOrderPageForApp(OperationOrderPageReqVO pageReqVO);

    /**
     * 运维组操作工单
     *
     * @param updateReqVO
     */
    void operateOrder(OperationOrderReqVO updateReqVO);

    /**
     * 工单流转
     *
     * @param operationOrderReqVO
     */
    CommonResult workOrderCirculation(OperationOrderReqVO operationOrderReqVO);

    /**
     * 查询所有未处理的工单数量
     *
     * @return
     */
    CommonResult<Integer> getUnDealOrderCount();
}