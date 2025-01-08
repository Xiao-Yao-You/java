package com.hk.jigai.module.system.service.prize;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.prize.vo.PrizePageReqVO;
import com.hk.jigai.module.system.controller.admin.prize.vo.PrizeSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.prize.PrizeDO;

/**
 * 奖品信息 Service 接口
 *
 * @author 邵志伟
 */
public interface PrizeService {

    /**
     * 创建奖品信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPrize(@Valid PrizeSaveReqVO createReqVO);

    /**
     * 更新奖品信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePrize(@Valid PrizeSaveReqVO updateReqVO);

    /**
     * 删除奖品信息
     *
     * @param id 编号
     */
    void deletePrize(Long id);

    /**
     * 获得奖品信息
     *
     * @param id 编号
     * @return 奖品信息
     */
    PrizeDO getPrize(Long id);

    /**
     * 获得奖品信息分页
     *
     * @param pageReqVO 分页查询
     * @return 奖品信息分页
     */
    PageResult<PrizeDO> getPrizePage(PrizePageReqVO pageReqVO);

    /**
     * 根据活动Id查询活动下的奖品
     * @param activityId
     * @return
     */
    List<PrizeDO> getAllPrizeByActivityId(Long activityId);
}