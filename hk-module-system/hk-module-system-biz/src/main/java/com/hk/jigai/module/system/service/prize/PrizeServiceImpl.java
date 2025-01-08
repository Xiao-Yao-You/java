package com.hk.jigai.module.system.service.prize;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.module.system.controller.admin.prize.vo.PrizePageReqVO;
import com.hk.jigai.module.system.controller.admin.prize.vo.PrizeSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.prize.PrizeDO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawactivity.PrizeDrawActivityDO;
import com.hk.jigai.module.system.dal.mysql.prize.PrizeMapper;
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
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.PRIZE_NOT_EXISTS;

/**
 * 奖品信息 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class PrizeServiceImpl implements PrizeService {

    @Resource
    private PrizeMapper prizeMapper;

    @Resource
    private PrizeDrawActivityMapper prizeDrawActivityMapper;

    @Override
    public Long createPrize(PrizeSaveReqVO createReqVO) {
        // 插入
        PrizeDO prize = BeanUtils.toBean(createReqVO, PrizeDO.class);

        PrizeDrawActivityDO prizeDrawActivityDO = prizeDrawActivityMapper.selectOne(new QueryWrapper<PrizeDrawActivityDO>().lambda().eq(PrizeDrawActivityDO::getId, createReqVO.getActivityId()));
        prize.setActivityName(prizeDrawActivityDO.getActivityName());
        prizeMapper.insert(prize);
        // 返回
        return prize.getId();
    }

    @Override
    public void updatePrize(PrizeSaveReqVO updateReqVO) {
        // 校验存在
        validatePrizeExists(updateReqVO.getId());
        // 更新
        PrizeDO updateObj = BeanUtils.toBean(updateReqVO, PrizeDO.class);
        prizeMapper.updateById(updateObj);
    }

    @Override
    public void deletePrize(Long id) {
        // 校验存在
        validatePrizeExists(id);
        // 删除
        prizeMapper.deleteById(id);
    }

    private void validatePrizeExists(Long id) {
        if (prizeMapper.selectById(id) == null) {
            throw exception(PRIZE_NOT_EXISTS);
        }
    }

    @Override
    public PrizeDO getPrize(Long id) {
        return prizeMapper.selectById(id);
    }

    @Override
    public PageResult<PrizeDO> getPrizePage(PrizePageReqVO pageReqVO) {
        return prizeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PrizeDO> getAllPrizeByActivityId(Long activityId) {
        return prizeMapper.selectList(new QueryWrapper<PrizeDO>().lambda().eq(PrizeDO::getActivityId, activityId).orderByAsc(PrizeDO::getLevel));
    }

}