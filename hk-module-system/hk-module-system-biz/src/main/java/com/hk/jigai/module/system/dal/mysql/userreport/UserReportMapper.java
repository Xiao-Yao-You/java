package com.hk.jigai.module.system.dal.mysql.userreport;

import java.util.*;
import java.util.function.Consumer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.userreport.vo.AttentionAlertRespVO;
import com.hk.jigai.module.system.controller.admin.userreport.vo.StatisticsReqVO;
import com.hk.jigai.module.system.controller.admin.userreport.vo.StatisticsRespVO;
import com.hk.jigai.module.system.controller.admin.userreport.vo.UserReportPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.userreport.AttentionOtherInfoDO;
import com.hk.jigai.module.system.dal.dataobject.userreport.UserReportDO;
import com.hk.jigai.module.system.dal.dataobject.userreport.UserSummaryReportDO;
import org.apache.ibatis.annotations.Mapper;

import static com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 用户汇报 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface UserReportMapper extends BaseMapperX<UserReportDO> {
    default PageResult<UserReportDO> selectPage(UserReportPageReqVO reqVO) {
        String userId = getLoginUserId().toString();
        return selectPage(reqVO, new LambdaQueryWrapperX<UserReportDO>()
                .betweenIfPresent(UserReportDO::getDateReport, reqVO.getDateReport())
                .likeIfPresent(UserReportDO::getUserNickName, reqVO.getUserNickName())
                .and(ss -> ss.eq(UserReportDO::getUserId,  getLoginUserId())
                        .or().like(UserReportDO::getReportObject, userId)
                        .or().likeLeft(UserReportDO::getReportObject, userId + "]")
                        .or().likeRight(UserReportDO::getReportObject, "[" + userId)
                        .or().eq(UserReportDO::getReportObject, "[" + userId + "]"))
                .orderByDesc(UserReportDO::getId));
    }


    List<StatisticsRespVO> statistics(StatisticsReqVO reqVO);

    List<StatisticsRespVO> statisticsSelf(StatisticsReqVO reqVO);

    List<String> queryNotSubmitUser(StatisticsReqVO reqVO);

    List<UserSummaryReportDO> querySummaryReport(StatisticsReqVO reqVO);

    Integer selectCount1(StatisticsReqVO reqVO);

    List<AttentionAlertRespVO> queryAttentionList(Long userId);

    AttentionOtherInfoDO queryByschedule(Long jobId);

    AttentionOtherInfoDO queryByPlan(Long jobId);

}