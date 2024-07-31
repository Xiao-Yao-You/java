package com.hk.jigai.module.system.service.reportjobschedule;

import com.hk.jigai.module.system.controller.admin.reportjobschedule.vo.ReportJobSchedulePageReqVO;
import com.hk.jigai.module.system.controller.admin.reportjobschedule.vo.ReportJobScheduleSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportjobschedule.ReportJobScheduleDO;
import com.hk.jigai.module.system.dal.mysql.reportjobschedule.ReportJobScheduleMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.REPORT_JOB_SCHEDULE_NOT_EXISTS;

/**
 * 汇报工作进度 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class ReportJobScheduleServiceImpl implements ReportJobScheduleService {

    @Resource
    private ReportJobScheduleMapper reportJobScheduleMapper;

    @Override
    public Long createReportJobSchedule(ReportJobScheduleSaveReqVO createReqVO) {
        // 插入
        ReportJobScheduleDO reportJobSchedule = BeanUtils.toBean(createReqVO, ReportJobScheduleDO.class);
        reportJobScheduleMapper.insert(reportJobSchedule);
        // 返回
        return reportJobSchedule.getId();
    }

    @Override
    public void updateReportJobSchedule(ReportJobScheduleSaveReqVO updateReqVO) {
        // 校验存在
        validateReportJobScheduleExists(updateReqVO.getId());
        // 更新
        ReportJobScheduleDO updateObj = BeanUtils.toBean(updateReqVO, ReportJobScheduleDO.class);
        reportJobScheduleMapper.updateById(updateObj);
    }

    @Override
    public void deleteReportJobSchedule(Long id) {
        // 校验存在
        validateReportJobScheduleExists(id);
        // 删除
        reportJobScheduleMapper.deleteById(id);
    }

    private void validateReportJobScheduleExists(Long id) {
        if (reportJobScheduleMapper.selectById(id) == null) {
            throw exception(REPORT_JOB_SCHEDULE_NOT_EXISTS);
        }
    }

    @Override
    public ReportJobScheduleDO getReportJobSchedule(Long id) {
        return reportJobScheduleMapper.selectById(id);
    }

    @Override
    public PageResult<ReportJobScheduleDO> getReportJobSchedulePage(ReportJobSchedulePageReqVO pageReqVO) {
        return reportJobScheduleMapper.selectPage(pageReqVO);
    }

}