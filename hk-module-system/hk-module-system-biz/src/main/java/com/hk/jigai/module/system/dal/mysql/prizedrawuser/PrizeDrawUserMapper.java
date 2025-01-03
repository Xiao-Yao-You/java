package com.hk.jigai.module.system.dal.mysql.prizedrawuser;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.prizedrawuser.vo.PrizeDrawUserPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawuser.PrizeDrawUserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 抽奖用户 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface PrizeDrawUserMapper extends BaseMapperX<PrizeDrawUserDO> {

    default PageResult<PrizeDrawUserDO> selectPage(PrizeDrawUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PrizeDrawUserDO>()
                .likeIfPresent(PrizeDrawUserDO::getNickname, reqVO.getNickname())
                .eqIfPresent(PrizeDrawUserDO::getHeadimgurl, reqVO.getHeadimgurl())
                .eqIfPresent(PrizeDrawUserDO::getOpenid, reqVO.getOpenid())
                .eqIfPresent(PrizeDrawUserDO::getActivityBatch, reqVO.getActivityBatch())
                .eqIfPresent(PrizeDrawUserDO::getWinningRate, reqVO.getWinningRate())
                .orderByDesc(PrizeDrawUserDO::getId));
    }

}