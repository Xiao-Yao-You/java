package com.hk.jigai.module.system.dal.mysql.prizedrawactivity;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.prizedrawactivity.vo.PrizeDrawActivityPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawactivity.PrizeDrawActivityDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 抽奖活动 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface PrizeDrawActivityMapper extends BaseMapperX<PrizeDrawActivityDO> {

    default PageResult<PrizeDrawActivityDO> selectPage(PrizeDrawActivityPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PrizeDrawActivityDO>()
                .likeIfPresent(PrizeDrawActivityDO::getActivityName, reqVO.getActivityName())
                .orderByDesc(PrizeDrawActivityDO::getId));
    }

}