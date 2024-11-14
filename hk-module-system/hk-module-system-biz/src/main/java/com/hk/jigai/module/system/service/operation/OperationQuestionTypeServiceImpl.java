package com.hk.jigai.module.system.service.operation;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceTypeDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationLabelCodeDO;
import com.hk.jigai.module.system.dal.dataobject.scenecode.SceneCodeDO;
import com.hk.jigai.module.system.dal.mysql.operation.OperationDeviceTypeMapper;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationQuestionTypeDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.operation.OperationQuestionTypeMapper;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 运维问题类型 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class OperationQuestionTypeServiceImpl implements OperationQuestionTypeService {

    @Resource
    private OperationQuestionTypeMapper operationQuestionTypeMapper;

    @Resource
    private OperationDeviceTypeMapper operationDeviceTypeMapper;

    @Override
    public Long createOperationQuestionType(OperationQuestionTypeSaveReqVO createReqVO) {
        // 插入
        OperationQuestionTypeDO operationQuestionType = BeanUtils.toBean(createReqVO, OperationQuestionTypeDO.class);
        OperationDeviceTypeDO operationDeviceTypeDO = operationDeviceTypeMapper.selectById(operationQuestionType.getDeviceTypeId());
        operationQuestionType.setDeviceTypeName(operationDeviceTypeDO.getName());
        operationQuestionTypeMapper.insert(operationQuestionType);
        // 返回
        return operationQuestionType.getId();
    }

    @Override
    @Transactional
    public void updateOperationQuestionType(OperationQuestionTypeSaveReqVO updateReqVO) {
        // 校验存在
        validateOperationQuestionTypeExists(updateReqVO.getId());

        // 更新
        OperationQuestionTypeDO updateObj = BeanUtils.toBean(updateReqVO, OperationQuestionTypeDO.class);
        OperationDeviceTypeDO operationDeviceTypeDO = operationDeviceTypeMapper.selectById(updateObj.getDeviceTypeId());
        updateObj.setDeviceTypeName(operationDeviceTypeDO.getName());
        operationQuestionTypeMapper.updateById(updateObj);
    }

    @Override
    @Transactional
    public void deleteOperationQuestionType(Long id) {
        // 校验存在
        validateOperationQuestionTypeExists(id);
        // 删除
        operationQuestionTypeMapper.deleteById(id);
        // 删除子节点
        List<OperationQuestionTypeDO> operationQuestionTypeDOS = operationQuestionTypeMapper.selectList(new QueryWrapper<OperationQuestionTypeDO>().lambda().eq(OperationQuestionTypeDO::getParentId, id));
        if (CollectionUtil.isNotEmpty(operationQuestionTypeDOS)) {
            operationQuestionTypeMapper.deleteBatchIds(operationQuestionTypeDOS);
        }
    }

    private void validateOperationQuestionTypeExists(Long id) {
        if (operationQuestionTypeMapper.selectById(id) == null) {
            throw exception(OPERATION_QUESTION_TYPE_NOT_EXISTS);
        }
    }

    @Override
    public OperationQuestionTypeDO getOperationQuestionType(Long id) {
        return operationQuestionTypeMapper.selectById(id);
    }

    @Override
    public List<OperationQuestionTypeDO> queryAll(OperationQuestionTypeReqVO req) {
        return operationQuestionTypeMapper.selectList(req);
    }

    @Override
    @Transactional
    public QuestionTypeImportRespVO importQuestionTypeList(List<QuestionTypeImportExcelVO> list, Boolean updateSupport) {

        //判空
        if (CollUtil.isEmpty(list)) {
            throw exception(IMPORT_LIST_IS_EMPTY);
        }
        QuestionTypeImportRespVO respVO = QuestionTypeImportRespVO.builder().createList(new ArrayList<>())
                .updateList(new ArrayList<>()).failureList(new LinkedHashMap<>()).build();
        //处理数据
        if (CollectionUtil.isNotEmpty(list)) {
            List<QuestionTypeImportExcelVO> mainQuestion = list.stream().filter(p -> p.getParentCode() == null).collect(Collectors.toList());
            List<QuestionTypeImportExcelVO> subQuestion = list.stream().filter(p -> p.getParentCode() != null).collect(Collectors.toList());


            //所有设备类型
            List<OperationDeviceTypeDO> deviceTypeList = operationDeviceTypeMapper.selectList();
            if (CollectionUtil.isEmpty(deviceTypeList)) {
                respVO.getFailureList().put("系统异常", "设备类型缺失");
                return respVO;
            }
            //处理主问题类型
            if (CollectionUtil.isNotEmpty(mainQuestion)) {

                mainQuestion.forEach(item -> {
                    if (StringUtil.isBlank(item.getDeviceType())) {
                        respVO.getFailureList().put(item.getQuestionType(), "设备类型缺失");
                    } else {
                        OperationQuestionTypeDO operationQuestionTypeDO = operationQuestionTypeMapper.selectOne(new QueryWrapper<OperationQuestionTypeDO>().lambda().eq(OperationQuestionTypeDO::getName, item.getName()));

                        OperationQuestionTypeDO mainQuestionDO = BeanUtils.toBean(item, OperationQuestionTypeDO.class);
                        Optional<OperationDeviceTypeDO> deviceType = deviceTypeList.stream().filter(p -> item.getDeviceType().equals(p.getName())).findFirst();
                        if (deviceType == null) {
                            respVO.getFailureList().put(item.getQuestionType(), "没有匹配的设备类型");
                            return;
                        }
                        deviceType.ifPresent(p -> {
                            mainQuestionDO.setParentId(0L);
                            mainQuestionDO.setDeviceTypeId(p.getId());
                            mainQuestionDO.setDeviceTypeName(p.getName());
                        });
                        if (operationQuestionTypeDO == null) {
                            operationQuestionTypeMapper.insert(mainQuestionDO);
                            respVO.getCreateList().add(mainQuestionDO.getName());
                            return;
                        }
                        if (!updateSupport) {
                            respVO.getFailureList().put(item.getName(), "问题类型已存在");
                            return;
                        }
                        mainQuestionDO.setId(operationQuestionTypeDO.getId());
                        operationQuestionTypeMapper.updateById(mainQuestionDO);
                        respVO.getUpdateList().add(mainQuestionDO.getName());
                        return;
                    }
                });

            }

            //处理子问题类型
            if (CollectionUtil.isNotEmpty(subQuestion)) {
                List<OperationQuestionTypeDO> mainList = operationQuestionTypeMapper.selectList(new QueryWrapper<OperationQuestionTypeDO>()
                        .lambda().eq(OperationQuestionTypeDO::getParentId, 0));
                subQuestion.forEach(item -> {
                    if (StringUtil.isBlank(item.getDeviceType())) {
                        respVO.getFailureList().put(item.getQuestionType(), "设备类型缺失");
                    } else {
                        List<OperationQuestionTypeDO> operationQuestionTypeDOS = operationQuestionTypeMapper.selectList(new QueryWrapper<OperationQuestionTypeDO>().lambda().eq(OperationQuestionTypeDO::getName, item.getParentCode()));
                        List<Long> parentIds = new ArrayList<>();
                        if (CollectionUtil.isNotEmpty(operationQuestionTypeDOS)) {
                            parentIds = operationQuestionTypeDOS.stream().map(p -> p.getId()).collect(Collectors.toList());
                        }
                        OperationQuestionTypeDO operationQuestionTypeDO = operationQuestionTypeMapper.selectOne(new QueryWrapper<OperationQuestionTypeDO>().lambda()
                                .eq(OperationQuestionTypeDO::getName, item.getName())
                                .in(OperationQuestionTypeDO::getParentId, parentIds));

                        OperationQuestionTypeDO subQuestionDO = BeanUtils.toBean(item, OperationQuestionTypeDO.class);
                        Optional<OperationDeviceTypeDO> deviceType = deviceTypeList.stream().filter(p -> item.getDeviceType().equals(p.getName())).findFirst();
                        if (deviceType == null) {
                            respVO.getFailureList().put(item.getQuestionType(), "没有匹配的设备类型");
                            return;
                        }
                        if ("软件".equals(item.getQuestionType())) {
                            subQuestionDO.setType("0");
                        } else if ("硬件".equals(item.getQuestionType())) {
                            subQuestionDO.setType("1");
                        } else if ("其他".equals(item.getQuestionType())) {
                            subQuestionDO.setType("2");
                        }
                        deviceType.ifPresent(p -> {
                            subQuestionDO.setDeviceTypeId(p.getId());
                            subQuestionDO.setDeviceTypeName(p.getName());
                        });
                        if (CollectionUtil.isNotEmpty(mainList)) {
                            Optional<OperationQuestionTypeDO> parentQuestion = mainList.stream().filter(p -> item.getParentCode().equals(p.getName())).findFirst();

                            if (parentQuestion.get() == null) {
                                respVO.getFailureList().put(item.getQuestionType(), "没有匹配的父级问题类型");
                                return;
                            }
                            parentQuestion.ifPresent(p -> {
                                subQuestionDO.setParentId(p.getId());
                            });
                        }
                        if (operationQuestionTypeDO == null) {
                            operationQuestionTypeMapper.insert(subQuestionDO);
                            respVO.getCreateList().add(subQuestionDO.getName());
                            return;
                        }
                        if (!updateSupport) {
                            respVO.getFailureList().put(item.getName(), "问题类型已存在");
                            return;
                        }
                        subQuestionDO.setId(operationQuestionTypeDO.getId());
                        operationQuestionTypeMapper.updateById(subQuestionDO);
                        respVO.getUpdateList().add(subQuestionDO.getName());
                        return;
                    }
                });
            }

        }
        return respVO;
    }
}