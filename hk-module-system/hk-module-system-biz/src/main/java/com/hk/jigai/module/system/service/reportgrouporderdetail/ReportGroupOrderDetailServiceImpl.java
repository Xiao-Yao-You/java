package com.hk.jigai.module.system.service.reportgrouporderdetail;

import com.hk.jigai.module.system.controller.admin.reportgrouporderdetail.vo.ReportGroupOrderDetailPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportgrouporderdetail.vo.ReportGroupOrderDetailSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportgrouporderdetail.ReportGroupOrderDetailDO;
import com.hk.jigai.module.system.dal.mysql.reportgrouporderdetail.ReportGroupOrderDetailMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.REPORT_GROUP_ORDER_DETAIL_NOT_EXISTS;

/**
 * 小组工单处理月报详情 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class ReportGroupOrderDetailServiceImpl implements ReportGroupOrderDetailService {

    @Resource
    private ReportGroupOrderDetailMapper reportGroupOrderDetailMapper;

    @Override
    public Long createReportGroupOrderDetail(ReportGroupOrderDetailSaveReqVO createReqVO) {
        // 插入
        ReportGroupOrderDetailDO reportGroupOrderDetail = BeanUtils.toBean(createReqVO, ReportGroupOrderDetailDO.class);
        reportGroupOrderDetailMapper.insert(reportGroupOrderDetail);
        // 返回
        return reportGroupOrderDetail.getId();
    }

    @Override
    public void updateReportGroupOrderDetail(ReportGroupOrderDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateReportGroupOrderDetailExists(updateReqVO.getId());
        // 更新
        ReportGroupOrderDetailDO updateObj = BeanUtils.toBean(updateReqVO, ReportGroupOrderDetailDO.class);
        reportGroupOrderDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteReportGroupOrderDetail(Long id) {
        // 校验存在
        validateReportGroupOrderDetailExists(id);
        // 删除
        reportGroupOrderDetailMapper.deleteById(id);
    }

    private void validateReportGroupOrderDetailExists(Long id) {
        if (reportGroupOrderDetailMapper.selectById(id) == null) {
            throw exception(REPORT_GROUP_ORDER_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public ReportGroupOrderDetailDO getReportGroupOrderDetail(Long id) {
        return reportGroupOrderDetailMapper.selectById(id);
    }

    @Override
    public PageResult<ReportGroupOrderDetailDO> getReportGroupOrderDetailPage(ReportGroupOrderDetailPageReqVO pageReqVO) {
        return reportGroupOrderDetailMapper.selectPage(pageReqVO);
    }

}