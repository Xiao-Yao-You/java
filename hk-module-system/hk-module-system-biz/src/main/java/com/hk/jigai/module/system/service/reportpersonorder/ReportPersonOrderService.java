package com.hk.jigai.module.system.service.reportpersonorder;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.module.system.controller.admin.reportpersonorder.vo.ReportPersonOrderPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportpersonorder.vo.ReportPersonOrderSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportpersonorder.ReportPersonOrderDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 个人工单处理月报 Service 接口
 *
 * @author 邵志伟
 */
public interface ReportPersonOrderService {

    /**
     * 创建个人工单处理月报
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReportPersonOrder(@Valid ReportPersonOrderSaveReqVO createReqVO);

    /**
     * 更新个人工单处理月报
     *
     * @param updateReqVO 更新信息
     */
    void updateReportPersonOrder(@Valid ReportPersonOrderSaveReqVO updateReqVO);

    /**
     * 删除个人工单处理月报
     *
     * @param id 编号
     */
    void deleteReportPersonOrder(Long id);

    /**
     * 获得个人工单处理月报
     *
     * @param id 编号
     * @return 个人工单处理月报
     */
    ReportPersonOrderDO getReportPersonOrder(Long id);

    /**
     * 获得个人工单处理月报分页
     *
     * @param pageReqVO 分页查询
     * @return 个人工单处理月报分页
     */
    PageResult<ReportPersonOrderDO> getReportPersonOrderPage(ReportPersonOrderPageReqVO pageReqVO);

    /**
     * 生成个人工单月报表
     *
     * @param month
     * @return
     */
    List<ReportPersonOrderDO> generateReport(String month);
}