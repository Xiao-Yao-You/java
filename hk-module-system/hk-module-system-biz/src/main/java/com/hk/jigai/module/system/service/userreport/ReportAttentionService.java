package com.hk.jigai.module.system.service.userreport;

import java.util.*;
import javax.validation.*;

import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.module.system.controller.admin.userreport.vo.*;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportAttentionDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportTransferRecordDO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 汇报关注跟进 Service 接口
 *
 * @author 超级管理员
 */
public interface ReportAttentionService {


    /**
     * 更新汇报关注跟进
     *
     * @param updateReqVO 更新信息
     */
    void updateReportAttention(@Valid ReportAttentionSaveReqVO updateReqVO);

    /**
     * 删除汇报关注跟进
     *
     * @param id 编号
     */
    void deleteReportAttention(Long id);

    /**
     * 获得汇报关注跟进
     *
     * @param id 编号
     * @return 汇报关注跟进
     */
    ReportAttentionDO getReportAttention(Long id);

    /**
     * 获得汇报关注跟进分页
     *
     * @param pageReqVO 分页查询
     * @return 汇报关注跟进分页
     */
    PageResult<ReportAttentionDO> getReportAttentionPage(ReportAttentionPageReqVO pageReqVO);

    /**
     * 查询当前登录人未跟进的跟进列表
     * @return
     */
    List<ReportAttentionDO> queryFollowUndo(Long id);

    /**
     * 关注操作
     * @param request
     * @return
     */
    Long createAttention(CreateAttentionReqVO request);

    /**
     * 转交
     * @param updateReqVO
     */
    void transfer(ReportAttentionTransferReqVO updateReqVO);

    /**
     * 跟进
     * @param updateReqVO
     * @return
     */
    Long follow(ReportAttentionFollowReqVO updateReqVO);

    /**
     * 查询关注的转交记录
     * @param id
     * @return
     */
    List<ReportTransferRecordDO> queryTransferList(Long id);

}