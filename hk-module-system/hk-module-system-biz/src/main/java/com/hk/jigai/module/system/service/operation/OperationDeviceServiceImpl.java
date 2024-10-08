package com.hk.jigai.module.system.service.operation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceAccessoryDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDevicePictureDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceTypeDO;
import com.hk.jigai.module.system.dal.dataobject.scenecode.SceneCodeDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.dal.mysql.operation.OperationDeviceAccessoryMapper;
import com.hk.jigai.module.system.dal.mysql.operation.OperationDevicePictureMapper;
import com.hk.jigai.module.system.dal.mysql.operation.OperationDeviceTypeMapper;
import com.hk.jigai.module.system.service.scenecode.SceneCodeService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.operation.OperationDeviceMapper;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 运维设备 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class OperationDeviceServiceImpl implements OperationDeviceService {

    @Resource
    private OperationDeviceMapper operationDeviceMapper;

    @Resource
    private SceneCodeService sceneCodeService;

    @Resource
    private OperationDeviceTypeMapper operationDeviceTypeMapper;

    @Resource
    private OperationDeviceAccessoryMapper operationDeviceAccessoryMapper;

    @Resource
    private OperationDevicePictureMapper operationDevicePictureMapper;

    @Override
    @Transactional
    public Long createOperationDevice(OperationDeviceSaveReqVO createReqVO) {
        createReqVO.setStatus(1);
        //1.先查询设备类型，设备编码
        OperationDeviceTypeDO operationLabelDO = operationDeviceTypeMapper.selectById(createReqVO.getDeviceType());
        if (operationLabelDO == null) {
            throw exception(OPERATION_DEVICE_TYPE_NOT_EXISTS);
        }
        SceneCodeDO sceneCodeDO = sceneCodeService.getSceneCode(operationLabelDO.getSceneCodeId().intValue());
        if(sceneCodeDO==null){
            throw exception(SCENE_CODE_NOT_AVAILABLE);
        }
        createReqVO.setCode(sceneCodeService.increment(sceneCodeDO.getKeyCode()));
        operationLabelDO.setCurrentCode(createReqVO.getCode());
        //2.标签编码
        if(StringUtils.isEmpty(createReqVO.getLabelCode())){
            SceneCodeDO labelSceneCodeDO = sceneCodeService.getSceneCode(operationLabelDO.getLabelSceneCodeId().intValue());
            if(labelSceneCodeDO==null){
                throw exception(SCENE_CODE_NOT_AVAILABLE);
            }
            createReqVO.setLabelCode(sceneCodeService.increment(labelSceneCodeDO.getKeyCode()));
            operationLabelDO.setLabelCurrentCode(createReqVO.getLabelCode());
        }
        //3.更新设备类型表
        operationDeviceTypeMapper.updateById(operationLabelDO);
        //4.设备表插入
        OperationDeviceDO operationDevice = BeanUtils.toBean(createReqVO, OperationDeviceDO.class);
        operationDeviceMapper.insert(operationDevice);
        //5.图片
        List<OperationDevicePictureDO> operationDevicePictureList = BeanUtils.toBean(createReqVO.getPictureList(), OperationDevicePictureDO.class);
        operationDevicePictureList.forEach((a)->a.setDeviceId(operationDevice.getId()));
        operationDevicePictureMapper.insertBatch(operationDevicePictureList);
        //6.配置
        List<OperationDeviceAccessoryDO> operationDeviceAccessoryList = BeanUtils.toBean(createReqVO.getAccessoryList(), OperationDeviceAccessoryDO.class);
        operationDeviceAccessoryList.forEach((a)->a.setDeviceId(operationDevice.getId()));
        operationDeviceAccessoryMapper.insertBatch(operationDeviceAccessoryList);
        //7.返回
        return operationDevice.getId();
    }

    @Override
    @Transactional
    public void updateOperationDevice(OperationDeviceSaveReqVO updateReqVO) {
        // 校验存在
        validateOperationDeviceExists(updateReqVO.getId());
        // 更新
        OperationDeviceDO updateObj = BeanUtils.toBean(updateReqVO, OperationDeviceDO.class);
        operationDeviceMapper.updateById(updateObj);
        //5.图片
        operationDevicePictureMapper.delete(new QueryWrapper<OperationDevicePictureDO>().lambda().in(OperationDevicePictureDO::getDeviceId,updateReqVO.getId()));
        List<OperationDevicePictureDO> operationDevicePictureList = BeanUtils.toBean(updateReqVO.getPictureList(), OperationDevicePictureDO.class);
        operationDevicePictureList.forEach((a)->a.setDeviceId(updateReqVO.getId()));
        operationDevicePictureMapper.insertBatch(operationDevicePictureList);
        //6.配置
        operationDeviceAccessoryMapper.delete(new QueryWrapper<OperationDeviceAccessoryDO>().lambda().in(OperationDeviceAccessoryDO::getDeviceId,updateReqVO.getId()));
        List<OperationDeviceAccessoryDO> operationDeviceAccessoryList = BeanUtils.toBean(updateReqVO.getAccessoryList(), OperationDeviceAccessoryDO.class);
        operationDeviceAccessoryList.forEach((a)->a.setDeviceId(updateReqVO.getId()));
        operationDeviceAccessoryMapper.insertBatch(operationDeviceAccessoryList);
        //todo 插入变更记录表

    }

    @Override
    @Transactional
    public void deleteOperationDevice(Long id) {
        // 校验存在
        validateOperationDeviceExists(id);
        // 删除
        operationDeviceMapper.deleteById(id);
        operationDevicePictureMapper.delete(new QueryWrapper<OperationDevicePictureDO>().lambda().in(OperationDevicePictureDO::getDeviceId,id));
        operationDeviceAccessoryMapper.delete(new QueryWrapper<OperationDeviceAccessoryDO>().lambda().in(OperationDeviceAccessoryDO::getDeviceId,id));
    }

    private void validateOperationDeviceExists(Long id) {
        if (operationDeviceMapper.selectById(id) == null) {
            throw exception(OPERATION_DEVICE_NOT_EXISTS);
        }
    }

    @Override
    public OperationDeviceRespVO getOperationDevice(Long id) {
        OperationDeviceDO operationDeviceDO = operationDeviceMapper.selectById(id);
        OperationDeviceRespVO resp = BeanUtils.toBean(operationDeviceDO, OperationDeviceRespVO.class);
        List<OperationDevicePictureSaveReqVO> pictureList = BeanUtils.toBean(operationDeviceAccessoryMapper.selectList((new QueryWrapper<OperationDeviceAccessoryDO>().lambda()
                .eq(OperationDeviceAccessoryDO::getDeviceId,operationDeviceDO.getId()))), OperationDevicePictureSaveReqVO.class);
        resp.setPictureList(pictureList);
        List<OperationDeviceAccessorySaveReqVO> accessoryList = BeanUtils.toBean(operationDevicePictureMapper.selectList((new QueryWrapper<OperationDevicePictureDO>().lambda()
                .eq(OperationDevicePictureDO::getDeviceId,operationDeviceDO.getId()))),OperationDeviceAccessorySaveReqVO.class);
        resp.setAccessoryList(accessoryList);
        return resp;
    }

    @Override
    public PageResult<OperationDeviceDO> getOperationDevicePage(OperationDevicePageReqVO pageReqVO) {
        return operationDeviceMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional
    public void register(OperationDeviceRegisterReqVO registerReqVO) {
        //查询设备信息
        OperationDeviceDO operationDeviceDO = operationDeviceMapper.selectById(registerReqVO.getId());
        if (operationDeviceDO == null) {
            throw exception(OPERATION_DEVICE_NOT_EXISTS);
        }
        //状态更新
        operationDeviceDO.setStatus(0);
        operationDeviceDO.setDeptId(registerReqVO.getDeptId());
        operationDeviceDO.setDeptName(registerReqVO.getDeptName());
        operationDeviceDO.setUserId(registerReqVO.getUserId());
        operationDeviceDO.setAddressId(registerReqVO.getAddressId());
        operationDeviceDO.setLocation(registerReqVO.getLocation());
        operationDeviceDO.setIp1(registerReqVO.getIp1());
        operationDeviceDO.setIp2(registerReqVO.getIp2());
        operationDeviceDO.setRegisterUserId(registerReqVO.getRegisterUserId());
        operationDeviceDO.setRegisterDate(registerReqVO.getRegisterDate());
        //更新
        operationDeviceMapper.updateById(operationDeviceDO);
        //picture表
        List<OperationDevicePictureDO> operationDevicePictureList = BeanUtils.toBean(registerReqVO.getPictureList(), OperationDevicePictureDO.class);
        operationDevicePictureList.forEach((a)->a.setDeviceId(operationDeviceDO.getId()));
        operationDevicePictureMapper.insertBatch(operationDevicePictureList);
    }

    @Override
    @Transactional
    public void scrap(OperationDeviceScrapReqVO scrapReqVO) {
        //查询设备信息
        OperationDeviceDO operationDeviceDO = operationDeviceMapper.selectById(scrapReqVO.getId());
        if (operationDeviceDO == null) {
            throw exception(OPERATION_DEVICE_NOT_EXISTS);
        }
        //状态更新
        operationDeviceDO.setStatus(2);
        operationDeviceDO.setScrapDate(scrapReqVO.getScrapDate());
        operationDeviceDO.setScrapType(scrapReqVO.getScrapType());
        operationDeviceDO.setScrapUserId(scrapReqVO.getScrapUserId());
        operationDeviceDO.setScrapDealTyep(scrapReqVO.getScrapDealTyep());
        operationDeviceDO.setScrapRemark(scrapReqVO.getScrapRemark());

        //更新
        operationDeviceMapper.updateById(operationDeviceDO);
        //picture表
        List<OperationDevicePictureDO> operationDevicePictureList = BeanUtils.toBean(scrapReqVO.getPictureList(), OperationDevicePictureDO.class);
        operationDevicePictureList.forEach((a)->a.setDeviceId(operationDeviceDO.getId()));
        operationDevicePictureMapper.insertBatch(operationDevicePictureList);
    }

}