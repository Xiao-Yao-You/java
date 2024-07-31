package com.hk.jigai.module.system.service.reportjobschedule;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.reportjobschedule.vo.ReportJobSchedulePageReqVO;
import com.hk.jigai.module.system.controller.admin.reportjobschedule.vo.ReportJobScheduleSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportjobschedule.ReportJobScheduleDO;

/**
 * 汇报工作进度 Service 接口
 *
 * @author 超级管理员
 */
public interface ReportJobScheduleService {

    /**
     * 创建汇报工作进度
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReportJobSchedule(@Valid ReportJobScheduleSaveReqVO createReqVO);

    /**
     * 更新汇报工作进度
     *
     * @param updateReqVO 更新信息
     */
    void updateReportJobSchedule(@Valid ReportJobScheduleSaveReqVO updateReqVO);

    /**
     * 删除汇报工作进度
     *
     * @param id 编号
     */
    void deleteReportJobSchedule(Long id);

    /**
     * 获得汇报工作进度
     *
     * @param id 编号
     * @return 汇报工作进度
     */
    ReportJobScheduleDO getReportJobSchedule(Long id);

    /**
     * 获得汇报工作进度分页
     *
     * @param pageReqVO 分页查询
     * @return 汇报工作进度分页
     */
    PageResult<ReportJobScheduleDO> getReportJobSchedulePage(ReportJobSchedulePageReqVO pageReqVO);

}