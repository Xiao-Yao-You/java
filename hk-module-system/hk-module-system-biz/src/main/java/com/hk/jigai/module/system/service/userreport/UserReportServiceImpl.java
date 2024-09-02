package com.hk.jigai.module.system.service.userreport;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.module.system.controller.admin.userreport.vo.*;
import com.hk.jigai.module.system.dal.dataobject.userreport.*;
import com.hk.jigai.module.system.dal.mysql.dept.DeptMapper;
import com.hk.jigai.module.system.dal.mysql.userreport.ReportJobPlanMapper;
import com.hk.jigai.module.system.dal.mysql.userreport.ReportJobScheduleMapper;
import com.hk.jigai.module.system.dal.mysql.userreport.ReportAttentionMapper;
import com.hk.jigai.module.system.dal.mysql.userreport.UserReportMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserNickname;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 用户汇报 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class UserReportServiceImpl implements UserReportService {
    @Resource
    private UserReportMapper userReportMapper;
    @Resource
    private ReportJobScheduleMapper reportJobScheduleMapper;
    @Resource
    private ReportJobPlanMapper reportJobPlanMapper;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private ReportAttentionMapper reportAttentionMapper;

    @Override
    @Transactional
    public Long createUserReport(UserReportSaveReqVO createReqVO) {
        UserReportDO userReport = BeanUtils.toBean(createReqVO, UserReportDO.class);
        //根据汇报日期校验当天是否已经提交过汇报
        List<UserReportDO> userReportDOS = userReportMapper.selectList(new QueryWrapper<UserReportDO>().lambda()
                .eq(UserReportDO::getDateReport, createReqVO.getDateReport())
                .eq(UserReportDO::getDeptId, userReport.getDeptId()));
        if (CollectionUtil.isNotEmpty(userReportDOS)) {
            throw exception(USER_REPORT_EXISTS);
        }
        //设置汇报类型，当天为正常0，往期为补交1
        userReport.setCommitTime(LocalDateTime.now());
        if (userReport.getDateReport().equals(userReport.getCommitTime().toLocalDate())) {
            userReport.setType("0");
        } else {
            userReport.setType("1");
        }
        userReport.setDeptName(deptMapper.selectById(userReport.getDeptId()).getName());
        // 插入母表数据
        userReportMapper.insert(userReport);
        // 处理子表数据
        List<ReportJobScheduleDO> reportJobScheduleDOList = createReqVO.getReportJobScheduleDOList();
        List<ReportJobPlanDO> reportJobPlanDOList = createReqVO.getReportJobPlanDOList();
        if (CollectionUtil.isNotEmpty(reportJobScheduleDOList)) {
            reportJobScheduleDOList.stream().forEach(p -> {
                p.setUserReportId(userReport.getId());
            });
            // 处理进度表数据
            reportJobScheduleMapper.insertBatch(reportJobScheduleDOList);
        }
        if (CollectionUtil.isNotEmpty(reportJobPlanDOList)) {
            reportJobPlanDOList.stream().forEach(p -> {
                p.setUserReportId(userReport.getId());
            });
            // 处理计划表数据
            reportJobPlanMapper.insertBatch(reportJobPlanDOList);
        }
        // 返回
        return userReport.getId();
    }

    @Override
    @Transactional
    public void updateUserReport(UserReportSaveReqVO updateReqVO) {
        // 校验存在
        validateUserReportExists(updateReqVO.getId());
        UserReportDO updateObj = BeanUtils.toBean(updateReqVO, UserReportDO.class);
        //根据汇报日期校验当天是否已经提交过汇报
//        List<UserReportDO> userReportDOS = userReportMapper.selectList(new QueryWrapper<UserReportDO>().lambda()
//                .eq(UserReportDO::getDateReport, updateReqVO.getDateReport())
//                .eq(UserReportDO::getDeptId, updateObj.getDeptId()));
        UserReportDO userReportDOS = userReportMapper.selectOne(new QueryWrapper<UserReportDO>().lambda()
                .eq(UserReportDO::getDateReport, updateReqVO.getDateReport())
                .eq(UserReportDO::getDeptId, updateObj.getDeptId()));
        if (userReportDOS != null && updateReqVO.getId() != userReportDOS.getId()) {
            throw exception(USER_REPORT_EXISTS);
        }
        //设置汇报类型，当天为正常0，往期为补交1
        updateObj.setCommitTime(LocalDateTime.now());
        if (updateObj.getDateReport().equals(updateObj.getCommitTime().toLocalDate())) {
            updateObj.setType("0");
        } else {
            updateObj.setType("1");
        }

        //更新对应的工作进度和计划
        reportJobScheduleMapper.delete(new QueryWrapper<ReportJobScheduleDO>().lambda().eq(ReportJobScheduleDO::getUserReportId, updateObj.getId()));
        reportJobPlanMapper.delete(new QueryWrapper<ReportJobPlanDO>().lambda().eq(ReportJobPlanDO::getUserReportId, updateObj.getId()));
        // 处理子表数据
        List<ReportJobScheduleDO> reportJobScheduleDOList = updateObj.getReportJobScheduleDOList();
        List<ReportJobPlanDO> reportJobPlanDOList = updateObj.getReportJobPlanDOList();
        if (CollectionUtil.isNotEmpty(reportJobScheduleDOList)) {
            reportJobScheduleDOList.stream().forEach(p -> {
                p.setUserReportId(updateObj.getId());
                p.setId(null);
            });
            // 处理进度表数据
            reportJobScheduleMapper.insertBatch(reportJobScheduleDOList);
        }
        if (CollectionUtil.isNotEmpty(reportJobPlanDOList)) {
            reportJobPlanDOList.stream().forEach(p -> {
                p.setUserReportId(updateObj.getId());
                p.setId(null);
            });
            // 处理计划表数据
            reportJobPlanMapper.insertBatch(reportJobPlanDOList);
        }
        // 更新
        userReportMapper.updateById(updateObj);
    }

    @Override
    public void deleteUserReport(Long id) {
        // 校验存在
        validateUserReportExists(id);
        // 删除
        userReportMapper.deleteById(id);
    }

    private void validateUserReportExists(Long id) {
        if (userReportMapper.selectById(id) == null) {
            throw exception(USER_REPORT_NOT_EXISTS);
        }
    }

    @Override
    public UserReportDO getUserReport(Long id) {
        UserReportDO userReportDO = userReportMapper.selectById(id);
        userReportDO.setReportJobScheduleDOList(reportJobScheduleMapper.selectList(new QueryWrapper<ReportJobScheduleDO>()
                .lambda().eq(ReportJobScheduleDO::getUserReportId, id)));
        userReportDO.setReportJobPlanDOList(reportJobPlanMapper.selectList(new QueryWrapper<ReportJobPlanDO>()
                .lambda().eq(ReportJobPlanDO::getUserReportId, id)));
        return userReportDO;
    }

    @Override
    public PageResult<UserReportDO> getUserReportPage(UserReportPageReqVO pageReqVO) {
        return userReportMapper.selectPage(pageReqVO);
    }

    @Override
    public List<StatisticsRespVO> statistics(StatisticsReqVO reqVO) {
        reqVO.setUserId(String.valueOf(getLoginUserId()));
        //先查询汇报给当前人的
        List<StatisticsRespVO> list = userReportMapper.statistics(reqVO);
        //如果未查到汇报给自己的，显示自己的汇报数据
        if (CollectionUtils.isAnyEmpty(list)) {
            list = userReportMapper.statisticsSelf(reqVO);
        }
        return list;
    }

    @Override
    public SummaryRespVO summary(StatisticsReqVO reqVO) {
        reqVO.setOffset((reqVO.getPageNo() - 1) * reqVO.getPageSize());
        SummaryRespVO result = new SummaryRespVO();
        reqVO.setUserId(String.valueOf(getLoginUserId()));
        //1.查询出漏交人员姓名
        result.setNotSubmitUserNameList(userReportMapper.queryNotSubmitUser(reqVO));
        //2.查询汇报记录
        Integer count = userReportMapper.selectCount1(reqVO);
        PageResult<UserSummaryReportDO> page = new PageResult<>();
        page.setTotal(Long.valueOf(count));
        page.setList(userReportMapper.querySummaryReport(reqVO));
        result.setReportList(page);
        return result;
    }

    @Override
    public List<AttentionAlertRespVO> queryAttentionList() {
        return userReportMapper.queryAttentionList(getLoginUserId());
    }


}