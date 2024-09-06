package com.hk.jigai.module.system.service.userreport;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.framework.security.core.LoginUser;
import com.hk.jigai.module.system.dal.dataobject.dept.UserDeptDO;
import com.hk.jigai.module.system.dal.dataobject.userreport.*;
import com.hk.jigai.module.system.dal.mysql.dept.DeptMapper;
import com.hk.jigai.module.system.dal.mysql.userreport.ReportJobScheduleMapper;
import com.hk.jigai.module.system.dal.mysql.userreport.ReportTransferRecordMapper;
import com.hk.jigai.module.system.dal.mysql.userreport.UserReportMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import com.hk.jigai.module.system.controller.admin.userreport.vo.*;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.module.system.dal.mysql.userreport.ReportAttentionMapper;
import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils.*;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 汇报关注跟进 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class ReportAttentionServiceImpl implements ReportAttentionService {

    @Resource
    private ReportAttentionMapper reportAttentionMapper;
    @Resource
    private ReportJobScheduleMapper reportJobScheduleMapper;
    @Resource
    private UserReportMapper userReportMapper;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private ReportTransferRecordMapper reportTransferRecordMapper;

    @Override
    public void updateReportAttention(ReportAttentionSaveReqVO updateReqVO) {
        // 校验存在
        validateReportAttentionExists(updateReqVO.getId());
        // 更新
        ReportAttentionDO updateObj = BeanUtils.toBean(updateReqVO, ReportAttentionDO.class);
        reportAttentionMapper.updateById(updateObj);
    }

    @Override
    public void deleteReportAttention(Long id) {
        // 校验存在
        validateReportAttentionExists(id);
        // 删除
        reportAttentionMapper.deleteById(id);
    }

    private void validateReportAttentionExists(Long id) {
        if (reportAttentionMapper.selectById(id) == null) {
            throw exception(REPORT_ATTENTION_NOT_EXISTS);
        }
    }

    @Override
    public ReportAttentionDO getReportAttention(Long id) {
        // 校验存在
        validateReportAttentionExists(id);
        return reportAttentionMapper.selectById(id);
    }

    @Override
    public PageResult<ReportAttentionDO> getReportAttentionPage(ReportAttentionPageReqVO pageReqVO) {
        return reportAttentionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ReportAttentionDO> queryFollowUndo() {
        return reportAttentionMapper.selectList();
    }

    @Override
    public Long createAttention(CreateAttentionReqVO request) {
        //1、根据jobId查询相关信息，并校验当前登录人是否有权限做关注操作
        AttentionOtherInfoDO attentionOtherInfoDO;
        if(request.getType().equals(new Integer(0))){
            attentionOtherInfoDO = userReportMapper.queryByschedule(request.getJobId());
        }else{
            attentionOtherInfoDO = userReportMapper.queryByPlan(request.getJobId());
        }
        if(attentionOtherInfoDO == null){
            throw exception(USER_REPORT_INFO_NOT_EXISTS);
        }
        Long loginUserId = getLoginUserId();
        if(CollectionUtils.isAnyEmpty(attentionOtherInfoDO.getReportObject()) || !attentionOtherInfoDO.getReportObject().contains(loginUserId)){
            throw exception(USER_REPORT_NOT_OPERATE);
        }
        //2.补充一些字段
        ReportAttentionDO reportAttentionDO = new ReportAttentionDO();
        reportAttentionDO.setDateReport(attentionOtherInfoDO.getDateReport());
        reportAttentionDO.setContent(attentionOtherInfoDO.getContent());
        reportAttentionDO.setConnectContent(attentionOtherInfoDO.getConnectContent());
        reportAttentionDO.setReply(request.getReply());
        reportAttentionDO.setDeptId(attentionOtherInfoDO.getDeptId());
        reportAttentionDO.setDeptName(attentionOtherInfoDO.getDeptName());
        reportAttentionDO.setReplyUserId(attentionOtherInfoDO.getUserId());
        reportAttentionDO.setReplyUserNickName(attentionOtherInfoDO.getUserNickName());
        reportAttentionDO.setReportUserId(attentionOtherInfoDO.getUserId());
        reportAttentionDO.setReportUserNickName(attentionOtherInfoDO.getUserNickName());
        reportAttentionDO.setUserId(loginUserId);
        reportAttentionDO.setUserNickName(getLoginUserNickname());
        reportAttentionDO.setType(request.getType());
        reportAttentionDO.setJobId(request.getJobId());
        //3.做插入操作
        reportAttentionMapper.insert(reportAttentionDO);
        return reportAttentionDO.getId();
    }

    @Override
    @Transactional
    public void transfer(ReportAttentionTransferReqVO updateReqVO) {
        //1.校验存在
        ReportAttentionDO reportAttentionDO = reportAttentionMapper.selectById(updateReqVO.getId());
        if (reportAttentionDO == null) {
            throw exception(REPORT_ATTENTION_NOT_EXISTS);
        }
        ReportAttentionDO checkReportAttentionDO = reportAttentionMapper.selectOne(new QueryWrapper<ReportAttentionDO>()
                .lambda().eq(ReportAttentionDO::getReplyUserId, getLoginUserId()).eq(ReportAttentionDO::getId,updateReqVO.getId()));
        if (checkReportAttentionDO == null) {
            throw exception(USER_REPORT_NOT_OPERATE);
        }
        // 更新
        ReportAttentionDO updateObj = BeanUtils.toBean(updateReqVO, ReportAttentionDO.class);
        reportAttentionMapper.updateById(updateObj);
        ReportTransferRecordDO reportTransferRecordDO = new ReportTransferRecordDO();
        reportTransferRecordDO.setTransferRemark(updateReqVO.getTransferRemark());
        reportTransferRecordDO.setTransferTime(LocalDateTime.now());
        reportTransferRecordDO.setReportAttentionId(updateReqVO.getId());
        reportTransferRecordDO.setOperatorNickName(getLoginUserNickname());
        reportTransferRecordDO.setOperatorUserId(getLoginUserId());
        reportTransferRecordDO.setReplyUserId(updateReqVO.getReplyUserId());
        reportTransferRecordDO.setReplyUserNickName(updateReqVO.getReplyUserNickName());
        reportTransferRecordMapper.insert(reportTransferRecordDO);
    }

    @Override
    @Transactional
    public Long follow(ReportAttentionFollowReqVO updateReqVO) {
        //1.校验存在
        ReportAttentionDO reportAttentionDO = reportAttentionMapper.selectById(updateReqVO.getId());
        if (reportAttentionDO == null) {
            throw exception(REPORT_ATTENTION_NOT_EXISTS);
        }
        ReportAttentionDO checkReportAttentionDO = reportAttentionMapper.selectOne(new QueryWrapper<ReportAttentionDO>()
                .lambda().eq(ReportAttentionDO::getReplyUserId, getLoginUserId()).eq(ReportAttentionDO::getId,updateReqVO.getId()));
        if (checkReportAttentionDO == null) {
            throw exception(USER_REPORT_NOT_OPERATE);
        }
        //2.创建汇报以及进度
        //2.1根据汇报日期校验当天是否已经提交过汇报
        UserReportDO userReportDOS = userReportMapper.selectOne(new QueryWrapper<UserReportDO>().lambda()
                .eq(UserReportDO::getDateReport, LocalDate.now())
                .eq(UserReportDO::getDeptId, reportAttentionDO.getDeptId())
                .eq(UserReportDO::getUserId, reportAttentionDO.getReplyUserId()));

        //2.2如果没有则先新增汇报后新增进度表
        if(userReportDOS == null){
            userReportDOS = new UserReportDO();
            userReportDOS.setDateReport(LocalDate.now());
            userReportDOS.setUserId(reportAttentionDO.getReplyUserId());
            userReportDOS.setType("0");
            userReportDOS.setDeptId(reportAttentionDO.getDeptId());
            userReportDOS.setCommitTime(LocalDateTime.now());
            Set<Long> reportObject = new HashSet();
            reportObject.add(reportAttentionDO.getUserId());
            userReportDOS.setReportObject(reportObject);
            userReportDOS.setDeptName(deptMapper.selectById(reportAttentionDO.getDeptId()).getName());
            userReportDOS.setUserNickName(reportAttentionDO.getReplyUserNickName());
            userReportMapper.insert(userReportDOS);
        }
        ReportJobScheduleDO reportJobScheduleDO = new ReportJobScheduleDO();
        reportJobScheduleDO.setUserReportId(userReportDOS.getId());
        reportJobScheduleDO.setContent(updateReqVO.getContent());
        reportJobScheduleDO.setSituation(updateReqVO.getSituation());
        reportJobScheduleDO.setConnectContent(reportAttentionDO.getContent());
        reportJobScheduleDO.setConnectId(updateReqVO.getId());
        reportJobScheduleMapper.insert(reportJobScheduleDO);

        //3.更新
        ReportAttentionDO updateObj = new ReportAttentionDO();
        updateObj.setId(updateReqVO.getId());
        updateObj.setSituation(updateReqVO.getSituation());
        updateObj.setReplyStatus("1");
        updateObj.setJobScheduleId(reportJobScheduleDO.getId());
        reportAttentionMapper.updateById(updateObj);
        //4.返回
        return reportJobScheduleDO.getId();
    }

    @Override
    public List<ReportTransferRecordDO> queryTransferList(Long id) {
        // 校验存在
        validateReportAttentionExists(id);

        return reportTransferRecordMapper.selectList(new QueryWrapper<ReportTransferRecordDO>().lambda()
                .eq(ReportTransferRecordDO::getReportAttentionId, id)
                .orderByDesc(ReportTransferRecordDO::getId)
        );
    }
}