package com.hk.jigai.module.system.dal.mysql.operationnoticeobject;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.operationnoticeobject.vo.OperationNoticeObjectPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationnoticeobject.OperationNoticeObjectDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息通知对象设置 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface OperationNoticeObjectMapper extends BaseMapperX<OperationNoticeObjectDO> {

    default PageResult<OperationNoticeObjectDO> selectPage(OperationNoticeObjectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OperationNoticeObjectDO>()
                .eqIfPresent(OperationNoticeObjectDO::getUserId, reqVO.getUserId())
                .betweenIfPresent(OperationNoticeObjectDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OperationNoticeObjectDO::getId));
    }

}