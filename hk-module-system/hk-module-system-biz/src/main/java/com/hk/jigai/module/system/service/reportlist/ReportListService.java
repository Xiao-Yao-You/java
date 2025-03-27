package com.hk.jigai.module.system.service.reportlist;

import java.util.*;
import javax.validation.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.reportlist.vo.ReportListPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportlist.vo.ReportListSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportlist.ReportListDO;

/**
 * 月报表列 Service 接口
 *
 * @author 邵志伟
 */
public interface ReportListService {

    /**
     * 创建月报表列
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReportList(@Valid ReportListSaveReqVO createReqVO);

    /**
     * 更新月报表列
     *
     * @param updateReqVO 更新信息
     */
    void updateReportList(@Valid ReportListSaveReqVO updateReqVO);

    /**
     * 删除月报表列
     *
     * @param id 编号
     */
    void deleteReportList(Long id);

    /**
     * 获得月报表列
     *
     * @param id 编号
     * @return 月报表列
     */
    ReportListDO getReportList(Long id);

    /**
     * 获得月报表列分页
     *
     * @param pageReqVO 分页查询
     * @return 月报表列分页
     */
    PageResult<ReportListDO> getReportListPage(ReportListPageReqVO pageReqVO);

}