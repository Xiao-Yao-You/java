package com.hk.jigai.module.system.service.operationgroup;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.module.system.controller.admin.operationgroup.vo.OperationGroupPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationgroup.vo.OperationGroupSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationgroup.OperationGroupDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.dal.mysql.operationgroup.OperationGroupMapper;
import com.hk.jigai.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.OPERATION_GROUP_EXISTS;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.OPERATION_GROUP_NOT_EXISTS;

/**
 * 运维小组 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class OperationGroupServiceImpl implements OperationGroupService {

    @Resource
    private OperationGroupMapper operationGroupMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Long createOperationGroup(OperationGroupSaveReqVO createReqVO) {

        List<OperationGroupDO> operationGroupDOS = operationGroupMapper.selectList(new QueryWrapper<OperationGroupDO>().lambda().eq(OperationGroupDO::getGroup, createReqVO.getGroup()));
        if (CollectionUtil.isNotEmpty(operationGroupDOS)) {
            throw exception(OPERATION_GROUP_EXISTS);
        }
        // 插入
        OperationGroupDO operationGroup = BeanUtils.toBean(createReqVO, OperationGroupDO.class);
        operationGroupMapper.insert(operationGroup);
        // 返回
        return operationGroup.getId();
    }

    @Override
    public void updateOperationGroup(OperationGroupSaveReqVO updateReqVO) {
        // 校验存在
        validateOperationGroupExists(updateReqVO.getId());

        List<OperationGroupDO> operationGroupDOS = operationGroupMapper.selectList(new QueryWrapper<OperationGroupDO>().lambda()
                .eq(OperationGroupDO::getGroup, updateReqVO.getGroup())
                .ne(OperationGroupDO::getId, updateReqVO.getId()));
        //存在不是本条数据的相同请求类型的分组
        if (CollectionUtil.isNotEmpty(operationGroupDOS)) {
            throw exception(OPERATION_GROUP_EXISTS);
        }
        // 更新
        OperationGroupDO updateObj = BeanUtils.toBean(updateReqVO, OperationGroupDO.class);
        operationGroupMapper.updateById(updateObj);
    }

    @Override
    public void deleteOperationGroup(Long id) {
        // 校验存在
        validateOperationGroupExists(id);
        // 删除
        operationGroupMapper.deleteById(id);
    }

    private void validateOperationGroupExists(Long id) {
        if (operationGroupMapper.selectById(id) == null) {
            throw exception(OPERATION_GROUP_NOT_EXISTS);
        }
    }

    @Override
    public OperationGroupDO getOperationGroup(Long id) {
        return operationGroupMapper.selectById(id);
    }

    @Override
    public PageResult<OperationGroupDO> getOperationGroupPage(OperationGroupPageReqVO pageReqVO) {
        return operationGroupMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OperationGroupDO> getAllGroup() {
        return operationGroupMapper.selectList();
    }

    @Override
    public List<AdminUserDO> getGroupUsers(Long groupId) {
        OperationGroupDO operationGroupDO = operationGroupMapper.selectOne(new QueryWrapper<OperationGroupDO>().lambda().eq(OperationGroupDO::getGroup, groupId));
        Set<Long> userIds = operationGroupDO.getUserIds();
        List<AdminUserDO> adminUserDOS = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userIds)) {
            adminUserDOS = adminUserMapper.selectList(new QueryWrapper<AdminUserDO>().lambda().in(AdminUserDO::getId, userIds));
        }
        return adminUserDOS;
    }

}