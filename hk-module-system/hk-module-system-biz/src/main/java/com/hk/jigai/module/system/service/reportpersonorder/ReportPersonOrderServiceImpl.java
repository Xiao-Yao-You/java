package com.hk.jigai.module.system.service.reportpersonorder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;
import com.hk.jigai.module.system.controller.admin.reportpersonorder.vo.ReportPersonOrderPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportpersonorder.vo.ReportPersonOrderSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderDO;
import com.hk.jigai.module.system.dal.dataobject.operationgroup.OperationGroupDO;
import com.hk.jigai.module.system.dal.dataobject.reportpersonorder.ReportPersonOrderDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.dal.mysql.operation.OperationOrderMapper;
import com.hk.jigai.module.system.dal.mysql.operationgroup.OperationGroupMapper;
import com.hk.jigai.module.system.dal.mysql.reportpersonorder.ReportPersonOrderMapper;
import com.hk.jigai.module.system.dal.mysql.user.AdminUserMapper;
import com.hk.jigai.module.system.util.operate.OperateConstant;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.REPORT_PERSON_ORDER_HAD_EXISTS;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.REPORT_PERSON_ORDER_NOT_EXISTS;

/**
 * 个人工单处理月报 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class ReportPersonOrderServiceImpl implements ReportPersonOrderService {

    @Resource
    private ReportPersonOrderMapper reportPersonOrderMapper;

    @Resource
    private OperationOrderMapper operationOrderMapper;

    @Resource
    private OperationGroupMapper operationGroupMapper;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Long createReportPersonOrder(ReportPersonOrderSaveReqVO createReqVO) {
        // 插入
        ReportPersonOrderDO reportPersonOrder = BeanUtils.toBean(createReqVO, ReportPersonOrderDO.class);
        reportPersonOrderMapper.insert(reportPersonOrder);
        // 返回
        return reportPersonOrder.getId();
    }

    @Override
    public void updateReportPersonOrder(ReportPersonOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateReportPersonOrderExists(updateReqVO.getId());
        // 更新
        ReportPersonOrderDO updateObj = BeanUtils.toBean(updateReqVO, ReportPersonOrderDO.class);
        reportPersonOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteReportPersonOrder(Long id) {
        // 校验存在
        validateReportPersonOrderExists(id);
        // 删除
        reportPersonOrderMapper.deleteById(id);
    }

    private void validateReportPersonOrderExists(Long id) {
        if (reportPersonOrderMapper.selectById(id) == null) {
            throw exception(REPORT_PERSON_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public ReportPersonOrderDO getReportPersonOrder(Long id) {
        return reportPersonOrderMapper.selectById(id);
    }

    @Override
    public PageResult<ReportPersonOrderDO> getReportPersonOrderPage(ReportPersonOrderPageReqVO pageReqVO) {
        return reportPersonOrderMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ReportPersonOrderDO> generateReport(String month) {

        Long reportCount = reportPersonOrderMapper.selectCount(new QueryWrapper<ReportPersonOrderDO>()
                .lambda()
                .eq(ReportPersonOrderDO::getReportMonth, month));
        //先校验是否生成过报表，如果生成过，则抛出异常
        if (reportCount > 0) {
            throw exception(REPORT_PERSON_ORDER_HAD_EXISTS);
        }
        //0、定义返回数组
        List<ReportPersonOrderDO> reportPersonOrderDOList = new ArrayList<>();
        //1、根据month字符串解析成日期区间，需要处理解析异常问题
        //month -->格式>>yyyy-MM
        //保存用日期格式
        SimpleDateFormat monthFormatter = new SimpleDateFormat("yyyy-MM");
        //保存用日期格式
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date queryStartDate = new Date();
        Date queryEndDate = new Date();
        try {
            Date startOfMonth = startOfMonth = monthFormatter.parse(month);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startOfMonth);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            queryStartDate = calendar.getTime();
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.SECOND, -1); // 设置为当月最后一天 23:59:59
            queryEndDate = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //2.1、查询所有当月符合条件的订单数据
        List<OperationOrderDO> orderList = operationOrderMapper.selectList(new QueryWrapper<OperationOrderDO>()
                .lambda()
                .between(BaseDO::getCreateTime, queryStartDate, queryEndDate));
        //2.2、查询所有运维班组的人员
        List<OperationGroupDO> groupList = operationGroupMapper.selectList();
        if (CollectionUtils.isNotEmpty(groupList)) {
            //2.3整合群组用户数据
            List<Long> personIds = groupList.stream()
                    .filter(g -> g.getUserIds() != null) // 过滤空集合
                    .flatMap(g -> g.getUserIds().stream())
                    .distinct()
                    .collect(Collectors.toList());
            //3、处理业务数据，这是个大工程，尽量去优化代码
            if (CollectionUtils.isNotEmpty(orderList)) {
                for (Long personId : personIds) {
                    //3.0、创建一条新的新纪录,每个员工有一个新的报告数据
                    ReportPersonOrderDO reportPersonOrderDO = new ReportPersonOrderDO();
                    //3.1、根据运维人员用户id从工单列表中筛选符合条件工单数据
                    List<OperationOrderDO> personOrders = orderList.stream()
                            .filter(order -> order.getDealUserId() != null && order.getDealUserId().equals(personId))
                            .collect(Collectors.toList());
                    //4、整合数据
                    //总工单数量
                    long totalCount = personOrders.size();
                    reportPersonOrderDO.setTotalOrderCount((int) totalCount);
                    //个人信息
                    AdminUserDO adminUserDO = adminUserMapper.selectById(personId);
                    //用户id
                    reportPersonOrderDO.setUserId(personId);
                    //运维人员名字
                    if (adminUserDO != null) {
                        reportPersonOrderDO.setUsername(adminUserDO.getNickname());
                    }
                    //报表月份
                    reportPersonOrderDO.setReportMonth(month);
                    //个人总工单数量不为空的情况
                    if (CollectionUtils.isNotEmpty(personOrders)) {
                        //已完成的工单数量
                        long completeCount = personOrders.stream().filter(o -> o.getStatus() == OperateConstant.COMPLETE_STATUS).count();
                        reportPersonOrderDO.setCompleteOrderCount((int) completeCount);
                        //待处理or进行中的工单数量
                        long processingCount = personOrders.stream().filter(o -> o.getStatus() == OperateConstant.WAIT_DEAL_STATUS || o.getStatus() == OperateConstant.IN_GOING_STATUS).count();
                        reportPersonOrderDO.setProcessingOrderCount((int) processingCount);
                        //挂起的工单数量
                        long pendingCount = personOrders.stream().filter(o -> o.getStatus() == OperateConstant.HANG_UP_STATUS).count();
                        reportPersonOrderDO.setPendingOrderCount((int) pendingCount);
                        //处置总时长
                        long totalHandleTime = personOrders.stream().mapToLong(time -> time.getDealConsume() == null ? 0L : time.getDealConsume()).sum();
                        reportPersonOrderDO.setTotalHandleTime(totalHandleTime);
                        //平均处置时长
                        long aht = completeCount > 0 ? totalHandleTime / completeCount : 0L;
                        reportPersonOrderDO.setAht(aht);
                        //挂起总时长
                        long totalPendingTime = personOrders.stream().mapToLong(time -> time.getHangUpConsume() == null ? 0L : time.getHangUpConsume()).sum();
                        reportPersonOrderDO.setPendingTotalTime(totalPendingTime);
                        //平均挂起时长
                        long apt = pendingCount > 0 ? totalPendingTime / pendingCount : 0L;
                        reportPersonOrderDO.setApt(apt);
                        //紧急程度工单数量分布
                        long lowCount = personOrders.stream().filter(o -> "0".equals(o.getLevel())).count();
                        long midCount = personOrders.stream().filter(o -> "1".equals(o.getLevel())).count();
                        long higCount = personOrders.stream().filter(o -> "2".equals(o.getLevel())).count();
                        reportPersonOrderDO.setUrgencyLevelDistribution("低:" + lowCount + "，中:" + midCount + "，高:" + higCount);
                        //工单接单类型数量分布
                        long disCount = personOrders.stream().filter(o -> 0 == o.getType()).count();
                        long actCount = personOrders.stream().filter(o -> 1 == o.getType()).count();
                        long pasCount = personOrders.stream().filter(o -> 2 == o.getType()).count();
                        reportPersonOrderDO.setOrderTypeDistribution("派:" + disCount + "，抢:" + actCount + "，转:" + pasCount);
                        //主动接单率
                        BigDecimal totalDecimal = BigDecimal.valueOf(totalCount);
                        BigDecimal actDecimal = BigDecimal.valueOf(actCount);
                        // 执行除法并保留两位小数（四舍五入）
                        if (!totalDecimal.equals(BigDecimal.ZERO)) {
                            BigDecimal orderAcceptedProportion = actDecimal.divide(totalDecimal, 2, RoundingMode.HALF_UP);
                            reportPersonOrderDO.setOrderAcceptedProportion(orderAcceptedProportion);
                        } else {
                            reportPersonOrderDO.setOrderAcceptedProportion(BigDecimal.ZERO);
                        }
                        //按时完成率
                        //按时完成数量，即个人处置时长在1h以内，1*60*60*1000
                        long otCount = personOrders.stream().filter(o -> o.getStatus() == OperateConstant.COMPLETE_STATUS && o.getDealConsume() <= 3600000).count();
                        BigDecimal completeDecimal = BigDecimal.valueOf(completeCount);
                        BigDecimal otDecimal = BigDecimal.valueOf(otCount);
                        // 执行除法并保留两位小数（四舍五入）
                        if (!completeDecimal.equals(BigDecimal.ZERO)) {
                            BigDecimal onTimeCompletionRate = otDecimal.divide(completeDecimal, 2, RoundingMode.HALF_UP);
                            reportPersonOrderDO.setOnTimeCompletionRate(onTimeCompletionRate);
                        } else {
                            reportPersonOrderDO.setOnTimeCompletionRate(BigDecimal.ZERO);
                        }
                        //环比增长量
                        //获取上个月
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(queryStartDate);
                        // 减去一个月（注意月份从0开始，但此处直接操作无需手动调整）
                        calendar.add(Calendar.MONTH, -1);
                        Date lastDate = calendar.getTime();
                        String lastDateStr = monthFormatter.format(lastDate);
                        ReportPersonOrderDO lastMonthReport = reportPersonOrderMapper.selectOne(new QueryWrapper<ReportPersonOrderDO>().lambda().eq(ReportPersonOrderDO::getUserId, personId).eq(ReportPersonOrderDO::getReportMonth, lastDateStr));
                        if (lastMonthReport != null) {
                            //环比增长量
                            long growth = completeCount - lastMonthReport.getCompleteOrderCount();
                            reportPersonOrderDO.setMonthOnMonthGrowth((int) growth);
                            //环比增长率
                            if (lastMonthReport.getCompleteOrderCount() != 0) {
                                BigDecimal growthDecimal = BigDecimal.valueOf(growth);
                                BigDecimal lastDecimal = BigDecimal.valueOf(completeCount);
                                // 执行除法并保留两位小数（四舍五入）
                                if (!lastDecimal.equals(BigDecimal.ZERO)) {
                                    BigDecimal growthRate = growthDecimal.divide(lastDecimal, 2, RoundingMode.HALF_UP);
                                    reportPersonOrderDO.setMonthOnMonthGrowthRate(growthRate);
                                } else {
                                    reportPersonOrderDO.setMonthOnMonthGrowthRate(BigDecimal.ZERO);
                                }
                            } else {
                                reportPersonOrderDO.setMonthOnMonthGrowthRate(BigDecimal.ZERO);
                            }
                        } else {
                            //没有上个月数据的时候增长量即为全部完成量
                            long growth = completeCount;
                            reportPersonOrderDO.setMonthOnMonthGrowth((int) growth);
                            reportPersonOrderDO.setMonthOnMonthGrowthRate(BigDecimal.ZERO);
                        }
                        //单日最大处理量
                        // 按日期分组统计次数
                        Map<String, Long> dateCountMap = personOrders.stream()
                                .filter(r -> r.getCreateTime() != null) // 过滤空时间
                                .collect(Collectors.groupingBy(
                                        r -> r.getCreateTime().format(DATE_FORMATTER), // 格式化 LocalDateTime 为字符串
                                        Collectors.counting()
                                ));

                        Optional<Map.Entry<String, Long>> maxEntry = dateCountMap.entrySet().stream()
                                .max(Map.Entry.comparingByValue());
                        maxEntry.ifPresent(entry ->
                                reportPersonOrderDO.setDailyHandleMax(entry.getKey() + ":" + entry.getValue())
                        );
                        // 默认值处理
                        if (!maxEntry.isPresent()) {
                            reportPersonOrderDO.setDailyHandleMax("无数据");
                        }
                    }
                    reportPersonOrderDOList.add(reportPersonOrderDO);
                }

            }
        }
        if (!reportPersonOrderDOList.isEmpty()) {
            reportPersonOrderMapper.insertBatch(reportPersonOrderDOList);
        }
        //F、返回整合好的数据
        return reportPersonOrderDOList;
    }

}