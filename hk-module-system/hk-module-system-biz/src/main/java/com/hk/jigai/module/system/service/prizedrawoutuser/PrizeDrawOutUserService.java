package com.hk.jigai.module.system.service.prizedrawoutuser;

import java.util.*;
import javax.validation.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo.PrizeDrawOutUserPageReqVO;
import com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo.PrizeDrawOutUserSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawoutuser.PrizeDrawOutUserDO;

/**
 * 场外参与人员 Service 接口
 *
 * @author 邵志伟
 */
public interface PrizeDrawOutUserService {

    /**
     * 场外参与抽奖
     * @param activityId
     * @param prizePool
     * @param winNum
     * @return
     */
    List<PrizeDrawOutUserDO> prizeDraw(Long activityId, Long prizePool, Integer winNum);

    /**
     * 场外人员中奖名单
     * @param activityId
     * @param prizeLevel
     * @return
     */
    List<PrizeDrawOutUserDO> getAllWinnerList(Long activityId, Long prizeLevel);
}