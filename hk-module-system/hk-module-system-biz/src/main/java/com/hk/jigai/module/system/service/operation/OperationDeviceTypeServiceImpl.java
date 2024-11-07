package com.hk.jigai.module.system.service.operation;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.module.system.controller.admin.scenecode.vo.SceneCodeImportRespVO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationLabelCodeDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationQuestionTypeDO;
import com.hk.jigai.module.system.dal.dataobject.scenecode.SceneCodeDO;
import com.hk.jigai.module.system.dal.mysql.operation.OperationLabelCodeMapper;
import com.hk.jigai.module.system.dal.mysql.scenecode.SceneCodeMapper;
import com.hk.jigai.module.system.service.scenecode.SceneCodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceTypeDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.operation.OperationDeviceTypeMapper;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 运维设备类型 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class OperationDeviceTypeServiceImpl implements OperationDeviceTypeService {

    @Resource
    private OperationDeviceTypeMapper operationDeviceTypeMapper;
    @Resource
    private OperationLabelCodeMapper operationLabelCodeMapper;

    @Resource
    private SceneCodeService sceneCodeService;

    @Resource
    private SceneCodeMapper sceneCodeMapper;

    @Override
    public Long createOperationDeviceType(OperationDeviceTypeSaveReqVO createReqVO) {
        // 插入
        OperationDeviceTypeDO operationDeviceType = BeanUtils.toBean(createReqVO, OperationDeviceTypeDO.class);
        operationDeviceTypeMapper.insert(operationDeviceType);
        // 返回
        return operationDeviceType.getId();
    }

    @Override
    public void updateOperationDeviceType(OperationDeviceTypeSaveReqVO updateReqVO) {
        // 校验存在
        validateOperationDeviceTypeExists(updateReqVO.getId());
        // 更新
        OperationDeviceTypeDO updateObj = BeanUtils.toBean(updateReqVO, OperationDeviceTypeDO.class);
        operationDeviceTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteOperationDeviceType(Long id) {
        // 校验存在
        validateOperationDeviceTypeExists(id);
        // 删除
        operationDeviceTypeMapper.deleteById(id);
    }

    private void validateOperationDeviceTypeExists(Long id) {
        if (operationDeviceTypeMapper.selectById(id) == null) {
            throw exception(OPERATION_DEVICE_TYPE_NOT_EXISTS);
        }
    }

    @Override
    public OperationDeviceTypeDO getOperationDeviceType(Long id) {
        return operationDeviceTypeMapper.selectById(id);
    }

    @Override
    public PageResult<OperationDeviceTypeDO> getOperationDeviceTypePage(OperationDeviceTypePageReqVO pageReqVO) {
        return operationDeviceTypeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OperationDeviceTypeDO> getAll() {
        return operationDeviceTypeMapper.selectList();
    }

    @Override
    public OperationLabelCodeDO preview(Long id) {
        validateOperationDeviceTypeExists(id);
        List<OperationLabelCodeDO> list = operationLabelCodeMapper.selectList(new QueryWrapper<OperationLabelCodeDO>()
                .lambda().eq(OperationLabelCodeDO::getDeviceId, id).orderByDesc(OperationLabelCodeDO::getId));
        if (!CollectionUtils.isAnyEmpty(list)) {
            return list.get(0);
        } else {
            return new OperationLabelCodeDO();
        }
    }

    @Override
    @Transactional
    public List<BatchPrintLabelRespVO> batchCreateLabelCode(OperationBatchCreateLabelReqVO req) {
        OperationDeviceTypeDO operationLabelDO = operationDeviceTypeMapper.selectById(req.getId());
        if (operationLabelDO == null) {
            throw exception(OPERATION_LABEL_NOT_EXISTS);
        }
        SceneCodeDO sceneCodeDO = sceneCodeService.getSceneCode(operationLabelDO.getLabelSceneCodeId().intValue());
        if (sceneCodeDO == null) {
            throw exception(SCENE_CODE_NOT_AVAILABLE);
        }
        if (req.getNum() <= 0) {
            throw exception(OPERATION_LABEL_NUM_CORRECT);
        }
        List<OperationLabelCodeDO> saveList = new ArrayList<>();
        List<BatchPrintLabelRespVO> result = new ArrayList<>();
        String lastedCode = "";
        for (int i = 0; i < req.getNum(); i++) {
            OperationLabelCodeDO operationLabelCodeDO = new OperationLabelCodeDO();
            operationLabelCodeDO.setCode(sceneCodeService.increment(sceneCodeDO.getKeyCode()));
            operationLabelCodeDO.setDeviceId(req.getId());
            operationLabelCodeDO.setStatus(0);
            lastedCode = operationLabelCodeDO.getCode();
            saveList.add(operationLabelCodeDO);
            BatchPrintLabelRespVO vo = new BatchPrintLabelRespVO();
            vo.setLabelCode(operationLabelCodeDO.getCode());
            vo.setName(operationLabelDO.getName());
            result.add(vo);
        }
        operationLabelCodeMapper.insertBatch(saveList);
        operationLabelDO.setLabelCurrentCode(lastedCode);
        operationDeviceTypeMapper.updateById(operationLabelDO);
        return result;
    }

    @Override
    @Transactional
    public DeviceTypeImportRespVO importDeviceTypeList(List<DeviceTypeImportExcelVO> list, Boolean updateSupport) {
        //判空
        if (CollUtil.isEmpty(list)) {
            throw exception(IMPORT_LIST_IS_EMPTY);
        }
        DeviceTypeImportRespVO respVO = DeviceTypeImportRespVO.builder().createList(new ArrayList<>())
                .updateList(new ArrayList<>()).failureList(new LinkedHashMap<>()).build();
        //处理数据
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(item -> {
                OperationDeviceTypeDO deviceType = operationDeviceTypeMapper.selectOne(new QueryWrapper<OperationDeviceTypeDO>().lambda().eq(OperationDeviceTypeDO::getName, item.getName()));
                OperationDeviceTypeDO operationDeviceTypeDO = BeanUtils.toBean(item, OperationDeviceTypeDO.class);
                SceneCodeDO typeSceneCode = sceneCodeMapper.selectOne(new QueryWrapper<SceneCodeDO>().lambda().eq(SceneCodeDO::getDescription, item.getName()));
                SceneCodeDO labelSceneCode = sceneCodeMapper.selectOne(new QueryWrapper<SceneCodeDO>().lambda().eq(SceneCodeDO::getDescription, item.getName() + "-标签"));
                if (deviceType == null) {

                    if (typeSceneCode != null && labelSceneCode != null) {
                        operationDeviceTypeMapper.insert(operationDeviceTypeDO.setSceneCodeId(typeSceneCode.getId().longValue())
                                .setSceneName(typeSceneCode.getDescription())
                                .setLabelSceneCodeId(labelSceneCode.getId().longValue())
                                .setLabelSceneName(labelSceneCode.getDescription())
                                .setStatus(0));
                        respVO.getCreateList().add(item.getName());
                        return;
                    } else {
                        respVO.getFailureList().put(item.getName(), "无法匹配类别编码或标签编码");
                        return;
                    }
                }
                if (!updateSupport) {
                    if (!updateSupport) {   //是否支持更新
                        respVO.getFailureList().put(item.getName(), "设备类型已存在");
                        return;
                    }
                    operationDeviceTypeMapper.updateById(operationDeviceTypeDO.setId(deviceType.getId())
                            .setSceneCodeId(typeSceneCode.getId().longValue())
                            .setSceneName(typeSceneCode.getDescription())
                            .setLabelSceneCodeId(labelSceneCode.getId().longValue())
                            .setLabelSceneName(labelSceneCode.getDescription())
                            .setStatus(0));
                    respVO.getUpdateList().add(item.getName());
                    return;
                }
            });

        }

        return respVO;
    }

}