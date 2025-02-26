package com.hk.jigai.module.system.dal.mysql.operationgroup;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.operationgroup.vo.OperationGroupPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationgroup.OperationGroupDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 运维小组 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface OperationGroupMapper extends BaseMapperX<OperationGroupDO> {

    default PageResult<OperationGroupDO> selectPage(OperationGroupPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OperationGroupDO>()
                .orderByDesc(OperationGroupDO::getId));
    }

}