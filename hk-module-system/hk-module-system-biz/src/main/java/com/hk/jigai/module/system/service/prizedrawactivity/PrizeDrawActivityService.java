package com.hk.jigai.module.system.service.prizedrawactivity;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.prizedrawactivity.vo.PrizeDrawActivityPageReqVO;
import com.hk.jigai.module.system.controller.admin.prizedrawactivity.vo.PrizeDrawActivitySaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawactivity.PrizeDrawActivityDO;

/**
 * 抽奖活动 Service 接口
 *
 * @author 邵志伟
 */
public interface PrizeDrawActivityService {

    /**
     * 创建抽奖活动
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPrizeDrawActivity(@Valid PrizeDrawActivitySaveReqVO createReqVO);

    /**
     * 更新抽奖活动
     *
     * @param updateReqVO 更新信息
     */
    void updatePrizeDrawActivity(@Valid PrizeDrawActivitySaveReqVO updateReqVO);

    /**
     * 删除抽奖活动
     *
     * @param id 编号
     */
    void deletePrizeDrawActivity(Long id);

    /**
     * 获得抽奖活动
     *
     * @param id 编号
     * @return 抽奖活动
     */
    PrizeDrawActivityDO getPrizeDrawActivity(Long id);

    /**
     * 获得抽奖活动分页
     *
     * @param pageReqVO 分页查询
     * @return 抽奖活动分页
     */
    PageResult<PrizeDrawActivityDO> getPrizeDrawActivityPage(PrizeDrawActivityPageReqVO pageReqVO);

}