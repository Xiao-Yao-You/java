package com.hk.jigai.module.system.dal.mysql.reasonablesuggestion;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo.ReasonableSuggestionPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderDO;
import com.hk.jigai.module.system.dal.dataobject.reasonablesuggestion.ReasonableSuggestionDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合理化建议 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface ReasonableSuggestionMapper extends BaseMapperX<ReasonableSuggestionDO> {

    default PageResult<ReasonableSuggestionDO> selectPage(ReasonableSuggestionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReasonableSuggestionDO>()
                .eqIfPresent(ReasonableSuggestionDO::getTitle, reqVO.getTitle())
                .eqIfPresent(ReasonableSuggestionDO::getSuggestionType, reqVO.getSuggestionType())
                .eqIfPresent(ReasonableSuggestionDO::getUserId, reqVO.getUserId())
                .likeIfPresent(ReasonableSuggestionDO::getNickname, reqVO.getNickname())
                .likeIfPresent(ReasonableSuggestionDO::getDeptName, reqVO.getDeptName())
                .eqIfPresent(ReasonableSuggestionDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ReasonableSuggestionDO::getFilePath, reqVO.getFilePath())
                .betweenIfPresent(ReasonableSuggestionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ReasonableSuggestionDO::getCreator, reqVO.getCreator())
                .orderByDesc(ReasonableSuggestionDO::getId));
    }

}