package com.hk.jigai.module.system.service.userreport;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.module.system.controller.admin.userreport.vo.*;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportObjectDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;

/**
 * 汇报对象 Service 接口
 *
 * @author 超级管理员
 */
public interface ReportObjectService {

    /**
     * 创建汇报对象
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    void createReportObject(@Valid List<ReportObjectSaveReqVO> createReqVO);

    /**
     * 获得汇报对象分页
     *
     * @param pageReqVO 分页查询
     * @return 汇报对象分页
     */
    List<ReportObjectDO> getReportObjectPage(ReportObjectPageReqVO pageReqVO);

}