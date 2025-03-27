package com.hk.jigai.module.system.service.reportgrouporder;

import java.util.*;
import javax.validation.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.module.system.controller.admin.reportgrouporder.vo.GroupReportDetailResultVO;
import com.hk.jigai.module.system.controller.admin.reportgrouporder.vo.ReportGroupOrderPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportgrouporder.vo.ReportGroupOrderSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportgrouporder.ReportGroupOrderDO;

/**
 * 小组工单处理月报 Service 接口
 *
 * @author 邵志伟
 */
public interface ReportGroupOrderService {

    /**
     * 创建小组工单处理月报
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReportGroupOrder(@Valid ReportGroupOrderSaveReqVO createReqVO);

    /**
     * 更新小组工单处理月报
     *
     * @param updateReqVO 更新信息
     */
    void updateReportGroupOrder(@Valid ReportGroupOrderSaveReqVO updateReqVO);

    /**
     * 删除小组工单处理月报
     *
     * @param id 编号
     */
    void deleteReportGroupOrder(Long id);

    /**
     * 获得小组工单处理月报
     *
     * @param id 编号
     * @return 小组工单处理月报
     */
    ReportGroupOrderDO getReportGroupOrder(Long id);

    /**
     * 获得小组工单处理月报分页
     *
     * @param pageReqVO 分页查询
     * @return 小组工单处理月报分页
     */
    PageResult<ReportGroupOrderDO> getReportGroupOrderPage(ReportGroupOrderPageReqVO pageReqVO);

    /**
     * 生成小组月度报表
     *
     * @param month
     * @return
     */
    List<ReportGroupOrderDO> generateReport(String month);
}