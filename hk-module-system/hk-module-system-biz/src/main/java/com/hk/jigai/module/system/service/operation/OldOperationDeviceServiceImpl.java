package com.hk.jigai.module.system.service.operation;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.Slave;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;
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
import com.hk.jigai.module.system.service.scenecode.SceneCodeService;
import com.hk.jigai.module.system.service.user.AdminUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 运维设备 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class OldOperationDeviceServiceImpl implements OldOperationDeviceService {

    @Resource
    private OldOperationDeviceMapper oldOperationDeviceMapper;

    @Override
    @Slave  //从数据库
    public PageResult<OldOperationDeviceDO> getOldOperationDevicePage(OldOperationDevicePageReqVO pageReqVO) {
        PageResult pageResult = new PageResult();

        if (StringUtils.isNotBlank(pageReqVO.getCitype())) {
            List<Long> modelIds = oldOperationDeviceMapper.selectModelType(pageReqVO.getCitype());//根据设备类型查询所有的型号ID
            if (CollectionUtil.isNotEmpty(modelIds)) {
                pageReqVO.setModelIds(modelIds);
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getProductname())) {
            List<Long> modelIds = oldOperationDeviceMapper.selectModelIds(pageReqVO.getProductname());//根据设备类型查询所有的型号ID
            if (CollectionUtil.isNotEmpty(modelIds)) {
                pageReqVO.setModelIds(modelIds);
            }
        }

        List<OldOperationDeviceDO> oldOperationDeviceDOS = oldOperationDeviceMapper.selectPage(pageReqVO);
        Long count = oldOperationDeviceMapper.selectTotal(pageReqVO);
        pageResult.setList(oldOperationDeviceDOS);
        pageResult.setTotal(count);
        return pageResult;
    }

    @Override
    @Slave  //从数据库
    public PageResult<OldOperationDeviceDTO> getOldOperationDevicePageForSync(OldOperationDevicePageReqVO pageReqVO) {
        PageResult pageResult = new PageResult();

        if (StringUtils.isNotBlank(pageReqVO.getCitype())) {
            List<Long> modelIds = oldOperationDeviceMapper.selectModelType(pageReqVO.getCitype());//根据设备类型查询所有的型号ID
            if (CollectionUtil.isNotEmpty(modelIds)) {
                pageReqVO.setModelIds(modelIds);
            }
        }

        if (StringUtils.isNotBlank(pageReqVO.getProductname())) {
            List<Long> modelIds = oldOperationDeviceMapper.selectModelIds(pageReqVO.getProductname());//根据设备类型查询所有的型号ID
            if (CollectionUtil.isNotEmpty(modelIds)) {
                pageReqVO.setModelIds(modelIds);
            }
        }

        List<OldOperationDeviceDTO> oldOperationDeviceDOS = oldOperationDeviceMapper.selectPageForSync(pageReqVO);
        Long count = oldOperationDeviceMapper.selectTotal(pageReqVO);
        pageResult.setList(oldOperationDeviceDOS);
        pageResult.setTotal(count);
        return pageResult;
    }


    @Override
    @Slave  //从数据库
    public OldOperationDeviceRespVO getOldOperationDevice(Long id) {
        String photoPath = "https://szh.jshkxcl.cn/hkjg-oldpic/";
        OldOperationDeviceDO oldOperationDeviceDO = oldOperationDeviceMapper.selectById(id);
        String departName = oldOperationDeviceMapper.selectDeptByDeptId(oldOperationDeviceDO.getDeptid());
        String corporationName = oldOperationDeviceMapper.selectCorporationById(oldOperationDeviceDO.getCorporationid());
        String modelName = oldOperationDeviceMapper.selectModelById(oldOperationDeviceDO.getProductid());
        String personName = oldOperationDeviceMapper.selectPersonById(oldOperationDeviceDO.getUserno());
        oldOperationDeviceDO.setDepartmentname(departName);
        oldOperationDeviceDO.setCorporationname(corporationName);
        oldOperationDeviceDO.setProductname(modelName);
        oldOperationDeviceDO.setPersonname(personName);
        OldOperationDeviceRespVO oldOperationDeviceRespVO = BeanUtils.toBean(oldOperationDeviceDO, OldOperationDeviceRespVO.class);
        String productphoto = oldOperationDeviceDO.getProductphoto();
        String typeName = oldOperationDeviceMapper.selectDeviceByProductId(oldOperationDeviceRespVO.getProductid());
        oldOperationDeviceRespVO.setTypeName(typeName);
        List<String> productPhotoList = new ArrayList<>();

        //使用￥分割历史图片字符串并组装城集合
        if (StringUtils.isNotBlank(productphoto)) {
            String[] p1 = productphoto.split("￥");
            for (String pStr : p1) {
                productPhotoList.add(photoPath + pStr + ".jpg");
            }
        }
        List<String> globalPhotoList = new ArrayList<>();
        String gobalphoto = oldOperationDeviceDO.getGobalphoto();
        if (StringUtils.isNotBlank(gobalphoto)) {
            String[] p1 = gobalphoto.split("￥");
            for (String pStr : p1) {
                globalPhotoList.add(photoPath + pStr + ".jpg");
            }
        }

        List<String> displayPhotoList = new ArrayList<>();
        String displayphoto = oldOperationDeviceDO.getDisplayphoto();
        if (StringUtils.isNotBlank(displayphoto)) {
            String[] p1 = displayphoto.split("￥");
            for (String pStr : p1) {
                displayPhotoList.add(photoPath + pStr + ".jpg");
            }
        }
        oldOperationDeviceRespVO.setProductPhotoList(productPhotoList);
        oldOperationDeviceRespVO.setGlobalPhotoList(globalPhotoList);
        oldOperationDeviceRespVO.setDisplayPhotoList(displayPhotoList);
        return oldOperationDeviceRespVO;
    }
}