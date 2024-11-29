package com.hk.jigai.module.system.dal.mysql.operation;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.operation.vo.OldOperationDevicePageReqVO;
import com.hk.jigai.module.system.dal.dataobject.operation.OldOperationDeviceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 运维设备 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface OldOperationDeviceMapper extends BaseMapperX<OldOperationDeviceDO> {
    List<OldOperationDeviceDO> selectPage(OldOperationDevicePageReqVO reqVO);

    Long selectTotal(OldOperationDevicePageReqVO pageReqVO);

    List<Long> selectModelType(String deviceType);

    List<Long> selectModelIds(String productname);

    String selectDeptByDeptId(Long deptid);

    String selectCorporationById(Long corporationid);

    String selectModelById(Long productid);

    String selectPersonById(String personNo);

}