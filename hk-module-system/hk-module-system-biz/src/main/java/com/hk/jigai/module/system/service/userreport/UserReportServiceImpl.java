package com.hk.jigai.module.system.service.userreport;

import com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportJobPlanDO;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportJobScheduleDO;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportObjectDO;
import com.hk.jigai.module.system.dal.mysql.userreport.ReportJobPlanMapper;
import com.hk.jigai.module.system.dal.mysql.userreport.ReportJobScheduleMapper;
import com.hk.jigai.module.system.dal.mysql.userreport.ReportObjectMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

import com.hk.jigai.module.system.controller.admin.userreport.vo.*;
import com.hk.jigai.module.system.dal.dataobject.userreport.UserReportDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.userreport.UserReportMapper;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
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
    private ReportObjectMapper reportObjectMapper;

    @Resource
    private ReportJobPlanMapper reportJobPlanMapper;

    @Resource
    private ReportJobScheduleMapper reportJobScheduleMapper;

    @Transactional
    @Override
    public Long createUserReport(UserReportSaveReqVO createReqVO) {
        UserReportDO userReport = BeanUtils.toBean(createReqVO, UserReportDO.class);
        userReport.setUserId(SecurityFrameworkUtils.getLoginUser().getId());
        //1.判断当前用户，今日某个部门的汇报只有一条数据
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId",SecurityFrameworkUtils.getLoginUser().getId());
        requestMap.put("deptId",createReqVO.getDeptId());
        requestMap.put("dateReport",createReqVO.getDateReport());
        Long count = userReportMapper.selectCount(requestMap);
        if(count.compareTo(new Long(1))>0){
            throw exception(USER_REPORT_ALREADY_COMMIT);
        }
        //2.凌晨跑批，已经创建未提交记录,先查询这条未提交记录id,然后更新或者插入
        UserReportDO emptyDO = userReportMapper.selectOne(UserReportDO::getUserId,SecurityFrameworkUtils.getLoginUser().getId(),
                UserReportDO::getDeptId,createReqVO.getDeptId(),UserReportDO::getDateReport,createReqVO.getDateReport());
        int id;
        if(emptyDO!=null){
            id = emptyDO.getId().intValue();
            userReport.setId(emptyDO.getId());
            userReportMapper.updateById(userReport);
        }else{
            id = userReportMapper.insert(userReport);
        }
        //3.插入汇报对象表
        List<ReportObjectDO> reportObjectList = createReqVO.getReportObjectList();
        for(ReportObjectDO reportObjectDO: reportObjectList){
            reportObjectDO.setUserReportId(new Long(id));
        }
        reportObjectMapper.insertBatch(reportObjectList);
        //4.插入工作进度表
        List<ReportJobScheduleDO> scheduleList = createReqVO.getScheduleList();
        for(ReportJobScheduleDO scheduleDO : scheduleList){
            scheduleDO.setUserReportId(new Long(id));
        }
        reportJobScheduleMapper.insertBatch(scheduleList);
        //5.插入工作计划表
        List<ReportJobPlanDO> planList = createReqVO.getPlanList();
        for(ReportJobPlanDO jobPlanDO : planList){
            jobPlanDO.setUserReportId(new Long(id));
        }
        reportJobPlanMapper.insertBatch(planList);
        return userReport.getId();
    }

    @Transactional
    @Override
    public void updateUserReport(UserReportSaveReqVO updateReqVO) {
        // 校验存在
        validateUserReportExists(updateReqVO.getId());
        // 更新
        UserReportDO updateObj = BeanUtils.toBean(updateReqVO, UserReportDO.class);
        updateObj.setUserId(SecurityFrameworkUtils.getLoginUser().getId());
        userReportMapper.updateById(updateObj);
        //汇报对象表，先删后插
        reportObjectMapper.delete(ReportObjectDO::getUserReportId,updateObj.getId());
        List<ReportObjectDO> reportObjectList = updateReqVO.getReportObjectList();
        for(ReportObjectDO reportObjectDO: reportObjectList){
            reportObjectDO.setUserReportId(updateObj.getId());
        }
        reportObjectMapper.insertBatch(reportObjectList);
        //工作进度，先删后插
        reportJobPlanMapper.delete(ReportJobPlanDO::getUserReportId,updateObj.getId());
        List<ReportJobPlanDO> planList = updateReqVO.getPlanList();
        for(ReportJobPlanDO jobPlanDO : planList){
            jobPlanDO.setUserReportId(updateObj.getId());
        }
        reportJobPlanMapper.insertBatch(planList);
        //工作计划，先删后插
        reportJobScheduleMapper.delete(ReportJobScheduleDO::getUserReportId,updateObj.getId());
        List<ReportJobScheduleDO> scheduleList = updateReqVO.getScheduleList();
        for(ReportJobScheduleDO scheduleDO : scheduleList){
            scheduleDO.setUserReportId(updateObj.getId());
        }
        reportJobScheduleMapper.insertBatch(scheduleList);
    }

    @Override
    public void deleteUserReport(Long id) {
        // 校验存在
        validateUserReportExists(id);
        // 删除
        userReportMapper.deleteById(id);
        reportObjectMapper.delete(ReportObjectDO::getUserReportId,id);
        reportJobScheduleMapper.delete(ReportJobScheduleDO::getUserReportId,id);
        reportJobPlanMapper.delete(ReportJobPlanDO::getUserReportId,id);
    }

    private void validateUserReportExists(Long id) {
        if (userReportMapper.selectById(id) == null) {
            throw exception(USER_REPORT_NOT_EXISTS);
        }
    }

    @Override
    public UserReportSaveReqVO getUserReport(Long id) {
        validateUserReportExists(id);
        //先查看汇报表
        UserReportDO userReportDO =  userReportMapper.selectById(id);
        UserReportSaveReqVO result = BeanUtils.toBean(userReportDO, UserReportSaveReqVO.class);
        //查看汇报人
        List<ReportObjectDO> reportObjectList = reportObjectMapper.selectList(ReportObjectDO::getUserReportId,id);
        result.setReportObjectList(reportObjectList);
        //查看工作进度
        List<ReportJobScheduleDO> scheduleList = reportJobScheduleMapper.selectList(ReportJobScheduleDO::getUserReportId,id);
        result.setScheduleList(scheduleList);
        //查看工作计划
        List<ReportJobPlanDO> planList = reportJobPlanMapper.selectList(ReportJobPlanDO::getUserReportId,id);
        result.setPlanList(planList);
        return result;
    }

    @Override
    public PageResult<UserReportDO> getUserReportPage(UserReportPageReqVO pageReqVO) {
        //查看自己的汇报记录 + 汇报对象是自己的
        return userReportMapper.selectPage(pageReqVO);
    }

}