package com.hk.jigai.module.system.dal.mysql.prize;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.prize.vo.PrizePageReqVO;
import com.hk.jigai.module.system.dal.dataobject.prize.PrizeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 奖品信息 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface PrizeMapper extends BaseMapperX<PrizeDO> {

    default PageResult<PrizeDO> selectPage(PrizePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PrizeDO>()
                .eqIfPresent(PrizeDO::getActivityId, reqVO.getActivityId())
                .likeIfPresent(PrizeDO::getPrizeName, reqVO.getPrizeName())
                .eqIfPresent(PrizeDO::getPrizeQuantity, reqVO.getPrizeQuantity())
                .eqIfPresent(PrizeDO::getPrizeUrl, reqVO.getPrizeUrl())
                .eqIfPresent(PrizeDO::getRemainingQuantity, reqVO.getRemainingQuantity())
                .orderByDesc(PrizeDO::getId));
    }

}