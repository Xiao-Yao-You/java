package com.hk.jigai.module.system.service.reportlist;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.module.system.controller.admin.reportlist.vo.ReportListPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportlist.vo.ReportListSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportlist.ReportListDO;
import com.hk.jigai.module.system.dal.mysql.reportlist.ReportListMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.REPORT_LIST_NOT_EXISTS;


/**
 * 月报表列 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class ReportListServiceImpl implements ReportListService {

    @Resource
    private ReportListMapper reportListMapper;

    @Override
    public Long createReportList(ReportListSaveReqVO createReqVO) {
        // 插入
        ReportListDO reportList = BeanUtils.toBean(createReqVO, ReportListDO.class);
        reportListMapper.insert(reportList);
        // 返回
        return reportList.getId();
    }

    @Override
    public void updateReportList(ReportListSaveReqVO updateReqVO) {
        // 校验存在
        validateReportListExists(updateReqVO.getId());
        // 更新
        ReportListDO updateObj = BeanUtils.toBean(updateReqVO, ReportListDO.class);
        reportListMapper.updateById(updateObj);
    }

    @Override
    public void deleteReportList(Long id) {
        // 校验存在
        validateReportListExists(id);
        // 删除
        reportListMapper.deleteById(id);
    }

    private void validateReportListExists(Long id) {
        if (reportListMapper.selectById(id) == null) {
            throw exception(REPORT_LIST_NOT_EXISTS);
        }
    }

    @Override
    public ReportListDO getReportList(Long id) {
        return reportListMapper.selectById(id);
    }

    @Override
    public PageResult<ReportListDO> getReportListPage(ReportListPageReqVO pageReqVO) {
        return reportListMapper.selectPage(pageReqVO);
    }

}