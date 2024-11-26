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
        return oldOperationDeviceMapper.selectPage(pageReqVO);
    }

    @Override
    @Slave  //从数据库
    public OldOperationDeviceRespVO getOldOperationDevice(Long id) {
        OldOperationDeviceDO oldOperationDeviceDO = oldOperationDeviceMapper.selectById(id);
        return BeanUtils.toBean(oldOperationDeviceDO, OldOperationDeviceRespVO.class);
    }
}