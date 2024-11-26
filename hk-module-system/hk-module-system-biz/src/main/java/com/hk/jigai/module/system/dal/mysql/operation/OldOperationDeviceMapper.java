package com.hk.jigai.module.system.dal.mysql.operation;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.module.system.controller.admin.operation.vo.OldOperationDevicePageReqVO;
import com.hk.jigai.module.system.controller.admin.operation.vo.OperationDevicePageReqVO;
import com.hk.jigai.module.system.dal.dataobject.operation.OldOperationDeviceDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 运维设备 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface OldOperationDeviceMapper extends BaseMapperX<OldOperationDeviceDO> {

    default PageResult<OldOperationDeviceDO> selectPage(OldOperationDevicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OldOperationDeviceDO>()
                .likeIfPresent(OldOperationDeviceDO::getResourcename, reqVO.getResourcename())
                );
    }

}