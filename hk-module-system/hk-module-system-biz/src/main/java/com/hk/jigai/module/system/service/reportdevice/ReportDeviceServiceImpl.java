package com.hk.jigai.module.system.service.reportdevice;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.module.system.controller.admin.reportdevice.vo.ReportDevicePageReqVO;
import com.hk.jigai.module.system.controller.admin.reportdevice.vo.ReportDeviceSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.dict.DictDataDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceTypeDO;
import com.hk.jigai.module.system.dal.dataobject.reportdevice.ReportDeviceDO;
import com.hk.jigai.module.system.dal.mysql.operation.OperationDeviceMapper;
import com.hk.jigai.module.system.dal.mysql.operation.OperationDeviceTypeMapper;
import com.hk.jigai.module.system.dal.mysql.operation.OperationQuestionTypeMapper;
import com.hk.jigai.module.system.dal.mysql.reportdevice.ReportDeviceMapper;
import com.hk.jigai.module.system.service.dict.DictDataService;
import com.hk.jigai.module.system.service.operation.OperationDeviceTypeService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.REPORT_DEVICE_NOT_EXISTS;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.REPORT_PERSON_ORDER_HAD_EXISTS;

/**
 * 设备资产报 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class ReportDeviceServiceImpl implements ReportDeviceService {

    @Resource
    private ReportDeviceMapper reportDeviceMapper;

    @Resource
    private OperationDeviceMapper operationDeviceMapper;

    @Resource
    private OperationDeviceTypeMapper operationDeviceTypeMapper;

    @Resource
    private DictDataService dictDataService;

    @Transactional
    @Override
    public List<ReportDeviceDO> generateReport(String month) {
        //校验报表是否存在
        Long count = reportDeviceMapper.selectCount(new QueryWrapper<ReportDeviceDO>().lambda().eq(ReportDeviceDO::getReportMonth, month));
        if (count > 0) {
            throw exception(REPORT_PERSON_ORDER_HAD_EXISTS);
        }
        //获取公司集合
        List<DictDataDO> companies = dictDataService.getCompany();
        //设备类型集合
        List<OperationDeviceTypeDO> operationDeviceTypeDOS = operationDeviceTypeMapper.selectList();
        List<ReportDeviceDO> reportDeviceDOS = new ArrayList<>();
        //遍历公司分别获取设备数据
        if (CollectionUtil.isNotEmpty(companies)) {
            for (DictDataDO company : companies) {
                //根据公司获取对应得设备
                List<OperationDeviceDO> companyDeviceList = operationDeviceMapper.selectList(new QueryWrapper<OperationDeviceDO>().lambda().eq(OperationDeviceDO::getCompany, company.getValue()));
                //根据设备类型获取对应得数据
                if (CollectionUtil.isNotEmpty(companyDeviceList)) {
                    for (OperationDeviceTypeDO deviceTypeDO : operationDeviceTypeDOS) {
                        ReportDeviceDO reportDeviceDO = new ReportDeviceDO();
                        List<OperationDeviceDO> typeDevices = companyDeviceList.stream().filter(device -> device.getDeviceType() == deviceTypeDO.getId()).collect(Collectors.toList());
                        //报表月份
                        reportDeviceDO.setReportMonth(month);
                        //公司Id
                        reportDeviceDO.setCompanyId(Long.valueOf(company.getValue()));
                        //公司名称
                        reportDeviceDO.setCompanyName(company.getLabel());
                        reportDeviceDO.setDeviceType(deviceTypeDO.getName());
                        //该设备类型的数量
                        reportDeviceDO.setDeviceCount(typeDevices.size());
                        //状态分布
                        long shiyong = typeDevices.stream().filter(d -> d.getStatus() == 0).count();
                        long xianzhi = typeDevices.stream().filter(d -> d.getStatus() == 1).count();
                        long baofei = typeDevices.stream().filter(d -> d.getStatus() == 2).count();
                        reportDeviceDO.setStateDistribution("使用中：" + shiyong + "，闲置：" + xianzhi + ",报废：" + baofei);
                        //影响程度分布
                        long low = typeDevices.stream().filter(d -> "0".equals(d.getEffectLevel())).count();
                        long mid = typeDevices.stream().filter(d -> "1".equals(d.getEffectLevel())).count();
                        long hig = typeDevices.stream().filter(d -> "2".equals(d.getEffectLevel())).count();
                        reportDeviceDO.setEffectDistribution("低：" + low + "，中：" + mid + ",高：" + hig);
                        //部门分布,暂不处理
//                        String deptStr = handleDeptDeviceCount(typeDevices);
                        reportDeviceDOS.add(reportDeviceDO);
                    }
                }
            }
        }
        List<ReportDeviceDO> re = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(reportDeviceDOS)) {
            for (ReportDeviceDO reportDeviceDO : reportDeviceDOS) {
                if (reportDeviceDO.getDeviceCount() > 0) {
                    re.add(reportDeviceDO);
                }
            }
            reportDeviceMapper.insertBatch(re);
        }
        return re;
    }

    @Transactional
    public String handleDeptDeviceCount(List<OperationDeviceDO> typeDevices) {
        return "";
    }

    @Override
    public Long createReportDevice(ReportDeviceSaveReqVO createReqVO) {
        // 插入
        ReportDeviceDO reportDevice = BeanUtils.toBean(createReqVO, ReportDeviceDO.class);
        reportDeviceMapper.insert(reportDevice);
        // 返回
        return reportDevice.getId();
    }

    @Override
    public void updateReportDevice(ReportDeviceSaveReqVO updateReqVO) {
        // 校验存在
        validateReportDeviceExists(updateReqVO.getId());
        // 更新
        ReportDeviceDO updateObj = BeanUtils.toBean(updateReqVO, ReportDeviceDO.class);
        reportDeviceMapper.updateById(updateObj);
    }

    @Override
    public void deleteReportDevice(Long id) {
        // 校验存在
        validateReportDeviceExists(id);
        // 删除
        reportDeviceMapper.deleteById(id);
    }

    private void validateReportDeviceExists(Long id) {
        if (reportDeviceMapper.selectById(id) == null) {
            throw exception(REPORT_DEVICE_NOT_EXISTS);
        }
    }

    @Override
    public ReportDeviceDO getReportDevice(Long id) {
        return reportDeviceMapper.selectById(id);
    }

    @Override
    public PageResult<ReportDeviceDO> getReportDevicePage(ReportDevicePageReqVO pageReqVO) {
        return reportDeviceMapper.selectPage(pageReqVO);
    }


}