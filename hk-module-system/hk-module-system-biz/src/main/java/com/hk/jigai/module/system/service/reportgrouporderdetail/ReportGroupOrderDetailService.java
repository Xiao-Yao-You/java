package com.hk.jigai.module.system.service.reportgrouporderdetail;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.reportgrouporderdetail.vo.ReportGroupOrderDetailPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportgrouporderdetail.vo.ReportGroupOrderDetailSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportgrouporderdetail.ReportGroupOrderDetailDO;

/**
 * 小组工单处理月报详情 Service 接口
 *
 * @author 邵志伟
 */
public interface ReportGroupOrderDetailService {

    /**
     * 创建小组工单处理月报详情
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReportGroupOrderDetail(@Valid ReportGroupOrderDetailSaveReqVO createReqVO);

    /**
     * 更新小组工单处理月报详情
     *
     * @param updateReqVO 更新信息
     */
    void updateReportGroupOrderDetail(@Valid ReportGroupOrderDetailSaveReqVO updateReqVO);

    /**
     * 删除小组工单处理月报详情
     *
     * @param id 编号
     */
    void deleteReportGroupOrderDetail(Long id);

    /**
     * 获得小组工单处理月报详情
     *
     * @param id 编号
     * @return 小组工单处理月报详情
     */
    ReportGroupOrderDetailDO getReportGroupOrderDetail(Long id);

    /**
     * 获得小组工单处理月报详情分页
     *
     * @param pageReqVO 分页查询
     * @return 小组工单处理月报详情分页
     */
    PageResult<ReportGroupOrderDetailDO> getReportGroupOrderDetailPage(ReportGroupOrderDetailPageReqVO pageReqVO);

}