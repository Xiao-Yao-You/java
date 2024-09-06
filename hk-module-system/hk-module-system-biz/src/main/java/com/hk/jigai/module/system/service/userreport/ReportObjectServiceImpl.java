package com.hk.jigai.module.system.service.userreport;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.hk.jigai.module.system.controller.admin.userreport.vo.*;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportObjectDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.userreport.ReportObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 汇报对象 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class ReportObjectServiceImpl implements ReportObjectService {

    @Resource
    private ReportObjectMapper reportObjectMapper;

    @Override
    public void createReportObject(List<ReportObjectSaveReqVO> createReqVO) {
        // 插入
        List<ReportObjectDO> reportObject = new ArrayList<>();
        for(ReportObjectSaveReqVO reportObjectSaveReqVO : createReqVO){
            reportObject.add(BeanUtils.toBean(reportObjectSaveReqVO, ReportObjectDO.class));
        }
        reportObjectMapper.insertBatch(reportObject);
    }

    @Override
    public void updateReportObject(ReportObjectSaveReqVO updateReqVO) {
        // 校验存在
        validateReportObjectExists(updateReqVO.getId());
        // 更新
        ReportObjectDO updateObj = BeanUtils.toBean(updateReqVO, ReportObjectDO.class);
        reportObjectMapper.updateById(updateObj);
    }

    @Override
    public void deleteReportObject(Long id) {
        // 校验存在
        validateReportObjectExists(id);
        // 删除
        reportObjectMapper.deleteById(id);
    }

    private void validateReportObjectExists(Long id) {
        if (reportObjectMapper.selectById(id) == null) {
            throw exception(REPORT_OBJECT_NOT_EXISTS);
        }
    }

    @Override
    public ReportObjectDO getReportObject(Long id) {
        return reportObjectMapper.selectById(id);
    }

    @Override
    public PageResult<ReportObjectDO> getReportObjectPage(ReportObjectPageReqVO pageReqVO) {
        return reportObjectMapper.selectPage(pageReqVO);
    }

}