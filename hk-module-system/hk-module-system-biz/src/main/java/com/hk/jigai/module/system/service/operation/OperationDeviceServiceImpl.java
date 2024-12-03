package com.hk.jigai.module.system.service.operation;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.Slave;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.util.date.LocalDateTimeUtils;
import com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils;
import com.hk.jigai.module.system.controller.admin.operationdevicehistory.vo.OperationDeviceHistoryPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.operation.*;
import com.hk.jigai.module.system.dal.dataobject.operationdeviceaccessoryhistory.OperationDeviceAccessoryHistoryDO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicehistory.OperationDeviceHistoryDO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicepicturehistory.OperationDevicePictureHistoryDO;
import com.hk.jigai.module.system.dal.dataobject.scenecode.SceneCodeDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.dal.mysql.operation.*;
import com.hk.jigai.module.system.dal.mysql.operationdeviceaccessoryhistory.OperationDeviceAccessoryHistoryMapper;
import com.hk.jigai.module.system.dal.mysql.operationdevicehistory.OperationDeviceHistoryMapper;
import com.hk.jigai.module.system.dal.mysql.operationdevicepicturehistory.OperationDevicePictureHistoryMapper;
import com.hk.jigai.module.system.dal.mysql.user.AdminUserMapper;
import com.hk.jigai.module.system.service.scenecode.SceneCodeService;
import com.hk.jigai.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;

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

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private OperationAddressMapper operationAddressMapper;

    @Resource
    private OperationLabelCodeMapper operationLabelCodeMapper;

    @Resource
    private OperationDeviceHistoryMapper operationDeviceHistoryMapper;

    @Resource
    private OperationDevicePictureHistoryMapper operationDevicePictureHistoryMapper;

    @Resource
    private OperationDeviceAccessoryHistoryMapper operationDeviceAccessoryHistoryMapper;

    @Resource
    private OldOperationDeviceMapper oldOperationDeviceMapper;

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
        if (sceneCodeDO == null) {
            throw exception(SCENE_CODE_NOT_AVAILABLE);
        }
        createReqVO.setCode(sceneCodeService.increment(sceneCodeDO.getKeyCode()));
        operationLabelDO.setCurrentCode(createReqVO.getCode());

        //2.标签编码
        if (StringUtils.isEmpty(createReqVO.getLabelCode())) {
            SceneCodeDO labelSceneCodeDO = sceneCodeService.getSceneCode(operationLabelDO.getLabelSceneCodeId().intValue());
            if (labelSceneCodeDO == null) {
                throw exception(SCENE_CODE_NOT_AVAILABLE);
            }
            createReqVO.setLabelCode(sceneCodeService.increment(labelSceneCodeDO.getKeyCode()));
            operationLabelDO.setLabelCurrentCode(createReqVO.getLabelCode());
        } else {
            //绑定了设备，更新设备标签状态为已使用
            OperationLabelCodeDO operationLabelCodeDO = operationLabelCodeMapper.selectOne(new QueryWrapper<OperationLabelCodeDO>().lambda().eq(OperationLabelCodeDO::getCode, createReqVO.getLabelCode()));
            operationLabelCodeDO.setStatus(1);
            operationLabelCodeMapper.updateById(operationLabelCodeDO);
        }
        //3.更新设备类型表
        operationDeviceTypeMapper.updateById(operationLabelDO);
        //4.设备表插入
        OperationDeviceDO operationDevice = BeanUtils.toBean(createReqVO, OperationDeviceDO.class);
//        OperationAddressDO operationAddressDO = operationAddressMapper.selectById(operationDevice.getAddressId());
//        operationDevice.setAddress(operationAddressDO == null ? "" : operationAddressDO.getAddressName());
        operationDeviceMapper.insert(operationDevice);
        //5.图片
        List<OperationDevicePictureDO> operationDevicePictureList = BeanUtils.toBean(createReqVO.getPictureList(), OperationDevicePictureDO.class);
        operationDevicePictureList.forEach((a) -> a.setDeviceId(operationDevice.getId()));
        operationDevicePictureMapper.insertBatch(operationDevicePictureList);
        //6.配置
        List<OperationDeviceAccessoryDO> operationDeviceAccessoryList = BeanUtils.toBean(createReqVO.getAccessoryList(), OperationDeviceAccessoryDO.class);
        operationDeviceAccessoryList.forEach((a) -> a.setDeviceId(operationDevice.getId()));
        operationDeviceAccessoryMapper.insertBatch(operationDeviceAccessoryList);
        //7.返回
        return operationDevice.getId();
    }

    @Override
    @Transactional
    public void updateOperationDevice(OperationDeviceSaveReqVO updateReqVO) {
        // 校验存在
        validateOperationDeviceExists(updateReqVO.getId());
        //获取原数据
        Long deviceId = updateReqVO.getId();
        //创建快照
        createDeviceSnapshot(deviceId);

        OperationDeviceDO last = operationDeviceMapper.selectById(updateReqVO.getId());

        // 更新
        OperationDeviceDO updateObj = BeanUtils.toBean(updateReqVO, OperationDeviceDO.class);
        updateObj.setDeptId(last.getDeptId());
        updateObj.setDeptName(last.getDeptName());
        updateObj.setUserId(last.getUserId());
        updateObj.setUserNickName(last.getUserNickName());
        updateObj.setAddressId(last.getAddressId());
        updateObj.setAddress(last.getAddress());
        operationDeviceMapper.updateById(updateObj);
        //5.图片
        operationDevicePictureMapper.delete(new QueryWrapper<OperationDevicePictureDO>().lambda()
                .eq(OperationDevicePictureDO::getDeviceId, updateReqVO.getId())
                .eq(OperationDevicePictureDO::getType, "0"));
        List<OperationDevicePictureDO> operationDevicePictureList = BeanUtils.toBean(updateReqVO.getPictureList(), OperationDevicePictureDO.class);
        operationDevicePictureList.forEach((a) -> {
            a.setDeviceId(updateReqVO.getId());
            a.setId(null);
        });

        operationDevicePictureMapper.insertBatch(operationDevicePictureList);
        //6.配置
        operationDeviceAccessoryMapper.delete(new QueryWrapper<OperationDeviceAccessoryDO>().lambda().in(OperationDeviceAccessoryDO::getDeviceId, updateReqVO.getId()));
        List<OperationDeviceAccessoryDO> operationDeviceAccessoryList = BeanUtils.toBean(updateReqVO.getAccessoryList(), OperationDeviceAccessoryDO.class);
        operationDeviceAccessoryList.forEach((a) -> {
            a.setDeviceId(updateReqVO.getId());
            a.setId(null);
        });
        operationDeviceAccessoryMapper.insertBatch(operationDeviceAccessoryList);
    }

    //创建设备快照
    @Transactional
    public void createDeviceSnapshot(Long deviceId) {
        //原设备基础信息
        OperationDeviceDO oldDevice = operationDeviceMapper.selectById(deviceId);
        //获取原图片
        List<OperationDevicePictureDO> oldDevicePictureDOS = operationDevicePictureMapper.selectList(new QueryWrapper<OperationDevicePictureDO>().lambda()
                .eq(OperationDevicePictureDO::getDeviceId, deviceId));
        //获取原分配记录
        List<OperationDeviceAccessoryDO> oldDeviceAccessoryDOS = operationDeviceAccessoryMapper.selectList(new QueryWrapper<OperationDeviceAccessoryDO>().lambda().eq(OperationDeviceAccessoryDO::getDeviceId, deviceId));

        //数据转换
        OperationDeviceHistoryDO operationDeviceHistoryDO = BeanUtils.toBean(oldDevice, OperationDeviceHistoryDO.class);
        operationDeviceHistoryDO.setId(null);
        operationDeviceHistoryDO.setCreateTime(null);
        operationDeviceHistoryDO.setCreator(null);
        operationDeviceHistoryDO.setUpdater(null);
        operationDeviceHistoryDO.setUpdateTime(null);
        operationDeviceHistoryDO.setDeviceId(oldDevice.getId());
        operationDeviceHistoryMapper.insert(operationDeviceHistoryDO);
        if (CollectionUtil.isNotEmpty(oldDevicePictureDOS)) {
            List<OperationDevicePictureHistoryDO> collect = oldDevicePictureDOS.stream().map(oldDevicePic -> {
                OperationDevicePictureHistoryDO operationDevicePictureHistoryDO = BeanUtils.toBean(oldDevicePic, OperationDevicePictureHistoryDO.class);
                operationDevicePictureHistoryDO.setId(null);
                operationDevicePictureHistoryDO.setCreateTime(null);
                operationDevicePictureHistoryDO.setCreator(null);
                operationDevicePictureHistoryDO.setUpdater(null);
                operationDevicePictureHistoryDO.setUpdateTime(null);
                operationDevicePictureHistoryDO.setHistoryId(operationDeviceHistoryDO.getId());
                return operationDevicePictureHistoryDO;
            }).collect(Collectors.toList());

            operationDevicePictureHistoryMapper.insertBatch(collect);
        }
        if (CollectionUtil.isNotEmpty(oldDeviceAccessoryDOS)) {
            List<OperationDeviceAccessoryHistoryDO> collect = oldDeviceAccessoryDOS.stream().map(oldDeviceAcc -> {
                OperationDeviceAccessoryHistoryDO operationDeviceAccessoryHistoryDO = BeanUtils.toBean(oldDeviceAcc, OperationDeviceAccessoryHistoryDO.class);
                operationDeviceAccessoryHistoryDO.setId(null);
                operationDeviceAccessoryHistoryDO.setCreateTime(null);
                operationDeviceAccessoryHistoryDO.setCreator(null);
                operationDeviceAccessoryHistoryDO.setUpdater(null);
                operationDeviceAccessoryHistoryDO.setUpdateTime(null);
                operationDeviceAccessoryHistoryDO.setHistoryId(operationDeviceHistoryDO.getId());
                return operationDeviceAccessoryHistoryDO;
            }).collect(Collectors.toList());

            operationDeviceAccessoryHistoryMapper.insertBatch(collect);
        }

    }

    @Override
    @Transactional
    public void deleteOperationDevice(Long id) {
        // 校验存在
        validateOperationDeviceExists(id);
        // 删除
        operationDeviceMapper.deleteById(id);
        operationDevicePictureMapper.delete(new QueryWrapper<OperationDevicePictureDO>().lambda().in(OperationDevicePictureDO::getDeviceId, id));
        operationDeviceAccessoryMapper.delete(new QueryWrapper<OperationDeviceAccessoryDO>().lambda().in(OperationDeviceAccessoryDO::getDeviceId, id));
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
        List<OperationDevicePictureSaveReqVO> pictureList = BeanUtils.toBean(operationDevicePictureMapper.selectList(new QueryWrapper<OperationDevicePictureDO>().lambda().eq(OperationDevicePictureDO::getDeviceId, id)), OperationDevicePictureSaveReqVO.class);
        if (CollectionUtil.isNotEmpty(pictureList)) {
            List<OperationDevicePictureSaveReqVO> devicePic = pictureList.stream().filter(p -> "0".equals(p.getType())).collect(Collectors.toList());
            List<OperationDevicePictureSaveReqVO> distributePic = pictureList.stream().filter(p -> "1".equals(p.getType())).collect(Collectors.toList());
            List<OperationDevicePictureSaveReqVO> scrapPic = pictureList.stream().filter(p -> "2".equals(p.getType())).collect(Collectors.toList());
            resp.setDevicePictureList(devicePic);
            resp.setDistributePictureList(distributePic);
            resp.setScrapPictureList(scrapPic);
        }
        if (operationDeviceDO != null) {
            List<OperationDeviceAccessoryDO> operationDeviceAccessoryDOS = operationDeviceAccessoryMapper.selectList((new QueryWrapper<OperationDeviceAccessoryDO>().lambda()
                    .eq(OperationDeviceAccessoryDO::getDeviceId, operationDeviceDO.getId())));
            List<OperationDeviceAccessorySaveReqVO> accessoryList = BeanUtils.toBean(operationDeviceAccessoryDOS, OperationDeviceAccessorySaveReqVO.class);
            resp.setAccessoryList(accessoryList);
            //编码名称
            SceneCodeDO sceneCode = sceneCodeService.getSceneCode(operationDeviceDO.getNumberName().intValue());
            if (sceneCode != null) {
                resp.setNumberNameStr(sceneCode.getDescription());
            }
        }
        return resp;
    }

    @Override
    public OperationDeviceRespVO getOperationDeviceByLabelCode(String labelCode) {
        OperationDeviceDO operationDeviceDO = operationDeviceMapper.selectOne(new QueryWrapper<OperationDeviceDO>().lambda().eq(OperationDeviceDO::getLabelCode, labelCode));
        //先从新系统获取数据
        OperationDeviceRespVO resp = new OperationDeviceRespVO();
        if (operationDeviceDO != null) {
            resp = BeanUtils.toBean(operationDeviceDO, OperationDeviceRespVO.class);
            List<OperationDevicePictureSaveReqVO> pictureList = BeanUtils.toBean(operationDevicePictureMapper.selectList(new QueryWrapper<OperationDevicePictureDO>().lambda().eq(OperationDevicePictureDO::getDeviceId, operationDeviceDO.getId())), OperationDevicePictureSaveReqVO.class);
            if (CollectionUtil.isNotEmpty(pictureList)) {
                List<OperationDevicePictureSaveReqVO> devicePic = pictureList.stream().filter(p -> "0".equals(p.getType())).collect(Collectors.toList());
                List<OperationDevicePictureSaveReqVO> distributePic = pictureList.stream().filter(p -> "1".equals(p.getType())).collect(Collectors.toList());
                List<OperationDevicePictureSaveReqVO> scrapPic = pictureList.stream().filter(p -> "2".equals(p.getType())).collect(Collectors.toList());
                resp.setDevicePictureList(devicePic);
                resp.setDistributePictureList(distributePic);
                resp.setScrapPictureList(scrapPic);
            }
            List<OperationDeviceAccessorySaveReqVO> accessoryList = BeanUtils.toBean(operationDeviceAccessoryMapper.selectList((new QueryWrapper<OperationDeviceAccessoryDO>().lambda()
                    .eq(OperationDeviceAccessoryDO::getDeviceId, operationDeviceDO.getId()))), OperationDeviceAccessorySaveReqVO.class);
            resp.setAccessoryList(accessoryList);
        }
        return resp;
    }

    @Override
    @Slave
    public OperationDeviceRespVO getOldOperationDeviceByLabelCode(String labelCode) {
        //先从新系统获取数据
        OperationDeviceRespVO resp = new OperationDeviceRespVO();
        OldOperationDeviceDO oldOperationDeviceDO = oldOperationDeviceMapper.selectOne(new QueryWrapper<OldOperationDeviceDO>().lambda().eq(OldOperationDeviceDO::getBarcode, labelCode));
        if (oldOperationDeviceDO != null) {
            resp.setCode(oldOperationDeviceDO.getCiid() + "");
            String deviceName = oldOperationDeviceMapper.selectDeviceByProductId(oldOperationDeviceDO.getProductid());
            resp.setDeviceTypeName(deviceName);
            String model = oldOperationDeviceMapper.selectModelById(oldOperationDeviceDO.getProductid());
            resp.setModel(model);
            resp.setSerialNumber(oldOperationDeviceDO.getSerialno());
            resp.setMacAddress1(oldOperationDeviceDO.getMacaddress1());
            resp.setMacAddress2(oldOperationDeviceDO.getMacaddress2());
            resp.setId(oldOperationDeviceDO.getCiid());
            resp.setCompany(oldOperationDeviceDO.getCorporationid().intValue());
            resp.setEffectLevel(oldOperationDeviceDO.getImpactid());
            resp.setAssetNumber(oldOperationDeviceDO.getAssettag());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(oldOperationDeviceDO.getProductdate());
            resp.setManufactureDate(LocalDate.parse(formattedDate));
            String productPhoto = oldOperationDeviceDO.getProductphoto();
            String[] pic = productPhoto.split("￥");
            String photoPath = "https://szh.jshkxcl.cn/hkjg-oldpic/";
            List<OperationDevicePictureSaveReqVO> pictureSaveReqVOS = new ArrayList<>();
            for (String p : pic) {
                OperationDevicePictureSaveReqVO operationDevicePictureDO = new OperationDevicePictureSaveReqVO();
                operationDevicePictureDO.setUrl(photoPath + p + ".jpg");
                operationDevicePictureDO.setType("0");
                pictureSaveReqVOS.add(operationDevicePictureDO);
                resp.setDevicePictureList(pictureSaveReqVOS);
            }
            resp.setName(oldOperationDeviceDO.getResourcename());
            resp.setLabelCode(labelCode);
            resp.setAddress(oldOperationDeviceDO.getLocationex());
            resp.setLocation(oldOperationDeviceDO.getLocation());
        }
        return resp;
    }

    @Override
    public PageResult<OperationDeviceDO> getOperationDevicePage(OperationDevicePageReqVO pageReqVO) {
        return operationDeviceMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional
    public void register(OperationDeviceRegisterReqVO registerReqVO) {

        //创建快照
        createDeviceSnapshot(registerReqVO.getId());
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
        if (registerReqVO.getAddressIdList() != null) {
            List<Long> addressIdList = registerReqVO.getAddressIdList();
            String result = addressIdList.stream()
                    .map(String::valueOf).collect(Collectors.joining(", "));
            operationDeviceDO.setAddressId(result);
        }

//        OperationAddressDO operationAddressDO = operationAddressMapper.selectById(operationDeviceDO.getAddressId());
        operationDeviceDO.setAddress(registerReqVO.getAddress());
        operationDeviceDO.setLocation(registerReqVO.getLocation());
        operationDeviceDO.setIp1(registerReqVO.getIp1());
        operationDeviceDO.setIp2(registerReqVO.getIp2());
        operationDeviceDO.setRegisterUserId(SecurityFrameworkUtils.getLoginUserId());
        operationDeviceDO.setRegisterUserName(SecurityFrameworkUtils.getLoginUserNickname());
        operationDeviceDO.setRegisterDate(LocalDateTime.now());
        AdminUserDO user = adminUserService.getUser(registerReqVO.getUserId());
        if (user != null) {
            operationDeviceDO.setUserNickName(user.getNickname());
        } else {
            operationDeviceDO.setStatus(1);
        }
        //更新
        operationDeviceMapper.updateById(operationDeviceDO);
        //picture表
        operationDevicePictureMapper.delete(new QueryWrapper<OperationDevicePictureDO>().lambda()
                .eq(OperationDevicePictureDO::getDeviceId, operationDeviceDO.getId())
                .eq(OperationDevicePictureDO::getType, "1"));
        List<OperationDevicePictureDO> operationDevicePictureList = BeanUtils.toBean(registerReqVO.getPictureList(), OperationDevicePictureDO.class);
        operationDevicePictureList.forEach((a) -> a.setDeviceId(operationDeviceDO.getId()));
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
        if (StringUtils.isNotBlank(scrapReqVO.getScrapUserId() + "")) {
            operationDeviceDO.setScrapUserName(adminUserService.getUser(scrapReqVO.getScrapUserId()) == null ? "" : adminUserService.getUser(scrapReqVO.getScrapUserId()).getNickname());
        }
        operationDeviceDO.setScrapDealType(scrapReqVO.getScrapDealType());
        operationDeviceDO.setScrapRemark(scrapReqVO.getScrapRemark());

        //更新
        operationDeviceMapper.updateById(operationDeviceDO);
        //picture表
        List<OperationDevicePictureDO> operationDevicePictureList = BeanUtils.toBean(scrapReqVO.getPictureList(), OperationDevicePictureDO.class);
        operationDevicePictureList.forEach((a) -> a.setDeviceId(operationDeviceDO.getId()));
        operationDevicePictureMapper.insertBatch(operationDevicePictureList);
    }

    @Override
    public CommonResult<List<OperationLabelCodeRespVO>> getUseableLabelCode() {
        List<OperationLabelCodeDO> operationLabelCodeDOS = operationLabelCodeMapper.selectList(new QueryWrapper<OperationLabelCodeDO>().lambda().eq(OperationLabelCodeDO::getStatus, 0));
        List<OperationLabelCodeRespVO> operationLabelCodeRespVOS = BeanUtils.toBean(operationLabelCodeDOS, OperationLabelCodeRespVO.class);
        return CommonResult.success(operationLabelCodeRespVOS);
    }


    @Override
    public PageResult<OperationDeviceHistoryDO> getOperationDeviceHistoryPage(OperationDeviceHistoryPageReqVO pageReqVO) {
        return operationDeviceHistoryMapper.selectPage(pageReqVO);
    }

}