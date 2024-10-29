package com.hk.jigai.module.system.service.operationnoticeobject;

import java.util.*;
import javax.validation.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.operationnoticeobject.vo.OperationNoticeObjectPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationnoticeobject.vo.OperationNoticeObjectSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationnoticeobject.OperationNoticeObjectDO;

/**
 * 消息通知对象设置 Service 接口
 *
 * @author 邵志伟
 */
public interface OperationNoticeObjectService {

    /**
     * 创建消息通知对象设置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Boolean createOperationNoticeObject(@Valid OperationNoticeObjectSaveReqVO createReqVO);

    /**
     * 更新消息通知对象设置
     *
     * @param updateReqVO 更新信息
     */
    void updateOperationNoticeObject(@Valid OperationNoticeObjectSaveReqVO updateReqVO);

    /**
     * 删除消息通知对象设置
     *
     * @param id 编号
     */
    void deleteOperationNoticeObject(Long id);

    /**
     * 获取所有对象id
     *
     * @return
     */
    List<OperationNoticeObjectDO> getAllUsers();

    /**
     * 获得消息通知对象设置
     *
     * @param id 编号
     * @return 消息通知对象设置
     */
    OperationNoticeObjectDO getOperationNoticeObject(Long id);

    /**
     * 获得消息通知对象设置分页
     *
     * @param pageReqVO 分页查询
     * @return 消息通知对象设置分页
     */
    PageResult<OperationNoticeObjectDO> getOperationNoticeObjectPage(OperationNoticeObjectPageReqVO pageReqVO);

}