package com.hk.jigai.module.system.service.operationnoticeobject;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.module.system.controller.admin.operationnoticeobject.vo.OperationNoticeObjectPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationnoticeobject.vo.OperationNoticeObjectSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationnoticeobject.OperationNoticeObjectDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.dal.mysql.operationnoticeobject.OperationNoticeObjectMapper;
import com.hk.jigai.module.system.service.user.AdminUserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.OPERATION_NOTICE_OBJECT_NOT_EXISTS;


/**
 * 消息通知对象设置 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class OperationNoticeObjectServiceImpl implements OperationNoticeObjectService {

    @Resource
    private OperationNoticeObjectMapper operationNoticeObjectMapper;
    @Resource
    private AdminUserService adminUserService;

    @Override
    public Boolean createOperationNoticeObject(OperationNoticeObjectSaveReqVO createReqVO) {
        //先删除
        operationNoticeObjectMapper.delete(new QueryWrapper<OperationNoticeObjectDO>().lambda().isNotNull(OperationNoticeObjectDO::getId));
        List<OperationNoticeObjectDO> operationNoticeObjectList = new ArrayList<>();
        for (Long id : createReqVO.getUserId()) {
            OperationNoticeObjectDO operationNoticeObject = new OperationNoticeObjectDO();
            operationNoticeObject.setUserId(id);
            AdminUserDO user = adminUserService.getUser(id);
            if (user != null) {
                operationNoticeObject.setNickname(user.getNickname());
            }
            operationNoticeObjectList.add(operationNoticeObject);
        }
        operationNoticeObjectMapper.insertBatch(operationNoticeObjectList);
        // 返回
        return true;
    }

    @Override
    public void updateOperationNoticeObject(OperationNoticeObjectSaveReqVO updateReqVO) {

        //先删除
        operationNoticeObjectMapper.delete(new QueryWrapper<OperationNoticeObjectDO>().lambda().isNotNull(OperationNoticeObjectDO::getId));
        //再新增
        List<OperationNoticeObjectDO> operationNoticeObjectList = new ArrayList<>();
        for (Long id : updateReqVO.getUserId()) {
            OperationNoticeObjectDO operationNoticeObject = new OperationNoticeObjectDO();
            operationNoticeObject.setUserId(id);
            AdminUserDO user = adminUserService.getUser(id);
            if (user != null) {
                operationNoticeObject.setNickname(user.getNickname());
            }
            operationNoticeObjectList.add(operationNoticeObject);
        }
        operationNoticeObjectMapper.insertBatch(operationNoticeObjectList);
    }

    @Override
    public void deleteOperationNoticeObject(Long id) {
        // 校验存在
        validateOperationNoticeObjectExists(id);
        // 删除
        operationNoticeObjectMapper.deleteById(id);
    }

    @Override
    public List<OperationNoticeObjectDO> getAllUsers() {

        List<OperationNoticeObjectDO> operationNoticeObjectDOS = operationNoticeObjectMapper.selectList();
        return operationNoticeObjectDOS;
    }

    private void validateOperationNoticeObjectExists(Long id) {
        if (operationNoticeObjectMapper.selectById(id) == null) {
            throw exception(OPERATION_NOTICE_OBJECT_NOT_EXISTS);
        }
    }

    @Override
    public OperationNoticeObjectDO getOperationNoticeObject(Long id) {
        return operationNoticeObjectMapper.selectById(id);
    }

    @Override
    public PageResult<OperationNoticeObjectDO> getOperationNoticeObjectPage(OperationNoticeObjectPageReqVO pageReqVO) {
        return operationNoticeObjectMapper.selectPage(pageReqVO);
    }

}