package com.hk.jigai.module.system.service.prizedrawactivity;

import com.hk.jigai.module.system.controller.admin.prizedrawactivity.vo.PrizeDrawActivityPageReqVO;
import com.hk.jigai.module.system.controller.admin.prizedrawactivity.vo.PrizeDrawActivitySaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawactivity.PrizeDrawActivityDO;
import com.hk.jigai.module.system.dal.mysql.prizedrawactivity.PrizeDrawActivityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.PRIZE_DRAW_ACTIVITY_NOT_EXISTS;

/**
 * 抽奖活动 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class PrizeDrawActivityServiceImpl implements PrizeDrawActivityService {

    @Resource
    private PrizeDrawActivityMapper prizeDrawActivityMapper;

    @Override
    public Long createPrizeDrawActivity(PrizeDrawActivitySaveReqVO createReqVO) {
        // 插入
        PrizeDrawActivityDO prizeDrawActivity = BeanUtils.toBean(createReqVO, PrizeDrawActivityDO.class);
        prizeDrawActivityMapper.insert(prizeDrawActivity);
        // 返回
        return prizeDrawActivity.getId();
    }

    @Override
    public void updatePrizeDrawActivity(PrizeDrawActivitySaveReqVO updateReqVO) {
        // 校验存在
        validatePrizeDrawActivityExists(updateReqVO.getId());
        // 更新
        PrizeDrawActivityDO updateObj = BeanUtils.toBean(updateReqVO, PrizeDrawActivityDO.class);
        prizeDrawActivityMapper.updateById(updateObj);
    }

    @Override
    public void deletePrizeDrawActivity(Long id) {
        // 校验存在
        validatePrizeDrawActivityExists(id);
        // 删除
        prizeDrawActivityMapper.deleteById(id);
    }

    private void validatePrizeDrawActivityExists(Long id) {
        if (prizeDrawActivityMapper.selectById(id) == null) {
            throw exception(PRIZE_DRAW_ACTIVITY_NOT_EXISTS);
        }
    }

    @Override
    public PrizeDrawActivityDO getPrizeDrawActivity(Long id) {
        return prizeDrawActivityMapper.selectById(id);
    }

    @Override
    public PageResult<PrizeDrawActivityDO> getPrizeDrawActivityPage(PrizeDrawActivityPageReqVO pageReqVO) {
        return prizeDrawActivityMapper.selectPage(pageReqVO);
    }

}