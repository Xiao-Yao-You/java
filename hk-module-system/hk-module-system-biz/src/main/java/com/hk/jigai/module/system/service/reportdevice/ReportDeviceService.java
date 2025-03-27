package com.hk.jigai.module.system.service.reportdevice;

import java.util.*;
import javax.validation.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.reportdevice.vo.ReportDevicePageReqVO;
import com.hk.jigai.module.system.controller.admin.reportdevice.vo.ReportDeviceSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reportdevice.ReportDeviceDO;

/**
 * 设备资产报 Service 接口
 *
 * @author 邵志伟
 */
public interface ReportDeviceService {

    /**
     * 创建设备资产报
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReportDevice(@Valid ReportDeviceSaveReqVO createReqVO);

    /**
     * 更新设备资产报
     *
     * @param updateReqVO 更新信息
     */
    void updateReportDevice(@Valid ReportDeviceSaveReqVO updateReqVO);

    /**
     * 删除设备资产报
     *
     * @param id 编号
     */
    void deleteReportDevice(Long id);

    /**
     * 获得设备资产报
     *
     * @param id 编号
     * @return 设备资产报
     */
    ReportDeviceDO getReportDevice(Long id);

    /**
     * 获得设备资产报分页
     *
     * @param pageReqVO 分页查询
     * @return 设备资产报分页
     */
    PageResult<ReportDeviceDO> getReportDevicePage(ReportDevicePageReqVO pageReqVO);

    /**
     * 生成设备报表
     *
     * @param month
     * @return
     */
    List<ReportDeviceDO> generateReport(String month);
}