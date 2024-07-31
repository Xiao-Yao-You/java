package com.hk.jigai.module.system.service.userreport;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.module.system.controller.admin.userreport.vo.UserReportPageReqVO;
import com.hk.jigai.module.system.controller.admin.userreport.vo.UserReportSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportjobplan.ReportJobPlanDO;
import com.hk.jigai.module.system.dal.dataobject.reportjobschedule.ReportJobScheduleDO;
import com.hk.jigai.module.system.dal.dataobject.userreport.UserReportDO;
import com.hk.jigai.module.system.dal.mysql.dept.DeptMapper;
import com.hk.jigai.module.system.dal.mysql.reportjobplan.ReportJobPlanMapper;
import com.hk.jigai.module.system.dal.mysql.reportjobschedule.ReportJobScheduleMapper;
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
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.USER_REPORT_EXISTS;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.USER_REPORT_NOT_EXISTS;

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

    @Override
    @Transactional
    public Long createUserReport(UserReportSaveReqVO createReqVO) {
        UserReportDO userReport = BeanUtils.toBean(createReqVO, UserReportDO.class);
        //根据汇报日期校验当天是否已经提交过汇报
        List<UserReportDO> userReportDOS = userReportMapper.selectList(new QueryWrapper<UserReportDO>().lambda().eq(UserReportDO::getDateReport, createReqVO.getDateReport()));
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
        // 更新
        UserReportDO updateObj = BeanUtils.toBean(updateReqVO, UserReportDO.class);
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


}