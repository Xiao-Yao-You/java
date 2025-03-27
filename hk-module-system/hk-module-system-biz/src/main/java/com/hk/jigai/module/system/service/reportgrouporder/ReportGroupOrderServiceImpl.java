package com.hk.jigai.module.system.service.reportgrouporder;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;
import com.hk.jigai.module.system.controller.admin.reportgrouporder.vo.ReportGroupOrderPageReqVO;
import com.hk.jigai.module.system.controller.admin.reportgrouporder.vo.ReportGroupOrderSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.dict.DictDataDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationQuestionTypeDO;
import com.hk.jigai.module.system.dal.dataobject.operationgroup.OperationGroupDO;
import com.hk.jigai.module.system.dal.dataobject.reportgrouporder.ReportGroupOrderDO;
import com.hk.jigai.module.system.dal.dataobject.reportgrouporderdetail.ReportGroupOrderDetailDO;
import com.hk.jigai.module.system.dal.mysql.operation.OperationOrderMapper;
import com.hk.jigai.module.system.dal.mysql.operation.OperationQuestionTypeMapper;
import com.hk.jigai.module.system.dal.mysql.operationgroup.OperationGroupMapper;
import com.hk.jigai.module.system.dal.mysql.reportgrouporder.ReportGroupOrderMapper;
import com.hk.jigai.module.system.dal.mysql.reportgrouporderdetail.ReportGroupOrderDetailMapper;
import com.hk.jigai.module.system.service.dict.DictDataService;
import com.hk.jigai.module.system.util.operate.OperateConstant;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.REPORT_GROUP_ORDER_NOT_EXISTS;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.REPORT_PERSON_ORDER_HAD_EXISTS;

/**
 * 小组工单处理月报 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class ReportGroupOrderServiceImpl implements ReportGroupOrderService {

    @Resource
    private ReportGroupOrderMapper reportGroupOrderMapper;

    @Resource
    private OperationOrderMapper operationOrderMapper;

    @Resource
    private OperationGroupMapper operationGroupMapper;

    @Resource
    private DictDataService dictDataService;

    @Resource
    private OperationQuestionTypeMapper operationQuestionTypeMapper;

    @Resource
    private ReportGroupOrderDetailMapper reportGroupOrderDetailMapper;

    @Override
    public Long createReportGroupOrder(ReportGroupOrderSaveReqVO createReqVO) {
        // 插入
        ReportGroupOrderDO reportGroupOrder = BeanUtils.toBean(createReqVO, ReportGroupOrderDO.class);
        reportGroupOrderMapper.insert(reportGroupOrder);
        // 返回
        return reportGroupOrder.getId();
    }

    @Override
    public void updateReportGroupOrder(ReportGroupOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateReportGroupOrderExists(updateReqVO.getId());
        // 更新
        ReportGroupOrderDO updateObj = BeanUtils.toBean(updateReqVO, ReportGroupOrderDO.class);
        reportGroupOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteReportGroupOrder(Long id) {
        // 校验存在
        validateReportGroupOrderExists(id);
        // 删除
        reportGroupOrderMapper.deleteById(id);
    }

    private void validateReportGroupOrderExists(Long id) {
        if (reportGroupOrderMapper.selectById(id) == null) {
            throw exception(REPORT_GROUP_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public ReportGroupOrderDO getReportGroupOrder(Long id) {
        return reportGroupOrderMapper.selectById(id);
    }

    @Override
    public PageResult<ReportGroupOrderDO> getReportGroupOrderPage(ReportGroupOrderPageReqVO pageReqVO) {
        return reportGroupOrderMapper.selectPage(pageReqVO);
    }

    @Transactional
    @Override
    public List<ReportGroupOrderDO> generateReport(String month) {

        Long count = reportGroupOrderMapper.selectCount(new QueryWrapper<ReportGroupOrderDO>().lambda().eq(ReportGroupOrderDO::getReportMonth, month));
        if (count > 0) {
            throw exception(REPORT_PERSON_ORDER_HAD_EXISTS);
        }

        List<ReportGroupOrderDO> groupOrderDOList = new ArrayList<>();
        List<ReportGroupOrderDetailDO> groupOrderDetailDOList = new ArrayList<>();

        //查询所有问题类型，放在循环外面处理，减少复杂度
        //先获取根节点问题类型
        Map<String, List<Long>> questionMap = new HashMap<>();
        List<OperationQuestionTypeDO> questionTypeDOS = operationQuestionTypeMapper.selectList(new QueryWrapper<OperationQuestionTypeDO>().lambda().eq(OperationQuestionTypeDO::getParentId, 0));
        for (OperationQuestionTypeDO questionTypeDO : questionTypeDOS) {
            List<Long> questionIds = new ArrayList<>();
            List<OperationQuestionTypeDO> childQuestion = operationQuestionTypeMapper.selectList(new QueryWrapper<OperationQuestionTypeDO>().lambda().eq(OperationQuestionTypeDO::getParentId, questionTypeDO.getId()));
            if (CollectionUtil.isNotEmpty(childQuestion))
                questionIds = childQuestion.stream().map(q -> q.getId()).collect(Collectors.toList());
            questionMap.put(questionTypeDO.getName(), questionIds);
        }

        //获取分组名称
        List<DictDataDO> groupDict = dictDataService.getGroupList();
        //1、根据设备所在地获取在指定公司的运维工单
        //保存用日期格式
        SimpleDateFormat monthFormatter = new SimpleDateFormat("yyyy-MM");
        Date queryStartDate = new Date();
        Date queryEndDate = new Date();
        try {
            Date startOfMonth = monthFormatter.parse(month);
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
        //2.1、查询所有分组
        List<OperationGroupDO> groupList = operationGroupMapper.selectList();
        for (OperationGroupDO group : groupList) {
            //2.2、查询所有当月符合条件和组员信息的订单数据
            //获取同一组的数据
            List<OperationOrderDO> groupOrderList = operationOrderMapper.selectList(new QueryWrapper<OperationOrderDO>()
                    .lambda()
                    .in(OperationOrderDO::getDealUserId, group.getUserIds() == null ? Collections.emptyList() : group.getUserIds())
                    .between(BaseDO::getCreateTime, queryStartDate, queryEndDate));
            //新建一个小组总数据
            ReportGroupOrderDO reportGroupOrderDO = new ReportGroupOrderDO();
            ReportGroupOrderDetailDO reportGroupOrderDetailDO = new ReportGroupOrderDetailDO();
            String groupName = "";
            if (CollectionUtil.isNotEmpty(groupDict)) {
                List<DictDataDO> gs = groupDict.stream().filter(g -> g.getValue().equals(group.getGroupId())).collect(Collectors.toList());
                if (CollectionUtil.isNotEmpty(gs)) {
                    groupName = gs.get(0).getLabel();
                    //小组Id
                    reportGroupOrderDO.setGroupId(Long.valueOf(group.getGroupId()));
                    //小组名称
                    reportGroupOrderDO.setGroupName(groupName);
                }
            }
            //设置月份
            reportGroupOrderDO.setReportMonth(month);
            reportGroupOrderDetailDO.setReportMonth(month);
            //工单数量
            int totalCount = groupOrderList.size();
            reportGroupOrderDO.setOrderCount(totalCount);
            if (CollectionUtil.isNotEmpty(groupOrderList)) {
                //处理数据
                List<ReportGroupOrderDO> groupOrderDOs = handleGroupData(reportGroupOrderDO, groupOrderList, queryStartDate, monthFormatter, group);
                groupOrderDOList.addAll(groupOrderDOs);
                //处理数据详情
                List<ReportGroupOrderDetailDO> groupOrderDetailDOs = handleGroupDetailData(groupOrderList, queryStartDate, monthFormatter, group, questionMap, month, groupName);
                groupOrderDetailDOList.addAll(groupOrderDetailDOs);
            }
        }
        //数据存储
        if (CollectionUtil.isNotEmpty(groupOrderDOList)) {
            reportGroupOrderMapper.insertBatch(groupOrderDOList);
        }
        if (CollectionUtil.isNotEmpty(groupOrderDetailDOList)) {
            List<ReportGroupOrderDetailDO> re = new ArrayList<>();
            for (ReportGroupOrderDetailDO reportGroupOrderDetailDO : groupOrderDetailDOList) {
                if (reportGroupOrderDetailDO.getOrderCount() != null && reportGroupOrderDetailDO.getOrderCount() > 0) {
                    re.add(reportGroupOrderDetailDO);
                }
            }
            reportGroupOrderDetailMapper.insertBatch(re);
        }
        return groupOrderDOList;
    }

    //分组数据详情
    public List<ReportGroupOrderDetailDO> handleGroupDetailData(List<OperationOrderDO> groupOrderList,
                                                                Date queryStartDate, SimpleDateFormat monthFormatter, OperationGroupDO group,
                                                                Map<String, List<Long>> questionMap, String month, String groupName) {
        List<ReportGroupOrderDetailDO> reportGroupOrderDetailDOS = new ArrayList<>();
        Iterator<Map.Entry<String, List<Long>>> it = questionMap.entrySet().iterator();
        while (it.hasNext()) {
            ReportGroupOrderDetailDO reportGroupOrderDetailDO = new ReportGroupOrderDetailDO();
            reportGroupOrderDetailDO.setGroupId(Long.valueOf(group.getGroupId()));
            reportGroupOrderDetailDO.setGroupName(groupName);
            reportGroupOrderDetailDO.setReportMonth(month);
            Map.Entry<String, List<Long>> entry = it.next();
            //设置问题类型Name
            reportGroupOrderDetailDO.setQuestionTypeName(entry.getKey());
            List<Long> childQuestionIds = entry.getValue();
            //筛选符合问题类型的工单
            List<OperationOrderDO> questionOrderList = new ArrayList<>();
            for (OperationOrderDO operationOrderDO : groupOrderList) {
                if (childQuestionIds.contains(operationOrderDO.getQuestionType())) {
                    questionOrderList.add(operationOrderDO);
                    groupOrderList.remove(operationOrderDO);
                }
            }
            if (CollectionUtil.isNotEmpty(questionOrderList)) {
                reportGroupOrderDetailDO.setOrderCount(questionOrderList.size());
                //已完成的工单数量
                long completeCount = questionOrderList.stream().filter(o -> OperateConstant.COMPLETE_STATUS.equals(o.getStatus())).count();
                reportGroupOrderDetailDO.setCompleteOrderCount((int) completeCount);
                //待处理or进行中的工单数量
                long processingCount = questionOrderList.stream().filter(o -> OperateConstant.WAIT_DEAL_STATUS.equals(o.getStatus()) || OperateConstant.IN_GOING_STATUS.equals(o.getStatus())).count();
                reportGroupOrderDetailDO.setProcessingOrderCount((int) processingCount);
                //挂起的工单数量
                long pendingCount = questionOrderList.stream().filter(o -> OperateConstant.HANG_UP_STATUS.equals(o.getStatus())).count();
                reportGroupOrderDetailDO.setPendingOrderCount((int) pendingCount);
                //处置总时长
                long totalHandleTime = questionOrderList.stream().mapToLong(time -> time.getDealConsume() == null ? 0L : time.getDealConsume()).sum();
                reportGroupOrderDetailDO.setTotalHandleTime(totalHandleTime);
                //平均处置时长
                long aht = completeCount > 0 ? totalHandleTime / completeCount : 0L;
                reportGroupOrderDetailDO.setAht(aht);
                //挂起总时长
                long totalPendingTime = questionOrderList.stream().mapToLong(time -> time.getHangUpConsume() == null ? 0L : time.getHangUpConsume()).sum();
                reportGroupOrderDetailDO.setPendingTotalTime(totalPendingTime);
                //平均挂起时长
                long apt = pendingCount > 0 ? totalPendingTime / pendingCount : 0L;
                reportGroupOrderDetailDO.setApt(apt);
                //紧急程度工单数量分布
                long lowCount = questionOrderList.stream().filter(o -> "0".equals(o.getLevel())).count();
                long midCount = questionOrderList.stream().filter(o -> "1".equals(o.getLevel())).count();
                long higCount = questionOrderList.stream().filter(o -> "2".equals(o.getLevel())).count();
                reportGroupOrderDetailDO.setUrgencyLevelDistribution("低:" + lowCount + "，中:" + midCount + "，高:" + higCount);
                //工单接单类型数量分布
                long disCount = questionOrderList.stream().filter(o -> 0 == o.getType()).count();
                long actCount = questionOrderList.stream().filter(o -> 1 == o.getType()).count();
                long pasCount = questionOrderList.stream().filter(o -> 2 == o.getType()).count();
                reportGroupOrderDetailDO.setOrderTypeDistribution("派:" + disCount + "，抢:" + actCount + "，转:" + pasCount);
                //主动接单率
                BigDecimal totalDecimal = BigDecimal.valueOf(questionOrderList.size());
                BigDecimal actDecimal = BigDecimal.valueOf(actCount);
                // 执行除法并保留两位小数（四舍五入）
                if (!totalDecimal.equals(BigDecimal.ZERO)) {
                    BigDecimal orderAcceptedProportion = actDecimal.divide(totalDecimal, 2, RoundingMode.HALF_UP);
                    reportGroupOrderDetailDO.setOrderAcceptedProportion(orderAcceptedProportion);
                } else {
                    reportGroupOrderDetailDO.setOrderAcceptedProportion(BigDecimal.ZERO);
                }
                //按时完成率
                //按时完成数量，即个人处置时长在1h以内，1*60*60*1000
                long otCount = questionOrderList.stream().filter(o -> OperateConstant.COMPLETE_STATUS.equals(o.getStatus()) && o.getDealConsume() <= 3600000).count();
                BigDecimal completeDecimal = BigDecimal.valueOf(completeCount);
                BigDecimal otDecimal = BigDecimal.valueOf(otCount);
                // 执行除法并保留两位小数（四舍五入）
                if (!completeDecimal.equals(BigDecimal.ZERO)) {
                    BigDecimal onTimeCompletionRate = otDecimal.divide(completeDecimal, 2, RoundingMode.HALF_UP);
                    reportGroupOrderDetailDO.setOnTimeCompletionRate(onTimeCompletionRate);
                } else {
                    reportGroupOrderDetailDO.setOnTimeCompletionRate(BigDecimal.ZERO);
                }
                //环比增长量
                //获取上个月
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(queryStartDate);
                // 减去一个月（注意月份从0开始，但此处直接操作无需手动调整）
                calendar.add(Calendar.MONTH, -1);
                Date lastDate = calendar.getTime();
                String lastDateStr = monthFormatter.format(lastDate);
                ReportGroupOrderDetailDO lastMonthReport = reportGroupOrderDetailMapper.selectOne(new QueryWrapper<ReportGroupOrderDetailDO>().lambda().eq(ReportGroupOrderDetailDO::getGroupId, group.getGroupId()).eq(ReportGroupOrderDetailDO::getReportMonth, lastDateStr));
                if (lastMonthReport != null) {
                    //环比增长量
                    long growth = completeCount - lastMonthReport.getCompleteOrderCount();
                    reportGroupOrderDetailDO.setMonthOnMonthGrowth((int) growth);
                    //环比增长率
                    if (lastMonthReport.getCompleteOrderCount() != 0) {
                        BigDecimal growthDecimal = BigDecimal.valueOf(growth);
                        BigDecimal lastDecimal = BigDecimal.valueOf(completeCount);
                        // 执行除法并保留两位小数（四舍五入）
                        if (!lastDecimal.equals(BigDecimal.ZERO)) {
                            BigDecimal growthRate = growthDecimal.divide(lastDecimal, 2, RoundingMode.HALF_UP);
                            reportGroupOrderDetailDO.setMonthOnMonthGrowthRate(growthRate);
                        } else {
                            reportGroupOrderDetailDO.setMonthOnMonthGrowthRate(BigDecimal.ZERO);
                        }
                    } else {
                        reportGroupOrderDetailDO.setMonthOnMonthGrowthRate(BigDecimal.ZERO);
                    }
                } else {
                    //没有上个月数据的时候增长量即为全部完成量
                    long growth = completeCount;
                    reportGroupOrderDetailDO.setMonthOnMonthGrowth((int) growth);
                    reportGroupOrderDetailDO.setMonthOnMonthGrowthRate(BigDecimal.ZERO);
                }
            }
            reportGroupOrderDetailDOS.add(reportGroupOrderDetailDO);

        }
        if (CollectionUtil.isNotEmpty(groupOrderList)) {
            ReportGroupOrderDetailDO r = new ReportGroupOrderDetailDO();
            r.setOrderCount(groupOrderList.size());
            r.setGroupId(Long.valueOf(group.getGroupId()));
            r.setGroupName(groupName);
            r.setReportMonth(month);
            r.setQuestionTypeName("未知问题");
            reportGroupOrderDetailDOS.add(r);
        }
        return reportGroupOrderDetailDOS;
    }

    //分组数据
    public List<ReportGroupOrderDO> handleGroupData(ReportGroupOrderDO reportGroupOrderDO, List<OperationOrderDO> groupOrderList,
                                                    Date queryStartDate, SimpleDateFormat monthFormatter, OperationGroupDO group) {

        List<ReportGroupOrderDO> groupOrderDOList = new ArrayList<>();

        //已完成的工单数量
        long completeCount = groupOrderList.stream().filter(o -> OperateConstant.COMPLETE_STATUS.equals(o.getStatus())).count();
        reportGroupOrderDO.setCompleteOrderCount((int) completeCount);
        //待处理or进行中的工单数量
        long processingCount = groupOrderList.stream().filter(o -> OperateConstant.WAIT_DEAL_STATUS.equals(o.getStatus()) || OperateConstant.IN_GOING_STATUS.equals(o.getStatus())).count();
        reportGroupOrderDO.setProcessingOrderCount((int) processingCount);
        //挂起的工单数量
        long pendingCount = groupOrderList.stream().filter(o -> OperateConstant.HANG_UP_STATUS.equals(o.getStatus())).count();
        reportGroupOrderDO.setPendingOrderCount((int) pendingCount);
        //处置总时长
        long totalHandleTime = groupOrderList.stream().mapToLong(time -> time.getDealConsume() == null ? 0L : time.getDealConsume()).sum();
        reportGroupOrderDO.setTotalHandleTime(totalHandleTime);
        //平均处置时长
        long aht = completeCount > 0 ? totalHandleTime / completeCount : 0L;
        reportGroupOrderDO.setAht(aht);
        //挂起总时长
        long totalPendingTime = groupOrderList.stream().mapToLong(time -> time.getHangUpConsume() == null ? 0L : time.getHangUpConsume()).sum();
        reportGroupOrderDO.setPendingTotalTime(totalPendingTime);
        //平均挂起时长
        long apt = pendingCount > 0 ? totalPendingTime / pendingCount : 0L;
        reportGroupOrderDO.setApt(apt);
        //紧急程度工单数量分布
        long lowCount = groupOrderList.stream().filter(o -> "0".equals(o.getLevel())).count();
        long midCount = groupOrderList.stream().filter(o -> "1".equals(o.getLevel())).count();
        long higCount = groupOrderList.stream().filter(o -> "2".equals(o.getLevel())).count();
        reportGroupOrderDO.setUrgencyLevelDistribution("低:" + lowCount + "，中:" + midCount + "，高:" + higCount);
        //工单接单类型数量分布
        long disCount = groupOrderList.stream().filter(o -> 0 == o.getType()).count();
        long actCount = groupOrderList.stream().filter(o -> 1 == o.getType()).count();
        long pasCount = groupOrderList.stream().filter(o -> 2 == o.getType()).count();
        reportGroupOrderDO.setOrderTypeDistribution("派:" + disCount + "，抢:" + actCount + "，转:" + pasCount);
        //主动接单率
        BigDecimal totalDecimal = BigDecimal.valueOf(groupOrderList.size());
        BigDecimal actDecimal = BigDecimal.valueOf(actCount);
        // 执行除法并保留两位小数（四舍五入）
        if (!totalDecimal.equals(BigDecimal.ZERO)) {
            BigDecimal orderAcceptedProportion = actDecimal.divide(totalDecimal, 2, RoundingMode.HALF_UP);
            reportGroupOrderDO.setOrderAcceptedProportion(orderAcceptedProportion);
        } else {
            reportGroupOrderDO.setOrderAcceptedProportion(BigDecimal.ZERO);
        }
        //按时完成率
        //按时完成数量，即个人处置时长在1h以内，1*60*60*1000
        long otCount = groupOrderList.stream().filter(o -> OperateConstant.COMPLETE_STATUS.equals(o.getStatus()) && o.getDealConsume() <= 3600000).count();
        BigDecimal completeDecimal = BigDecimal.valueOf(completeCount);
        BigDecimal otDecimal = BigDecimal.valueOf(otCount);
        // 执行除法并保留两位小数（四舍五入）
        if (!completeDecimal.equals(BigDecimal.ZERO)) {
            BigDecimal onTimeCompletionRate = otDecimal.divide(completeDecimal, 2, RoundingMode.HALF_UP);
            reportGroupOrderDO.setOnTimeCompletionRate(onTimeCompletionRate);
        } else {
            reportGroupOrderDO.setOnTimeCompletionRate(BigDecimal.ZERO);
        }
        //环比增长量
        //获取上个月
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(queryStartDate);
        // 减去一个月（注意月份从0开始，但此处直接操作无需手动调整）
        calendar.add(Calendar.MONTH, -1);
        Date lastDate = calendar.getTime();
        String lastDateStr = monthFormatter.format(lastDate);
        ReportGroupOrderDO lastMonthReport = reportGroupOrderMapper.selectOne(new QueryWrapper<ReportGroupOrderDO>().lambda().eq(ReportGroupOrderDO::getGroupId, group.getGroupId()).eq(ReportGroupOrderDO::getReportMonth, lastDateStr));
        if (lastMonthReport != null) {
            //环比增长量
            long growth = completeCount - lastMonthReport.getCompleteOrderCount();
            reportGroupOrderDO.setMonthOnMonthGrowth((int) growth);
            //环比增长率
            if (lastMonthReport.getCompleteOrderCount() != 0) {
                BigDecimal growthDecimal = BigDecimal.valueOf(growth);
                BigDecimal lastDecimal = BigDecimal.valueOf(completeCount);
                // 执行除法并保留两位小数（四舍五入）
                if (!lastDecimal.equals(BigDecimal.ZERO)) {
                    BigDecimal growthRate = growthDecimal.divide(lastDecimal, 2, RoundingMode.HALF_UP);
                    reportGroupOrderDO.setMonthOnMonthGrowthRate(growthRate);
                } else {
                    reportGroupOrderDO.setMonthOnMonthGrowthRate(BigDecimal.ZERO);
                }
            } else {
                reportGroupOrderDO.setMonthOnMonthGrowthRate(BigDecimal.ZERO);
            }
        } else {
            //没有上个月数据的时候增长量即为全部完成量
            long growth = completeCount;
            reportGroupOrderDO.setMonthOnMonthGrowth((int) growth);
            reportGroupOrderDO.setMonthOnMonthGrowthRate(BigDecimal.ZERO);
        }
        groupOrderDOList.add(reportGroupOrderDO);

        return groupOrderDOList;
    }

}