package com.hk.jigai.module.system.service.operationdevicemodel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.module.system.controller.admin.operation.vo.QuestionTypeImportRespVO;
import com.hk.jigai.module.system.controller.admin.operationdevicemodel.vo.ModelImportExcelVO;
import com.hk.jigai.module.system.controller.admin.operationdevicemodel.vo.ModelImportRespVO;
import com.hk.jigai.module.system.controller.admin.operationdevicemodel.vo.OperationDeviceModelPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationdevicemodel.vo.OperationDeviceModelSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceTypeDO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicemodel.OperationDeviceModelDO;
import com.hk.jigai.module.system.dal.mysql.operation.OperationDeviceTypeMapper;
import com.hk.jigai.module.system.dal.mysql.operationdevicemodel.OperationDeviceModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.IMPORT_LIST_IS_EMPTY;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.OPERATION_DEVICE_MODEL_NOT_EXISTS;

/**
 * 设备型号 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class OperationDeviceModelServiceImpl implements OperationDeviceModelService {

    @Resource
    private OperationDeviceModelMapper operationDeviceModelMapper;


    @Resource
    private OperationDeviceTypeMapper operationDeviceTypeMapper;

    @Override
    public Long createOperationDeviceModel(OperationDeviceModelSaveReqVO createReqVO) {
        // 插入
        OperationDeviceModelDO operationDeviceModel = BeanUtils.toBean(createReqVO, OperationDeviceModelDO.class);
        operationDeviceModelMapper.insert(operationDeviceModel);
        // 返回
        return operationDeviceModel.getId();
    }

    @Override
    public void updateOperationDeviceModel(OperationDeviceModelSaveReqVO updateReqVO) {
        // 校验存在
        validateOperationDeviceModelExists(updateReqVO.getId());
        // 更新
        OperationDeviceModelDO updateObj = BeanUtils.toBean(updateReqVO, OperationDeviceModelDO.class);
        operationDeviceModelMapper.updateById(updateObj);
    }

    @Override
    public void deleteOperationDeviceModel(Long id) {
        // 校验存在
        validateOperationDeviceModelExists(id);
        // 删除
        operationDeviceModelMapper.deleteById(id);
    }

    private void validateOperationDeviceModelExists(Long id) {
        if (operationDeviceModelMapper.selectById(id) == null) {
            throw exception(OPERATION_DEVICE_MODEL_NOT_EXISTS);
        }
    }

    @Override
    public OperationDeviceModelDO getOperationDeviceModel(Long id) {
        return operationDeviceModelMapper.selectById(id);
    }

    @Override
    public PageResult<OperationDeviceModelDO> getOperationDeviceModelPage(OperationDeviceModelPageReqVO pageReqVO) {
        return operationDeviceModelMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional
    public ModelImportRespVO importModelList(List<ModelImportExcelVO> list, Boolean updateSupport) {
        if (CollUtil.isEmpty(list)) {
            throw exception(IMPORT_LIST_IS_EMPTY);
        }
        ModelImportRespVO respVO = ModelImportRespVO.builder().createList(new ArrayList<>())
                .updateList(new ArrayList<>()).failureList(new LinkedHashMap<>()).build();
        //处理数据
        if (CollectionUtil.isNotEmpty(list)) {

            list.forEach(item -> {
                OperationDeviceModelDO operationDeviceModelDO = BeanUtils.toBean(item, OperationDeviceModelDO.class);
                String deviceType = item.getDeviceType();
                OperationDeviceTypeDO operationDeviceTypeDO = operationDeviceTypeMapper.selectOne(new QueryWrapper<OperationDeviceTypeDO>().lambda().eq(OperationDeviceTypeDO::getName, deviceType));
                //获取设备类型的值
                if (operationDeviceTypeDO != null) {
                    operationDeviceModelDO.setDeviceTypeId(operationDeviceModelDO.getId());
                } else {
                    respVO.getFailureList().put(item.getModel(), "未找到匹配的设备类型");
                }

                operationDeviceModelMapper.insert(operationDeviceModelDO);
                respVO.getCreateList().add(operationDeviceModelDO.getModel());
            });

        }

        return respVO;

    }

    @Override
    public List<OperationDeviceModelDO> getByDeviceTypeId(Long deviceId) {
        List<OperationDeviceModelDO> operationDeviceModelDOS = operationDeviceModelMapper.selectList(new QueryWrapper<OperationDeviceModelDO>().lambda().eq(OperationDeviceModelDO::getDeviceTypeId, deviceId));
        return operationDeviceModelDOS;
    }

}