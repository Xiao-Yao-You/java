package com.hk.jigai.module.system.dal.mysql.scenecode;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.scenecode.SceneCodeDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.scenecode.vo.*;

/**
 * 单据编码类型配置 Mapper
 *
 * @author 恒科信改
 */
@Mapper
public interface SceneCodeMapper extends BaseMapperX<SceneCodeDO> {

    default PageResult<SceneCodeDO> selectPage(SceneCodePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SceneCodeDO>()
                .eqIfPresent(SceneCodeDO::getKey, reqVO.getKey())
                .eqIfPresent(SceneCodeDO::getDescription, reqVO.getDescription())
                .eqIfPresent(SceneCodeDO::getPrefix, reqVO.getPrefix())
                .eqIfPresent(SceneCodeDO::getInfix, reqVO.getInfix())
                .eqIfPresent(SceneCodeDO::getSuffix, reqVO.getSuffix())
                .eqIfPresent(SceneCodeDO::getType, reqVO.getType())
                .eqIfPresent(SceneCodeDO::getStart, reqVO.getStart())
                .eqIfPresent(SceneCodeDO::getStep, reqVO.getStep())
                .eqIfPresent(SceneCodeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SceneCodeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SceneCodeDO::getId));
    }

}