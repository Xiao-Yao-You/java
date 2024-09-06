package com.hk.jigai.module.system.service.userreport;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.query.QueryWrapperX;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportTransferRecordDO;
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
import static com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserNickname;
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
        reportObjectMapper.delete(new QueryWrapper<ReportObjectDO>().lambda()
                .eq(ReportObjectDO::getUserId, getLoginUserId()));
        // 插入
        List<ReportObjectDO> reportObject = new ArrayList<>();
        for(ReportObjectSaveReqVO reportObjectSaveReqVO : createReqVO){
            reportObjectSaveReqVO.setUserId(getLoginUserId());
            reportObjectSaveReqVO.setUserNickName(getLoginUserNickname());
            reportObject.add(BeanUtils.toBean(reportObjectSaveReqVO, ReportObjectDO.class));
        }
        reportObjectMapper.insertBatch(reportObject);
    }

    @Override
    public List<ReportObjectDO> getReportObjectPage(ReportObjectPageReqVO pageReqVO) {
        return reportObjectMapper.selectList(new QueryWrapper<ReportObjectDO>().lambda()
                .eq(ReportObjectDO::getUserId, getLoginUserId()));
    }

}