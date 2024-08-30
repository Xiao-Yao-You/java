package com.hk.jigai.module.system.dal.mysql.userreport;


import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportTransferRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 汇报转交记录 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface ReportTransferRecordMapper extends BaseMapperX<ReportTransferRecordDO> {

//    default PageResult<ReportTransferRecordDO> selectPage(ReportTransferRecordPageReqVO reqVO) {
//        return selectPage(reqVO, new LambdaQueryWrapperX<ReportTransferRecordDO>()
//                .eqIfPresent(ReportTransferRecordDO::getReportAttentionId, reqVO.getReportAttentionId())
//                .eqIfPresent(ReportTransferRecordDO::getOperatorUserId, reqVO.getOperatorUserId())
//                .likeIfPresent(ReportTransferRecordDO::getOperatorNickName, reqVO.getOperatorNickName())
//                .eqIfPresent(ReportTransferRecordDO::getTransferRemark, reqVO.getTransferRemark())
//                .eqIfPresent(ReportTransferRecordDO::getReplyUserId, reqVO.getReplyUserId())
//                .likeIfPresent(ReportTransferRecordDO::getReplyUserNickName, reqVO.getReplyUserNickName())
//                .betweenIfPresent(ReportTransferRecordDO::getTransferTime, reqVO.getTransferTime())
//                .betweenIfPresent(ReportTransferRecordDO::getCreateTime, reqVO.getCreateTime())
//                .orderByDesc(ReportTransferRecordDO::getId));
//    }

}