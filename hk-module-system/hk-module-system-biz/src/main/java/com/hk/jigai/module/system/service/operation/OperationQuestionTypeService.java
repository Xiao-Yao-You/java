package com.hk.jigai.module.system.service.operation;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationLabelCodeDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationQuestionTypeDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;

/**
 * 运维问题类型 Service 接口
 *
 * @author 超级管理员
 */
public interface OperationQuestionTypeService {

    /**
     * 创建运维问题类型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOperationQuestionType(@Valid OperationQuestionTypeSaveReqVO createReqVO);

    /**
     * 更新运维问题类型
     *
     * @param updateReqVO 更新信息
     */
    void updateOperationQuestionType(@Valid OperationQuestionTypeSaveReqVO updateReqVO);

    /**
     * 删除运维问题类型
     *
     * @param id 编号
     */
    void deleteOperationQuestionType(Long id);

    /**
     * 获得运维问题类型
     *
     * @param id 编号
     * @return 运维问题类型
     */
    OperationQuestionTypeDO getOperationQuestionType(Long id);

    /**
     * 查询所有问题
     * @return
     */
    List<OperationQuestionTypeDO> queryAll(OperationQuestionTypeReqVO req);

}