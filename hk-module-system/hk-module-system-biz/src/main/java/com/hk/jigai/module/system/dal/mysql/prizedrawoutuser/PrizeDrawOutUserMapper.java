package com.hk.jigai.module.system.dal.mysql.prizedrawoutuser;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo.PrizeDrawOutUserPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawoutuser.PrizeDrawOutUserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 场外参与人员 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface PrizeDrawOutUserMapper extends BaseMapperX<PrizeDrawOutUserDO> {

    default PageResult<PrizeDrawOutUserDO> selectPage(PrizeDrawOutUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PrizeDrawOutUserDO>()
                .likeIfPresent(PrizeDrawOutUserDO::getNickname, reqVO.getNickname())
                .eqIfPresent(PrizeDrawOutUserDO::getDept, reqVO.getDept())
                .eqIfPresent(PrizeDrawOutUserDO::getWorkNum, reqVO.getWorkNum())
                .eqIfPresent(PrizeDrawOutUserDO::getMobile, reqVO.getMobile())
                .eqIfPresent(PrizeDrawOutUserDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PrizeDrawOutUserDO::getPrizeLevel, reqVO.getPrizeLevel())
                .betweenIfPresent(PrizeDrawOutUserDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PrizeDrawOutUserDO::getId));
    }

}