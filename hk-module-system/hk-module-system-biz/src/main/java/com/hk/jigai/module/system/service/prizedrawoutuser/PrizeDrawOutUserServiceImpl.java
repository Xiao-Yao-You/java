package com.hk.jigai.module.system.service.prizedrawoutuser;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo.PrizeDrawOutUserPageReqVO;
import com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo.PrizeDrawOutUserSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawoutuser.PrizeDrawOutUserDO;
import com.hk.jigai.module.system.dal.dataobject.prizedrawuser.PrizeDrawUserDO;
import com.hk.jigai.module.system.dal.mysql.prizedrawoutuser.PrizeDrawOutUserMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.PRIZE_DRAW_OUT_USER_NOT_EXISTS;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.PRIZE_DRAW_USER_NOT_ENOUGH;

/**
 * 场外参与人员 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class PrizeDrawOutUserServiceImpl implements PrizeDrawOutUserService {
    @Resource
    private PrizeDrawOutUserMapper prizeDrawOutUserMapper;

    @Override
    @Transactional
    public List<PrizeDrawOutUserDO> prizeDraw(Long activityId, Long prizePool, Integer winNum) {
        //所有可以参与抽奖的人员
        List<PrizeDrawOutUserDO> prizeDrawOutUserDOS = prizeDrawOutUserMapper.selectList(new QueryWrapper<PrizeDrawOutUserDO>().lambda()
                .eq(PrizeDrawOutUserDO::getActivityBatch, activityId)
                .eq(PrizeDrawOutUserDO::getStatus, 1)
                .and(wrapper->wrapper.isNull(PrizeDrawOutUserDO::getPrizePool).or().eq(PrizeDrawOutUserDO::getPrizePool , prizePool)));
        if (CollectionUtil.isEmpty(prizeDrawOutUserDOS) || prizeDrawOutUserDOS.size() < winNum) {
            throw exception(PRIZE_DRAW_USER_NOT_ENOUGH);
        }
        //开始抽奖
        List<PrizeDrawOutUserDO> winners = drawWinners(prizeDrawOutUserDOS, winNum);
        winners.stream().map(p->{
            p.setStatus(2);
            p.setPrizeLevel(prizePool.intValue());
            return p;
        }).collect(Collectors.toList());
        prizeDrawOutUserMapper.updateBatch(winners);
        return winners;
    }

    @Override
    public List<PrizeDrawOutUserDO> getAllWinnerList(Long activityId, Long prizeLevel) {
        List<PrizeDrawOutUserDO> prizeDrawOutUserDOS = prizeDrawOutUserMapper.selectList(new QueryWrapper<PrizeDrawOutUserDO>().lambda()
                .eq(PrizeDrawOutUserDO::getActivityBatch, activityId)
                .eq(PrizeDrawOutUserDO::getPrizeLevel, prizeLevel));

        return prizeDrawOutUserDOS;
    }

    public static List<PrizeDrawOutUserDO> drawWinners(List<PrizeDrawOutUserDO> participants, int numberOfWinners) {
        List<PrizeDrawOutUserDO> winners = new ArrayList<>();

        // 计算累积概率
        double[] cumulativeProbabilities = new double[participants.size()];
        double sum = 0.0;
        for (int i = 0; i < participants.size(); i++) {
            sum += participants.get(i).getWinningRate();
            cumulativeProbabilities[i] = sum;
        }

        // 使用随机数生成器选择中奖者
        Random random = new Random();
        while (winners.size() < numberOfWinners) {
            double rand = random.nextDouble() * sum;
            int winnerIndex = binarySearch(cumulativeProbabilities, rand);
            PrizeDrawOutUserDO winner = participants.get(winnerIndex);

            // 如果该参与者已经被抽中过，则继续抽，否则将其添加到中奖者列表
            if (!winners.contains(winner)) {
                winners.add(winner);
            }
        }

        return winners;
    }

    private static int binarySearch(double[] cumulativeProbabilities, double rand) {
        int low = 0, high = cumulativeProbabilities.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            double midVal = cumulativeProbabilities[mid];

            if (midVal < rand) {
                low = mid + 1;
            } else if (midVal > rand) {
                high = mid - 1;
            } else {
                return mid; // 这种情况几乎不会发生，因为概率是浮点数
            }
        }
        return low;
    }
}