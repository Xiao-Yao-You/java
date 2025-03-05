package com.hk.jigai.module.system.service.operationgroup;

import java.util.*;
import javax.validation.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.operationgroup.vo.OperationGroupPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationgroup.vo.OperationGroupSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationgroup.OperationGroupDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;

/**
 * 运维小组 Service 接口
 *
 * @author 邵志伟
 */
public interface OperationGroupService {

    /**
     * 创建运维小组
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOperationGroup(@Valid OperationGroupSaveReqVO createReqVO);

    /**
     * 更新运维小组
     *
     * @param updateReqVO 更新信息
     */
    void updateOperationGroup(@Valid OperationGroupSaveReqVO updateReqVO);

    /**
     * 删除运维小组
     *
     * @param id 编号
     */
    void deleteOperationGroup(Long id);

    /**
     * 获得运维小组
     *
     * @param id 编号
     * @return 运维小组
     */
    OperationGroupDO getOperationGroup(Long id);

    /**
     * 获得运维小组分页
     *
     * @param pageReqVO 分页查询
     * @return 运维小组分页
     */
    PageResult<OperationGroupDO> getOperationGroupPage(OperationGroupPageReqVO pageReqVO);

    /**
     * 获取所有分组
     *
     * @return
     */
    List<OperationGroupDO> getAllGroup();

    /**
     * 获取小组成员
     *
     * @param groupId
     * @return
     */
    List<AdminUserDO> getGroupUsers(Long groupId);

    /**
     * 根据用户id获取所在分组
     *
     * @param userId
     * @return
     */
    List<OperationGroupDO> getGroupByUserId(Long userId);
}