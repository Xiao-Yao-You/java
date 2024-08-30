package com.hk.jigai.module.system.dal.mysql.userreport;


import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportAttentionDO;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.userreport.vo.*;

import static com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * 汇报关注跟进 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ReportAttentionMapper extends BaseMapperX<ReportAttentionDO> {

    default PageResult<ReportAttentionDO> selectPage(ReportAttentionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReportAttentionDO>()
                .eqIfPresent(ReportAttentionDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ReportAttentionDO::getReply, reqVO.getReply())
                .eqIfPresent(ReportAttentionDO::getType, reqVO.getType())
                .eqIfPresent(ReportAttentionDO::getJobId, reqVO.getJobId())
                .eqIfPresent(ReportAttentionDO::getConnectContent, reqVO.getConnectContent())
                .eqIfPresent(ReportAttentionDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(ReportAttentionDO::getDateReport, reqVO.getDateReport())
                .likeIfPresent(ReportAttentionDO::getReplyUserNickName, reqVO.getReplyUserNickName())
                .likeIfPresent(ReportAttentionDO::getReportUserNickName, reqVO.getReportUserNickName())
                .eqIfPresent(ReportAttentionDO::getContent, reqVO.getContent())
                .eqIfPresent(ReportAttentionDO::getTransferRemark, reqVO.getTransferRemark())
                .eqIfPresent(ReportAttentionDO::getReplyUserId, reqVO.getReplyUserId())
                .eqIfPresent(ReportAttentionDO::getSituation, reqVO.getSituation())
                .eqIfPresent(ReportAttentionDO::getReplyStatus, reqVO.getReplyStatus())
                .eqIfPresent(ReportAttentionDO::getJobScheduleId, reqVO.getJobScheduleId())
                .betweenIfPresent(ReportAttentionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ReportAttentionDO::getId));
    }

    default List<ReportAttentionDO> selectList() {
        return selectList(new LambdaQueryWrapperX<ReportAttentionDO>()
                .eqIfPresent(ReportAttentionDO::getReplyUserId, getLoginUserId())
                .eqIfPresent(ReportAttentionDO::getReplyStatus, "0")
                .orderByDesc(ReportAttentionDO::getId));
    }

}