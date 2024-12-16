package com.hk.jigai.module.system.service.operation;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.dal.mysql.user.AdminUserMapper;
import io.netty.channel.AddressedEnvelope;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationAddressDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.operation.OperationAddressMapper;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 运维地点 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class OperationAddressServiceImpl implements OperationAddressService {

    @Resource
    private OperationAddressMapper operationAddressMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Long createOperationAddress(OperationAddressSaveReqVO createReqVO) {
        // 插入
        OperationAddressDO operationAddress = BeanUtils.toBean(createReqVO, OperationAddressDO.class);
        operationAddressMapper.insert(operationAddress);
        // 返回
        return operationAddress.getId();
    }

    @Override
    public void updateOperationAddress(OperationAddressSaveReqVO updateReqVO) {
        // 校验存在
        validateOperationAddressExists(updateReqVO.getId());
        //元数据
        OperationAddressDO operationAddressDO = operationAddressMapper.selectById(updateReqVO.getId());
        //状态发生改变，且新状态为禁用时
        if (operationAddressDO.getStatus() != updateReqVO.getStatus() && updateReqVO.getStatus() == 1) {
            List<OperationAddressDO> operationAddressDOS = operationAddressMapper.selectList(new QueryWrapper<OperationAddressDO>().lambda()
                    .eq(OperationAddressDO::getParentAddressId, operationAddressDO.getId()));
            //找到所有子级
            if (CollectionUtil.isNotEmpty(operationAddressDOS)) {
                //设置子级状态与父级一致，禁用
                operationAddressDOS.forEach(p -> p.setStatus(updateReqVO.getStatus())); //=1
                //批量更新
                operationAddressMapper.updateBatch(operationAddressDOS);
            }
        }


        // 更新
        OperationAddressDO updateObj = BeanUtils.toBean(updateReqVO, OperationAddressDO.class);
        operationAddressMapper.updateById(updateObj);
    }

    @Override
    @Transactional
    public void deleteOperationAddress(Long id) {
        // 校验存在
        validateOperationAddressExists(id);

        List<OperationAddressDO> operationAddressDOS = operationAddressMapper.selectList(new QueryWrapper<OperationAddressDO>().lambda().eq(OperationAddressDO::getParentAddressId, id));
        if (CollectionUtil.isNotEmpty(operationAddressDOS)) {
            //如果存在启用的子节点
            List<OperationAddressDO> collect = operationAddressDOS.stream().filter(p -> p.getStatus() == 0).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(collect)) {
                throw exception(OPERATION_SUB_ADDRESS_EXISTS);
            } else {
                operationAddressMapper.deleteBatchIds(operationAddressDOS);
            }
        }
        // 删除
        operationAddressMapper.deleteById(id);

    }

    private void validateOperationAddressExists(Long id) {
        if (operationAddressMapper.selectById(id) == null) {
            throw exception(OPERATION_ADDRESS_NOT_EXISTS);
        }
    }

    @Override
    public OperationAddressDO getOperationAddress(Long id) {
        return operationAddressMapper.selectById(id);
    }

    @Override
    public PageResult<OperationAddressDO> getOperationAddressPage(OperationAddressPageReqVO pageReqVO) {
        return operationAddressMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OperationAddressDO> getAll(OperationAddressRespVO reqVO) {
        reqVO.setStatus(0);
        return operationAddressMapper.selectList(reqVO);
    }

    @Override
    public List<OperationAddressDO> getAllAddress() {
        return operationAddressMapper.selectList();
    }

    @Override
    public AddressImportRespVO importAddressList(List<AddressImportExcelVO> list, Boolean updateSupport) {
        //判空
        if (CollUtil.isEmpty(list)) {
            throw exception(IMPORT_LIST_IS_EMPTY);
        }
        AddressImportRespVO respVO = AddressImportRespVO.builder().createList(new ArrayList<>())
                .updateList(new ArrayList<>()).failureList(new LinkedHashMap<>()).build();

        //处理数据
        if (CollectionUtil.isNotEmpty(list)) {
            //区分父级节点和子级节点
            List<AddressImportExcelVO> parentAddress = list.stream().filter(p -> "0".equals(p.getParentCode())).collect(Collectors.toList());
            List<AddressImportExcelVO> childAddress = list.stream().filter(p -> !"0".equals(p.getParentCode())).collect(Collectors.toList());
            //处理父级地点
            parentAddress.forEach(item -> {
                OperationAddressDO operationAddressDO = BeanUtils.toBean(item, OperationAddressDO.class);
                operationAddressDO.setParentAddressId(0L);
                operationAddressDO.setStatus(0);
                if (StringUtil.isNotBlank(item.getSoftManager())) {
                    AdminUserDO adminUserDO = adminUserMapper.selectOne(new QueryWrapper<AdminUserDO>().lambda().eq(AdminUserDO::getUsername, item.getSoftManager()));
                    if (adminUserDO != null) {
                        operationAddressDO.setSoftUserId(adminUserDO.getId());
                        operationAddressDO.setSoftUserNickName(adminUserDO.getNickname());
                    }
                }

                if (StringUtil.isNotBlank(item.getHardManager())) {
                    AdminUserDO adminUserDO = adminUserMapper.selectOne(new QueryWrapper<AdminUserDO>().lambda().eq(AdminUserDO::getUsername, item.getHardManager()));
                    if (adminUserDO != null) {
                        operationAddressDO.setHardwareUserId(adminUserDO.getId());
                        operationAddressDO.setHardwareUserNickName(adminUserDO.getNickname());
                    }
                }
                OperationAddressDO checkAddress = operationAddressMapper.selectOne(new QueryWrapper<OperationAddressDO>().lambda().eq(OperationAddressDO::getAddressName, item.getAddressName()));
                if (checkAddress == null) {
                    operationAddressMapper.insert(operationAddressDO);
                    respVO.getCreateList().add(item.getAddressName());
                    return;
                }
                if (!updateSupport) {
                    respVO.getFailureList().put(item.getAddressName(), "地点已存在");
                    return;
                }
                operationAddressMapper.updateById(operationAddressDO.setId(checkAddress.getId()));
                respVO.getUpdateList().add(item.getAddressName());
                return;
            });
            //处理子级地点
            childAddress.forEach(item -> {
                OperationAddressDO operationAddressDO = BeanUtils.toBean(item, OperationAddressDO.class);
                if (StringUtil.isNotBlank(item.getParentCode())) {
                    List<AddressImportExcelVO> collect = list.stream().filter(p -> item.getParentCode().equals(p.getAddressCode())).collect(Collectors.toList());
                    String addressName = collect.get(0).getAddressName();
                    OperationAddressDO pAddress = operationAddressMapper.selectOne(new QueryWrapper<OperationAddressDO>().lambda().eq(OperationAddressDO::getAddressName, addressName));
                    if (pAddress != null) {
                        operationAddressDO.setParentAddressId(pAddress.getId());
                    } else {
                        pAddress.setParentAddressId(0L);
                    }
                }
                operationAddressDO.setStatus(0);
                if (StringUtil.isNotBlank(item.getSoftManager())) {
                    AdminUserDO adminUserDO = adminUserMapper.selectOne(new QueryWrapper<AdminUserDO>().lambda().eq(AdminUserDO::getUsername, item.getSoftManager()));
                    if (adminUserDO != null) {
                        operationAddressDO.setSoftUserId(adminUserDO.getId());
                        operationAddressDO.setSoftUserNickName(adminUserDO.getNickname());
                    }
                }

                if (StringUtil.isNotBlank(item.getHardManager())) {
                    AdminUserDO adminUserDO = adminUserMapper.selectOne(new QueryWrapper<AdminUserDO>().lambda().eq(AdminUserDO::getUsername, item.getHardManager()));
                    if (adminUserDO != null) {
                        operationAddressDO.setHardwareUserId(adminUserDO.getId());
                        operationAddressDO.setHardwareUserNickName(adminUserDO.getNickname());
                    }
                }
                OperationAddressDO checkAddress = operationAddressMapper.selectOne(new QueryWrapper<OperationAddressDO>().lambda()
                        .eq(OperationAddressDO::getAddressName, item.getAddressName())
                        .eq(OperationAddressDO::getParentAddressId, operationAddressDO.getParentAddressId()));
                if (checkAddress == null) {
                    operationAddressMapper.insert(operationAddressDO);
                    respVO.getCreateList().add(item.getAddressName());
                    return;
                }
                if (!updateSupport) {
                    respVO.getFailureList().put(item.getAddressName(), "地点已存在");
                    return;
                }
                operationAddressMapper.updateById(operationAddressDO.setId(checkAddress.getId()));
                respVO.getUpdateList().add(item.getAddressName());
                return;

            });


        }
        return respVO;
    }

    @Override
    public OperationAddressDO getAddressByAddress(String address) {
        return operationAddressMapper.selectOne(new QueryWrapper<OperationAddressDO>().lambda().eq(OperationAddressDO::getAddressName, address));
    }

}