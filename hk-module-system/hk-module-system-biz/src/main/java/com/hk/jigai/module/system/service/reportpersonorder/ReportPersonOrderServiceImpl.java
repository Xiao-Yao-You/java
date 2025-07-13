package com.hk.jigai.module.system.service.reportpersonorder;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;
import com.hk.jigai.module.system.controller.admin.reportpersonorder.vo.ReportPersonOrderPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportpersonorder.vo.ReportPersonOrderSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderDO;
import com.hk.jigai.module.system.dal.dataobject.operationgroup.OperationGroupDO;
import com.hk.jigai.module.system.dal.dataobject.reportlist.ReportListDO;
import com.hk.jigai.module.system.dal.dataobject.reportpersonorder.ReportPersonOrderDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.dal.mysql.operation.OperationOrderMapper;
import com.hk.jigai.module.system.dal.mysql.operationgroup.OperationGroupMapper;
import com.hk.jigai.module.system.dal.mysql.reportlist.ReportListMapper;
import com.hk.jigai.module.system.dal.mysql.reportpersonorder.ReportPersonOrderMapper;
import com.hk.jigai.module.system.dal.mysql.user.AdminUserMapper;
import com.hk.jigai.module.system.util.operate.OperateConstant;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

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

    @Resource
    private ReportListMapper reportListMapper;

    public static final String MONTH_FORMAT = "yyyy-MM";

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
    @Transactional
    public List<ReportPersonOrderDO> generateReport(String month) {
        Long reportCount = reportPersonOrderMapper.selectCount(new QueryWrapper<ReportPersonOrderDO>()
                .lambda()
                .eq(ReportPersonOrderDO::getReportMonth, month));
        //先校验是否生成过报表，如果生成过，则抛出异常
        if (reportCount > 0) {
            throw exception(REPORT_PERSON_ORDER_HAD_EXISTS);
        }
        //查询用日期格式
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 定义返回数组
        List<ReportPersonOrderDO> reportPersonOrderDOList = new ArrayList<>();
        //0 查询所有运维班组的人员
        List<OperationGroupDO> groupList = operationGroupMapper.selectList();
        // 没有机组运维人员，则不去创建报表数据，直接返回
        if(groupList.isEmpty()){
            return reportPersonOrderDOList;
        }
        //1. 获取查询月份的工单数据
        List<OperationOrderDO> orderList = getMonthReportPersonOrder(month);
        if(orderList.isEmpty()){
            // 按照先前逻辑，如果没有订单，也不会生成报表数据，直接返回
            return reportPersonOrderDOList;
        }
        //1.2 按照人员对订单进行分组
        Map<Long, List<OperationOrderDO>> groupedOrders = orderList.stream()
                .filter(order -> order.getDealUserId() != null) // 过滤掉 DealUserId 为 null 的数据
                .collect(Collectors.groupingBy(OperationOrderDO::getDealUserId));

        // 2.1 获取上一个月的工单数据
        Date date = DateUtil.parse(month, MONTH_FORMAT);
        String lastMonth = DateUtil.format(DateUtil.offsetMonth(date, -1), MONTH_FORMAT);
        List<OperationOrderDO> lastOrderList = getMonthReportPersonOrder(lastMonth);

        // 2.2 上个月的工单数据对人员进行分组
        Map<Long, List<OperationOrderDO>> lastGroupedOrders = lastOrderList.stream()
                .filter(order -> order.getDealUserId() != null) // 过滤掉 DealUserId 为 null 的数据
                .collect(Collectors.groupingBy(OperationOrderDO::getDealUserId));

        //3. 获取运维机组人员的所有id，方便后续遍历
        List<Long> personIds = groupList.stream()
                .filter(g -> g.getUserIds() != null) // 过滤空集合
                .flatMap(g -> g.getUserIds().stream())
                .distinct()
                .collect(Collectors.toList());

        //4. 获取用户信息，并将用户信息转换成 map，方便后续根据用户id 获取用户信息
        Map<Long, AdminUserDO> adminUserDOMap = adminUserMapper.selectList(new QueryWrapper<AdminUserDO>().in("id", personIds))
                .stream().collect(Collectors.toMap(AdminUserDO::getId, user -> user)); // 使用 getId() 作为 key

        //5. 处理业务数据
        if (CollectionUtils.isNotEmpty(orderList)) {
            for (Long personId : personIds) {
                //5.0 创建一条新的新纪录,每个员工有一个新的报告数据
                ReportPersonOrderDO reportPersonOrderDO = new ReportPersonOrderDO();
                // 报表月份
                reportPersonOrderDO.setReportMonth(month);
                //用户id
                reportPersonOrderDO.setUserId(personId);
                //个人信息
                AdminUserDO adminUserDO = adminUserDOMap.get(personId);
                //运维人员名字
                if (adminUserDO != null) {
                    reportPersonOrderDO.setUsername(adminUserDO.getNickname());
                }
                List<OperationOrderDO> personOrders = groupedOrders.get(personId);
                //5.1 整合数据
                //总工单数量
                long totalCount = personOrders == null ? 0L : personOrders.size();
                reportPersonOrderDO.setTotalOrderCount((int) totalCount);

                //个人总工单数量不为空的情况
                if (CollectionUtils.isNotEmpty(personOrders)) {
                    //已完成的工单数量
                    long completeCount = personOrders.stream().filter(o -> OperateConstant.COMPLETE_STATUS.equals(o.getStatus())).count();
                    reportPersonOrderDO.setCompleteOrderCount((int) completeCount);
                    //待处理or进行中的工单数量
                    long processingCount = personOrders.stream().filter(o -> OperateConstant.WAIT_DEAL_STATUS.equals(o.getStatus()) || OperateConstant.IN_GOING_STATUS.equals(o.getStatus())).count();
                    reportPersonOrderDO.setProcessingOrderCount((int) processingCount);
                    //挂起的工单数量
                    long pendingCount = personOrders.stream().filter(o -> OperateConstant.HANG_UP_STATUS.equals(o.getStatus())).count();
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
                    long otCount = personOrders.stream().filter(o -> OperateConstant.COMPLETE_STATUS.equals(o.getStatus()) && o.getDealConsume() <= 3600000).count();
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
                    List<OperationOrderDO> lastMonthReport = lastGroupedOrders.get(personId);
                    if (lastMonthReport != null) {
                        // 获取上个月该用户的历史完成订单数
                        long lastMonthCompleteOrderCount = lastMonthReport.stream()
                                .filter(o -> OperateConstant.COMPLETE_STATUS.equals(o.getStatus()))
                                .count();
                        // 环比增长量
                        long growth = completeCount - lastMonthCompleteOrderCount;
                        reportPersonOrderDO.setMonthOnMonthGrowth((int) growth);
                        // 环比增长率
                        BigDecimal growthRate = BigDecimal.ZERO;
                        if (lastMonthCompleteOrderCount > 0) {
                            BigDecimal growthDecimal = BigDecimal.valueOf(growth);
                            BigDecimal lastMonthCountDecimal = BigDecimal.valueOf(lastMonthCompleteOrderCount);
                            growthRate = growthDecimal.divide(lastMonthCountDecimal, 2, RoundingMode.HALF_UP);
                        }
                        reportPersonOrderDO.setMonthOnMonthGrowthRate(growthRate);
                    } else {
                        //没有上个月数据的时候增长量即为全部完成量
                        reportPersonOrderDO.setMonthOnMonthGrowth((int) completeCount);
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
        if (!reportPersonOrderDOList.isEmpty()) {
            reportPersonOrderMapper.insertBatch(reportPersonOrderDOList);

            //新增报表标题列表
            ReportListDO reportListDO = new ReportListDO();
            reportListDO.setReportTitle(month + "运维人员工单处理月度报表");
            reportListDO.setReportMonth(month);
            reportListMapper.insert(reportListDO);

        } else {
            throw exception(REPORT_PERSON_ORDER_NO_DATA);
        }
        //F、返回整合好的数据
        return reportPersonOrderDOList;
    }

    @Override
    public void deleteReport(String month) {
        reportPersonOrderMapper.delete(new QueryWrapper<ReportPersonOrderDO>().eq("report_month", month));
        reportListMapper.delete(new QueryWrapper<ReportListDO>().eq("report_month", month));
    }

    public List<OperationOrderDO> getMonthReportPersonOrder(String month)  {
        Date startOfMonth = DateUtil.parse(month, MONTH_FORMAT);
        Date queryStartDate = DateUtil.beginOfMonth(startOfMonth);
        Date queryEndDate = DateUtil.endOfMonth(startOfMonth);
        //2.1、查询所有当月符合条件的订单数据
        return operationOrderMapper.selectList(new QueryWrapper<OperationOrderDO>()
                .lambda()
                .between(BaseDO::getCreateTime, queryStartDate, queryEndDate));
    }

}