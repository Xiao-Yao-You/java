package com.hk.jigai.module.system.service.operation;

import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.controller.admin.operationdevicehistory.vo.OperationDeviceHistoryPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.operation.OldOperationDeviceDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceDO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicehistory.OperationDeviceHistoryDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 运维设备 Service 接口
 *
 * @author 超级管理员
 */
public interface OldOperationDeviceService {

    PageResult<OldOperationDeviceDO> getOldOperationDevicePage(OldOperationDevicePageReqVO pageReqVO);

    OldOperationDeviceRespVO getOldOperationDevice(Long id);

}