package com.hk.jigai.module.system.service.userreport;

import java.util.*;
import javax.validation.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.userreport.vo.*;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportJobScheduleDO;
import com.hk.jigai.module.system.dal.dataobject.userreport.UserReportDO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户汇报 Service 接口
 *
 * @author 超级管理员
 */
public interface UserReportService {

    /**
     * 创建用户汇报
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUserReport(@Valid UserReportSaveReqVO createReqVO);

    /**
     * 更新用户汇报
     *
     * @param updateReqVO 更新信息
     */
    void updateUserReport(@Valid UserReportSaveReqVO updateReqVO);

    /**
     * 删除用户汇报
     *
     * @param id 编号
     */
    void deleteUserReport(Long id);

    /**
     * 获得用户汇报
     *
     * @param id 编号
     * @return 用户汇报
     */
    UserReportDO getUserReport(Long id);

    /**
     * 获得用户汇报分页
     *
     * @param pageReqVO 分页查询
     * @return 用户汇报分页
     */
    PageResult<UserReportDO> getUserReportPage(UserReportPageReqVO pageReqVO);

    /**
     * 汇报统计
     * @param reqVO
     * @return
     */
    List<StatisticsRespVO> statistics(@Valid StatisticsReqVO reqVO);

    /**
     * 汇报汇总
     * @param reqVO
     * @return
     */
    SummaryRespVO summary(StatisticsReqVO reqVO);

    /**
     * 关注下拉
     * @return
     */
    List<AttentionAlertRespVO> queryAttentionList();

    /**
     * 查询schdule详情
     * @param id
     * @return
     */
    ReportJobScheduleDO getScheduleInfo(Long id);

}