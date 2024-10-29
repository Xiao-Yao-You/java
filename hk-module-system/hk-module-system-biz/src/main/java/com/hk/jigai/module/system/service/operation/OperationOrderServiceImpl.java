package com.hk.jigai.module.system.service.operation;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;
import com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils;
import com.hk.jigai.module.system.controller.admin.notice.NoticeController;
import com.hk.jigai.module.system.controller.admin.notice.vo.WechatNoticeVO;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderOperatePictureDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderOperateRecordDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationQuestionTypeDO;
import com.hk.jigai.module.system.dal.dataobject.operationnoticeobject.OperationNoticeObjectDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.dal.mysql.operation.OperationOrderMapper;
import com.hk.jigai.module.system.dal.mysql.operation.OperationOrderOperatePictureMapper;
import com.hk.jigai.module.system.dal.mysql.operation.OperationOrderOperateRecordMapper;
import com.hk.jigai.module.system.dal.mysql.operation.OperationQuestionTypeMapper;
import com.hk.jigai.module.system.enums.OrderOperateEnum;
import com.hk.jigai.module.system.service.notice.NoticeService;
import com.hk.jigai.module.system.service.notice.NoticeServiceImpl;
import com.hk.jigai.module.system.service.notice.WeChatSendMessageService;
import com.hk.jigai.module.system.service.operationnoticeobject.OperationNoticeObjectService;
import com.hk.jigai.module.system.service.scenecode.SceneCodeService;
import com.hk.jigai.module.system.service.user.AdminUserService;
import com.hk.jigai.module.system.util.operate.OperateConstant;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.lang.invoke.SwitchPoint;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserNickname;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 工单 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class OperationOrderServiceImpl implements OperationOrderService {

    @Resource
    private OperationOrderMapper operationOrderMapper;

    @Resource
    OperationOrderOperateRecordMapper operationOrderOperateRecordMapper;

    @Resource
    OperationOrderOperatePictureMapper operationOrderOperatePictureMapper;

    @Resource
    OperationQuestionTypeMapper operationQuestionTypeMapper;

    @Resource
    AdminUserService adminUserService;

    @Resource
    private SceneCodeService sceneCodeService;

    @Resource
    private WeChatSendMessageService weChatSendMessageService;
    @Resource
    private OperationNoticeObjectService operationNoticeObjectService;

    @Resource
    private OperationDeviceService operationDeviceService;

    /**
     * 新建工单消息模板
     */
    private final String templateId = "2pXmLdpwIns8NpfTwExqVCB1mWLOQrzsosdJHy2Yx7M";

    private final String appId = "wx49590d619c10f743";

    @Override
    @Transactional
    public Long createOperationOrder(OperationOrderSaveReqVO createReqVO) {
        // 插入
        OperationOrderDO operationOrder = BeanUtils.toBean(createReqVO, OperationOrderDO.class);
        //设置工单初始状态
        operationOrder.setStatus(OperateConstant.WAIT_ALLOCATION_STATUS);
        String code = sceneCodeService.increment("REPAIR_ORDER");
        operationOrder.setCode(code);
        AdminUserDO submitUser = adminUserService.getUser(createReqVO.getSubmitUserId());
        operationOrder.setSubmitUserNickName(submitUser == null ? "未查到此人" : submitUser.getNickname());
        operationOrderMapper.insert(operationOrder);

        // 创建工单执行记录
        OperationOrderOperateRecordDO operationOrderOperateRecordDO = new OperationOrderOperateRecordDO();
        operationOrderOperateRecordDO.setOrderId(operationOrder.getId());
        operationOrderOperateRecordDO.setOperateUserId(submitUser.getId());
        operationOrderOperateRecordDO.setOperateUserNickName(submitUser.getNickname());
        operationOrderOperateRecordDO.setBeginTime(LocalDateTime.now());
        operationOrderOperateRecordDO.setOperateType(OperateConstant.CREATE_TYPE);
        operationOrderOperateRecordDO.setUserId(submitUser.getId());
        operationOrderOperateRecordDO.setUserNickName(submitUser.getNickname());
        operationOrderOperateRecordMapper.insert(operationOrderOperateRecordDO);

        List<String> openIdList = new ArrayList<>();

        List<OperationNoticeObjectDO> allUsers = operationNoticeObjectService.getAllUsers();
        if (CollectionUtil.isNotEmpty(allUsers)) {
            List<Long> collect = allUsers.stream().map(p -> p.getUserId()).collect(Collectors.toList());
            for (Long aLong : collect) {
                AdminUserDO user = adminUserService.getUser(aLong);
                openIdList.add(user.getOpenid());
            }
//            openIdList.add("o__Px6pWasRvDQ0hVwyS0kOiVLGc");
            //发送微信公众号消息
            WechatNoticeVO wechatNoticeVO = new WechatNoticeVO();
            wechatNoticeVO.setTemplate_id(templateId); //模板Id
            Map dataMap = new HashMap<>();
            Map cs = new HashMap<>();
            cs.put("value", operationOrder.getCode());
            dataMap.put("character_string2", cs);    //工单编号
            Map t5 = new HashMap<>();
            t5.put("value", operationOrder.getSubmitUserNickName());
            dataMap.put("thing5", t5);    //报修人员
            Map t3 = new HashMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            t3.put("value", operationOrder.getCreateTime().format(formatter));
            dataMap.put("time3", t3);//报修时间
            Map p13 = new HashMap<>();
            p13.put("value", operationOrder.getSubmitUserMobile());
            dataMap.put("phone_number13", p13);//联系电话
            Map t6 = new HashMap<>();
            t6.put("value", operationOrder.getDescription());
            dataMap.put("thing6", t6);//故障描述
            wechatNoticeVO.setData(dataMap);
            wechatNoticeVO.setMiniprogram(wechatNoticeVO.createMiniProgram("appId", "/"));

            try {
                weChatSendMessageService.sendModelMessage(openIdList, wechatNoticeVO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 返回
        return operationOrder.getId();
    }

    @Override
    public void updateOperationOrder(OperationOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateOperationOrderExists(updateReqVO.getId());
        // 更新
        OperationOrderDO updateObj = BeanUtils.toBean(updateReqVO, OperationOrderDO.class);
        operationOrderMapper.updateById(updateObj);
    }

    @Override
    public void deleteOperationOrder(Long id) {
        // 校验存在
        validateOperationOrderExists(id);
        // 删除
        operationOrderMapper.deleteById(id);
    }

    private void validateOperationOrderExists(Long id) {
        if (operationOrderMapper.selectById(id) == null) {
            throw exception(OPERATION_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public OperationOrderDO getOperationOrder(Long id) {
        OperationOrderDO operationOrderDO = operationOrderMapper.selectById(id);
        if (operationOrderDO != null) {
            OperationDeviceRespVO operationDevice = operationDeviceService.getOperationDevice(operationOrderDO.getDeviceId());
            operationOrderDO.setAddress(operationDevice.getAddress());
        }
        return operationOrderDO;
    }

    @Override
    public PageResult<OperationOrderDO> getOperationOrderPage(OperationOrderPageReqVO pageReqVO) {

        List<OperationQuestionTypeDO> operationQuestionTypeDOS = operationQuestionTypeMapper.selectAllQuestionType();
        PageResult<OperationOrderDO> operationOrderDOPageResult = operationOrderMapper.selectPage(pageReqVO);
        List<OperationOrderDO> orderList = operationOrderDOPageResult.getList();
        if (CollectionUtil.isNotEmpty(orderList) && CollectionUtil.isNotEmpty(operationQuestionTypeDOS)) {
            for (OperationOrderDO operationOrderDO : orderList) {
                OperationQuestionTypeDO operationQuestionTypeDO = operationQuestionTypeDOS.stream().filter(p -> p.getId() == operationOrderDO.getQuestionType()).findAny().orElse(null);
                operationOrderDO.setQuestionTypeStr(operationQuestionTypeDO.getName());
            }
        }
        operationOrderDOPageResult.setList(orderList);
        return operationOrderDOPageResult;
    }

    @Override
    @Transactional
    public void operateOrder(OperationOrderReqVO operationOrderReqVO) {
        //工单非空判断
        OperationOrderDO operationOrderDO = operationOrderMapper.selectById(operationOrderReqVO.getId());
        if (operationOrderDO == null) {
            throw exception(OPERATION_ORDER_NOT_EXISTS);
        }
        OperationOrderOperateRecordDO orderOperateRecordDO = BeanUtils.toBean(operationOrderReqVO, OperationOrderOperateRecordDO.class);
        orderOperateRecordDO.setId(null);
        orderOperateRecordDO.setOrderId(operationOrderDO.getId());
        orderOperateRecordDO.setEndTime(LocalDateTime.now());
        orderOperateRecordDO.setOperateUserId(getLoginUserId());
        orderOperateRecordDO.setOperateUserNickName(getLoginUserNickname());
        //1.根据操作type具体处理，当前工单状态判断，以及获取每种操作的beginTime
        if (OperateConstant.PAIDAN_TYPE.equals(operationOrderReqVO.getOperateType())) {//00:派单
            if (!OperateConstant.WAIT_ALLOCATION_STATUS.equals(operationOrderDO.getStatus())) {
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            //修改订单信息
            orderOperateRecordDO.setBeginTime(LocalDateTime.now());//当前状态的开始时间
            operationOrderDO.setType("00");
            operationOrderDO.setStatus(OperateConstant.WAIT_DEAL_STATUS);
            operationOrderDO.setDealUserId(operationOrderReqVO.getUserId());
            operationOrderDO.setDealUserNickName(operationOrderReqVO.getUserNickName());
            operationOrderDO.setAllocationTime(LocalDateTime.now());
            operationOrderDO.setAllocationConsume(Duration.between(orderOperateRecordDO.getBeginTime(), orderOperateRecordDO.getEndTime()).toMillis());
            operationOrderDO.setAllocationUserId(getLoginUserId());
            operationOrderDO.setAllocationUserNickName(getLoginUserNickname());
        } else if (OperateConstant.LINGDAN_TYPE.equals(operationOrderReqVO.getOperateType())) {//01:领单
            if (!OperateConstant.WAIT_ALLOCATION_STATUS.equals(operationOrderDO.getStatus())) {
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            orderOperateRecordDO.setBeginTime(operationOrderDO.getCreateTime());
            orderOperateRecordDO.setUserId(SecurityFrameworkUtils.getLoginUserId());
            orderOperateRecordDO.setUserNickName(SecurityFrameworkUtils.getLoginUserNickname());
            operationOrderDO.setType("01");
            operationOrderDO.setStatus(OperateConstant.WAIT_DEAL_STATUS);
            operationOrderDO.setDealUserId(getLoginUserId());
            operationOrderDO.setDealUserNickName(getLoginUserNickname());
            operationOrderDO.setAllocationTime(LocalDateTime.now());
            operationOrderDO.setAllocationConsume(Duration.between(orderOperateRecordDO.getBeginTime(), orderOperateRecordDO.getEndTime()).toMillis());
            operationOrderDO.setAllocationUserId(getLoginUserId());
            operationOrderDO.setAllocationUserNickName(getLoginUserNickname());
        } else if (OperateConstant.ZHUANJIAO_TYPE.equals(operationOrderReqVO.getOperateType())) {//02转交
            if (!OperateConstant.WAIT_DEAL_STATUS.equals(operationOrderDO.getStatus())
                    && !OperateConstant.IN_GOING_STATUS.equals(operationOrderDO.getStatus())
                    && !OperateConstant.HANG_UP_STATUS.equals(operationOrderDO.getStatus())) {
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            if (OperateConstant.WAIT_DEAL_STATUS.equals(operationOrderDO.getStatus())) {
                orderOperateRecordDO.setBeginTime(operationOrderDO.getAllocationTime());
            } else if (OperateConstant.IN_GOING_STATUS.equals(operationOrderDO.getStatus())) {
                orderOperateRecordDO.setBeginTime(operationOrderDO.getSiteConfirmTime());
            } else {
                orderOperateRecordDO.setBeginTime(operationOrderDO.getHangUpTime());
            }
            operationOrderDO.setType("02");
            operationOrderDO.setStatus(OperateConstant.WAIT_DEAL_STATUS);
            operationOrderDO.setDealUserId(operationOrderReqVO.getUserId());
            operationOrderDO.setDealUserNickName(operationOrderReqVO.getUserNickName());
        } else if (OperateConstant.XIANCHNAGQUEREN_TYPE.equals(operationOrderReqVO.getOperateType())) {//03:现场确认
            if (!OperateConstant.WAIT_DEAL_STATUS.equals(operationOrderDO.getStatus())) {
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            if (CollectionUtils.isAnyEmpty(operationOrderReqVO.getPictureList())) {
                throw exception(OPERATION_ORDER_OPERATE_NOT_PICTURE);
            }
            orderOperateRecordDO.setBeginTime(operationOrderDO.getAllocationTime());
            operationOrderDO.setStatus(OperateConstant.IN_GOING_STATUS);
            operationOrderDO.setSiteConfirmTime(LocalDateTime.now());
            operationOrderDO.setSiteDonfirmConsume(Duration.between(orderOperateRecordDO.getBeginTime(), orderOperateRecordDO.getEndTime()).toMillis());

        } else if (OperateConstant.GUAQI_TYPE.equals(operationOrderReqVO.getOperateType())) {//04:挂起
            if (!OperateConstant.IN_GOING_STATUS.equals(operationOrderDO.getStatus())) {
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            orderOperateRecordDO.setBeginTime(operationOrderDO.getSiteConfirmTime());
            operationOrderDO.setStatus(OperateConstant.HANG_UP_STATUS);
            operationOrderDO.setHangUpTime(LocalDateTime.now());
            operationOrderDO.setHangUpConsume(Duration.between(orderOperateRecordDO.getBeginTime(), orderOperateRecordDO.getEndTime()).toMillis());
        } else if (OperateConstant.WANCHENG_TYPE.equals(operationOrderReqVO.getOperateType())
                || OperateConstant.WANCHENG_WUXUCHULI_TYPE.equals(operationOrderReqVO.getOperateType())
                || OperateConstant.WANCHENG_WUFACHULI_TYPE.equals(operationOrderReqVO.getOperateType())) {//05 已完成,0501:无需处理,0502:无法排除故障
            if (!OperateConstant.IN_GOING_STATUS.equals(operationOrderDO.getStatus())) {
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            orderOperateRecordDO.setBeginTime(operationOrderDO.getSiteConfirmTime());
            operationOrderDO.setStatus(OperateConstant.ALREADY_DEAL_STATUS);
            operationOrderDO.setDealTime(LocalDateTime.now());
            operationOrderDO.setDealConsume(Duration.between(orderOperateRecordDO.getBeginTime(), orderOperateRecordDO.getEndTime()).toMillis());
        } else if (OperateConstant.CHEXIAO_TYPE.equals(operationOrderReqVO.getOperateType())) {//06 已撤销
            orderOperateRecordDO.setBeginTime(operationOrderDO.getCreateTime());
            operationOrderDO.setStatus(OperateConstant.ROLLBACK_STATUS);
        } else {
            throw exception(OPERATION_ORDER_OPERATE_NOT_EXISTS);
        }
        orderOperateRecordDO.setSpendTime(Duration.between(orderOperateRecordDO.getBeginTime(), orderOperateRecordDO.getEndTime()).toMillis());

        //2.先存记录表
        orderOperateRecordDO.setRemark(operationOrderReqVO.getRemark());
        operationOrderOperateRecordMapper.insert(orderOperateRecordDO);
        if (OperateConstant.XIANCHNAGQUEREN_TYPE.equals(operationOrderReqVO.getOperateType())) {//03:现场确认
            List<OperationOrderOperatePictureDO> pictureList = new ArrayList<>();
            for (OperationDevicePictureSaveReqVO operationDevicePictureSaveReqVO : operationOrderReqVO.getPictureList()) {
                OperationOrderOperatePictureDO picture = new OperationOrderOperatePictureDO();
                picture.setOperateRecordId(orderOperateRecordDO.getId());
                picture.setType("00");
                picture.setUrl(operationDevicePictureSaveReqVO.getUrl());
                pictureList.add(picture);
            }
            operationOrderOperatePictureMapper.insertBatch(pictureList);
        }
        //3.更新工单表
        operationOrderMapper.updateById(operationOrderDO);
    }

    @Override
    @Transactional
    public CommonResult workOrderCirculation(OperationOrderReqVO operationOrderReqVO) {

        validateOperationOrderExists(operationOrderReqVO.getId());
        //获取当前工单数据
        OperationOrderDO operationOrderDO = operationOrderMapper.selectById(operationOrderReqVO.getId());
        operationOrderDO.setCompleteResult(operationOrderReqVO.getCompleteResult());

        OperationOrderOperateRecordDO operateRecordDO = new OperationOrderOperateRecordDO();
        operateRecordDO.setOrderId(operationOrderDO.getId());
        operateRecordDO.setBeginTime(LocalDateTime.now());
        operateRecordDO.setOperateUserId(SecurityFrameworkUtils.getLoginUserId());
        operateRecordDO.setOperateUserNickName(SecurityFrameworkUtils.getLoginUserNickname());
        operateRecordDO.setRemark(operationOrderReqVO.getRemark());
        operateRecordDO.setPicList(operationOrderReqVO.getPictureList());
        //有操作对象的时候就存，没有的是时候就是当前登录人
        operateRecordDO.setUserId(operationOrderReqVO.getUserId() == null ? SecurityFrameworkUtils.getLoginUserId() : operationOrderReqVO.getUserId());
        AdminUserDO user = adminUserService.getUser(operateRecordDO.getUserId());
        operateRecordDO.setUserNickName(user.getNickname());

        OperationOrderOperateRecordDO lastOperateRecordDO = operationOrderOperateRecordMapper.selectOne(new QueryWrapper<OperationOrderOperateRecordDO>().lambda()
                .eq(OperationOrderOperateRecordDO::getOrderId, operationOrderReqVO.getId())
                .orderByDesc(BaseDO::getCreateTime).last("LIMIT 1"));

        //更新上一条流转记录的结束时间并计算上一条的耗时
        lastOperateRecordDO.setEndTime(LocalDateTime.now());
        lastOperateRecordDO.setSpendTime(Duration.between(lastOperateRecordDO.getBeginTime(), lastOperateRecordDO.getEndTime()).toMillis());

        String methodStr = OrderOperateEnum.valueOf(operationOrderReqVO.getOperateMethod()).getValue();
        //反射调用方法
        WorkOrderCirculationClass workOrderCirculationClass = new WorkOrderCirculationClass();
        Method method = null;

        try {
            method = WorkOrderCirculationClass.class.getMethod(methodStr, OperationOrderDO.class, OperationOrderOperateRecordDO.class, OperationOrderOperateRecordDO.class);
            method.invoke(workOrderCirculationClass, operationOrderDO, operateRecordDO, lastOperateRecordDO);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error(500, "当前工单状态不可执行该操作");
        }

        operationOrderOperateRecordMapper.updateById(lastOperateRecordDO);

        return CommonResult.success(true);
    }


    class WorkOrderCirculationClass {
        /**
         * 派单
         */
        @Transactional
        public void distributeLeaflets(OperationOrderDO operationOrderDO, OperationOrderOperateRecordDO operateRecordDO,
                                       OperationOrderOperateRecordDO lastOperateRecordDO) {
            //先判断工单状态，只有未分配的工单可以分配/领单
            if (!OperateConstant.WAIT_ALLOCATION_STATUS.equals(operationOrderDO.getStatus())) {
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            //业务处理
            //1、新增流转记录，补充流转的相关数据
            operateRecordDO.setOperateType(OperateConstant.PAIDAN_TYPE);
            operationOrderOperateRecordMapper.insert(operateRecordDO);
            //2、更新工单相关数据
            operationOrderDO.setAllocationConsume(lastOperateRecordDO.getSpendTime());
            operationOrderDO.setType(OperateConstant.PAIDAN_TYPE);
            operationOrderDO.setStatus(OperateConstant.WAIT_DEAL_STATUS);
            operationOrderDO.setDealUserId(operateRecordDO.getUserId());
            operationOrderDO.setDealUserNickName(operateRecordDO.getUserNickName());
            operationOrderDO.setAllocationTime(LocalDateTime.now());
            operationOrderDO.setAllocationConsume(lastOperateRecordDO.getSpendTime());
            operationOrderDO.setAllocationUserId(getLoginUserId());
            operationOrderDO.setAllocationUserNickName(getLoginUserNickname());
            operationOrderDO.setType("0");
            operationOrderMapper.updateById(operationOrderDO);
            //发送微信公众号消息--信息部内部消息
            List<String> openIdList = new ArrayList<>();
            AdminUserDO user = adminUserService.getUser(operateRecordDO.getUserId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (user != null) {
                openIdList.add(user.getOpenid());
                WechatNoticeVO wechatNoticeVO = new WechatNoticeVO();
                String templateId = "moCz5xqQZooEghuw2D_EU57tmilMtnACDeEYjraRD1I";  //工单派工消息模板
                wechatNoticeVO.setTemplate_id(templateId); //模板Id,templateId
                Map dataMap = new HashMap<>();
                Map<String, String> cs2 = new HashMap<>();
                cs2.put("value", operationOrderDO.getCode());
                dataMap.put("character_string2", cs2);
                Map<String, String> t8 = new HashMap<>();
                t8.put("value", operationOrderDO.getTitle());
                dataMap.put("thing8", t8);
                Map<String, String> p10 = new HashMap<>();
                p10.put("value", operateRecordDO.getUserNickName());
                dataMap.put("phrase10", p10);
                Map<String, String> time13 = new HashMap<>();

                time13.put("value", operateRecordDO.getCreateTime().format(formatter));
                dataMap.put("time13", time13);
                wechatNoticeVO.setData(dataMap);
                wechatNoticeVO.setMiniprogram(wechatNoticeVO.createMiniProgram("appId", "/"));
                try {
                    weChatSendMessageService.sendModelMessage(openIdList, wechatNoticeVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //给报修人的状态反馈
            AdminUserDO repairer = adminUserService.getUser(Long.valueOf(operationOrderDO.getCreator()));
            String repairerOpenId = repairer.getOpenid();
            String code = operationOrderDO.getCode();
            String orderStatus = "待处理";
            String operatorName = "工单由" + operateRecordDO.getOperateUserNickName() + "指派给" + operateRecordDO.getUserNickName();
            String time = operateRecordDO.getCreateTime().format(formatter);
            if (StringUtil.isNotBlank(repairerOpenId)) {
                orderStatusChangeNotice(repairerOpenId, code, orderStatus, operatorName, time);
            }

        }

        /**
         * 领单
         */
        @Transactional
        public void collectDocuments(OperationOrderDO operationOrderDO, OperationOrderOperateRecordDO operateRecordDO,
                                     OperationOrderOperateRecordDO lastOperateRecordDO) {
            //先判断工单状态，只有未分配的工单可以分配/领单
            if (!OperateConstant.WAIT_ALLOCATION_STATUS.equals(operationOrderDO.getStatus())) {
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            //业务处理
            //1、新增流转记录，补充流转的相关数据
            operateRecordDO.setOperateType(OperateConstant.LINGDAN_TYPE);
            operationOrderOperateRecordMapper.insert(operateRecordDO);
            //2、更新工单相关数据
            operationOrderDO.setAllocationConsume(lastOperateRecordDO.getSpendTime());
            operationOrderDO.setType(OperateConstant.PAIDAN_TYPE);
            operationOrderDO.setStatus(OperateConstant.WAIT_DEAL_STATUS);
            operationOrderDO.setDealUserId(operateRecordDO.getUserId());
            operationOrderDO.setDealUserNickName(operateRecordDO.getUserNickName());
            operationOrderDO.setAllocationTime(LocalDateTime.now());
            operationOrderDO.setAllocationConsume(lastOperateRecordDO.getSpendTime());
            operationOrderDO.setAllocationUserId(getLoginUserId());
            operationOrderDO.setAllocationUserNickName(getLoginUserNickname());
            operationOrderDO.setType("1");
            operationOrderMapper.updateById(operationOrderDO);

            //发送微信公众号消息--信息部内部消息
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //给报修人的状态反馈
            AdminUserDO repairer = adminUserService.getUser(Long.valueOf(operationOrderDO.getCreator()));
            String repairerOpenId = repairer.getOpenid();
            String code = operationOrderDO.getCode();
            String orderStatus = "待处理";
            String operatorName = operateRecordDO.getUserNickName() + "认领了工单";
            String time = operateRecordDO.getCreateTime().format(formatter);
            if (StringUtil.isNotBlank(repairerOpenId)) {
                orderStatusChangeNotice(repairerOpenId, code, orderStatus, operatorName, time);
            }
        }

        /**
         * 转交
         */
        @Transactional
        public void transfer(OperationOrderDO operationOrderDO, OperationOrderOperateRecordDO operateRecordDO,
                             OperationOrderOperateRecordDO lastOperateRecordDO) {
            if (!(OperateConstant.WAIT_ALLOCATION_STATUS.equals(operationOrderDO.getStatus())
                    || OperateConstant.ALREADY_DEAL_STATUS.equals(operationOrderDO.getStatus())
                    || OperateConstant.COMPLETE_STATUS.equals(operationOrderDO.getStatus())
                    || OperateConstant.ROLLBACK_STATUS.equals(operationOrderDO.getStatus())
                    || OperateConstant.COMPLETE_NO_NEED_DEAL_STATUS.equals(operationOrderDO.getStatus())
                    || OperateConstant.COMPLETE_CAN_NOT_DEAL_STATUS.equals(operationOrderDO.getStatus()))) {
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            operationOrderDO.setDealUserId(operateRecordDO.getUserId());
            operationOrderDO.setDealUserNickName(operateRecordDO.getUserNickName());
            operationOrderDO.setType(OperateConstant.ZHUANJIAO_TYPE);
            operationOrderDO.setStatus(OperateConstant.WAIT_DEAL_STATUS);
            operationOrderDO.setType("2");
            operationOrderMapper.updateById(operationOrderDO);
            operateRecordDO.setOperateType(OperateConstant.ZHUANJIAO_TYPE);
            operationOrderOperateRecordMapper.insert(operateRecordDO);

            //发送微信公众号消息--信息部内部消息
            List<String> openIdList = new ArrayList<>();
            AdminUserDO user = adminUserService.getUser(operateRecordDO.getUserId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (user != null) {
                openIdList.add(user.getOpenid());
                WechatNoticeVO wechatNoticeVO = new WechatNoticeVO();
                String templateId = "8rW5_qBpLfqYzgsrpuXrGKatI4mxf2CwiYfuSO-Qgc8";  //工单转交消息模板
                wechatNoticeVO.setTemplate_id(templateId); //模板Id,templateId
                Map dataMap = new HashMap<>();
                Map<String, String> cs2 = new HashMap<>();
                cs2.put("value", operationOrderDO.getCode());
                dataMap.put("character_string1", cs2);
                Map<String, String> t6 = new HashMap<>();
                t6.put("value", operationOrderDO.getTitle());
                dataMap.put("thing6", t6);

                Map<String, String> thing9 = new HashMap<>();
                thing9.put("value", operateRecordDO.getOperateUserNickName());
                dataMap.put("thing9", thing9);
                Map<String, String> thing12 = new HashMap<>();
                thing12.put("value", operateRecordDO.getUserNickName());
                dataMap.put("thing12", thing12);
                Map<String, String> time13 = new HashMap<>();
                time13.put("value", operateRecordDO.getCreateTime().format(formatter));
                dataMap.put("time5", time13);
                wechatNoticeVO.setData(dataMap);
                wechatNoticeVO.setMiniprogram(wechatNoticeVO.createMiniProgram("appId", "/"));
                try {
                    weChatSendMessageService.sendModelMessage(openIdList, wechatNoticeVO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //给报修人的状态反馈
            AdminUserDO repairer = adminUserService.getUser(Long.valueOf(operationOrderDO.getCreator()));
            String repairerOpenId = repairer.getOpenid();
            String code = operationOrderDO.getCode();
            String orderStatus = "待处理";
            String operatorName = "工单由" + operateRecordDO.getOperateUserNickName() + "转交给" + operateRecordDO.getUserNickName();
            String time = operateRecordDO.getCreateTime().format(formatter);
            if (StringUtil.isNotBlank(repairerOpenId)) {
                orderStatusChangeNotice(repairerOpenId, code, orderStatus, operatorName, time);
            }

        }

        /**
         * 现场确认
         */
        @Transactional
        public void confirm(OperationOrderDO operationOrderDO, OperationOrderOperateRecordDO operateRecordDO,
                            OperationOrderOperateRecordDO lastOperateRecordDO) {
            if (!OperateConstant.WAIT_DEAL_STATUS.equals(operationOrderDO.getStatus())) {
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            operationOrderDO.setStatus(OperateConstant.IN_GOING_STATUS);
            operationOrderDO.setSiteConfirmTime(LocalDateTime.now());
            operationOrderDO.setSiteDonfirmConsume(lastOperateRecordDO.getSpendTime());
            operationOrderMapper.updateById(operationOrderDO);
            operateRecordDO.setOperateType(OperateConstant.XIANCHNAGQUEREN_TYPE);
            operationOrderOperateRecordMapper.insert(operateRecordDO);
            if (CollectionUtil.isNotEmpty(operateRecordDO.getPicList())) {
                List<OperationOrderOperatePictureDO> pictureDOList = new ArrayList<>();
                for (OperationDevicePictureSaveReqVO pic : operateRecordDO.getPicList()) {
                    OperationOrderOperatePictureDO picture = new OperationOrderOperatePictureDO();
                    picture.setOperateRecordId(operateRecordDO.getId());
                    picture.setType("00");
                    picture.setUrl(pic.getUrl());
                    pictureDOList.add(picture);
                }
                operationOrderOperatePictureMapper.insertBatch(pictureDOList);
            }
            //发送微信公众号消息--信息部内部消息
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //给报修人的状态反馈
            AdminUserDO repairer = adminUserService.getUser(Long.valueOf(operationOrderDO.getCreator()));
            String repairerOpenId = repairer.getOpenid();
            String code = operationOrderDO.getCode();
            String orderStatus = "处理中";
            String operatorName = operateRecordDO.getUserNickName() + "进行了工单现场确认";
            String time = operateRecordDO.getCreateTime().format(formatter);
            if (StringUtil.isNotBlank(repairerOpenId)) {
                orderStatusChangeNotice(repairerOpenId, code, orderStatus, operatorName, time);
            }
        }

        /**
         * 挂起
         */
        @Transactional
        public void hangUp(OperationOrderDO operationOrderDO, OperationOrderOperateRecordDO operateRecordDO,
                           OperationOrderOperateRecordDO lastOperateRecordDO) {

            if (!OperateConstant.IN_GOING_STATUS.equals(operationOrderDO.getStatus())) {
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            operationOrderDO.setHangUpTime(LocalDateTime.now());
            operationOrderDO.setStatus(OperateConstant.HANG_UP_STATUS);
            operationOrderMapper.updateById(operationOrderDO);
            operateRecordDO.setOperateType(OperateConstant.GUAQI_TYPE);
            operationOrderOperateRecordMapper.insert(operateRecordDO);
            //发送微信公众号消息--信息部内部消息
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //给报修人的状态反馈
            AdminUserDO repairer = adminUserService.getUser(Long.valueOf(operationOrderDO.getCreator()));
            String repairerOpenId = repairer.getOpenid();
            String code = operationOrderDO.getCode();
            String orderStatus = "已挂起";
            String operatorName = operateRecordDO.getUserNickName() + "暂停了当前工单进度";
            String time = operateRecordDO.getCreateTime().format(formatter);
            if (StringUtil.isNotBlank(repairerOpenId)) {
                orderStatusChangeNotice(repairerOpenId, code, orderStatus, operatorName, time);
            }
        }

        /**
         * 再次开始
         */
        @Transactional
        public void restart(OperationOrderDO operationOrderDO, OperationOrderOperateRecordDO operateRecordDO,
                            OperationOrderOperateRecordDO lastOperateRecordDO) {
            //只有挂起状态的工单才能再次开始
            if (!OperateConstant.HANG_UP_STATUS.equals(operationOrderDO.getStatus())) {
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            operationOrderDO.setStatus(OperateConstant.IN_GOING_STATUS);
            operationOrderDO.setHangUpConsume(operationOrderDO.getHangUpConsume() == null ? 0 : operationOrderDO.getHangUpConsume() + lastOperateRecordDO.getSpendTime());
            operationOrderMapper.updateById(operationOrderDO);
            operateRecordDO.setOperateType(OperateConstant.KAISHI_TYPE);
            operationOrderOperateRecordMapper.insert(operateRecordDO);

            //发送微信公众号消息--信息部内部消息
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //给报修人的状态反馈
            AdminUserDO repairer = adminUserService.getUser(Long.valueOf(operationOrderDO.getCreator()));
            String repairerOpenId = repairer.getOpenid();
            String code = operationOrderDO.getCode();
            String orderStatus = "处理中";
            String operatorName = operateRecordDO.getUserNickName() + "重启了工单进度";
            String time = operateRecordDO.getCreateTime().format(formatter);
            if (StringUtil.isNotBlank(repairerOpenId)) {
                orderStatusChangeNotice(repairerOpenId, code, orderStatus, operatorName, time);
            }
        }

        /**
         * 完工
         */
        @Transactional
        public void complete(OperationOrderDO operationOrderDO, OperationOrderOperateRecordDO operateRecordDO,
                             OperationOrderOperateRecordDO lastOperateRecordDO) {

            //只有进行中状态的工单才能完成
            if (!OperateConstant.IN_GOING_STATUS.equals(operationOrderDO.getStatus())) {
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            //处理完成的工单无法继续进行状态操作
            //处理不同的完工状态
            switch (operationOrderDO.getCompleteResult()) {
                case 1: //无需处理
                    operationOrderDO.setStatus(OperateConstant.COMPLETE_NO_NEED_DEAL_STATUS);
                    break;
                case 2: //无法处理
                    operationOrderDO.setStatus(OperateConstant.COMPLETE_CAN_NOT_DEAL_STATUS);
                    break;
                default:    //默认已完成
                    operationOrderDO.setStatus(OperateConstant.COMPLETE_STATUS);
            }
            operationOrderDO.setDealTime(LocalDateTime.now());
            List<OperationOrderOperateRecordDO> dealOperateRecordDOS = operationOrderOperateRecordMapper.selectList(new QueryWrapper<OperationOrderOperateRecordDO>().lambda()
                    .eq(OperationOrderOperateRecordDO::getUserId, operationOrderDO.getDealUserId())
                    .eq(OperationOrderOperateRecordDO::getOperateType, OperateConstant.KAISHI_TYPE));
            Long sumSpend = dealOperateRecordDOS.stream().mapToLong(p -> p.getSpendTime().intValue()).sum();
            operationOrderDO.setDealConsume(sumSpend);
            operationOrderDO.setCompleteTime(LocalDateTime.now());
            //计算完成总耗时
            operationOrderDO.setCompleteConsume(Duration.between(LocalDateTime.now(), operationOrderDO.getCreateTime()).toMillis());
            operationOrderMapper.updateById(operationOrderDO);
            operateRecordDO.setOperateType(OperateConstant.WANCHENG_TYPE);
            operationOrderOperateRecordMapper.insert(operateRecordDO);
            //发送微信公众号消息--信息部内部消息
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //给报修人的状态反馈
            AdminUserDO repairer = adminUserService.getUser(Long.valueOf(operationOrderDO.getCreator()));
            String repairerOpenId = repairer.getOpenid();
            String code = operationOrderDO.getCode();
            String orderStatus = "已完成";
            String operatorName = operateRecordDO.getUserNickName() + "对工单提报完工";
            String time = operateRecordDO.getCreateTime().format(formatter);
            if (StringUtil.isNotBlank(repairerOpenId)) {
                orderStatusChangeNotice(repairerOpenId, code, orderStatus, operatorName, time);
            }

        }

        /**
         * 撤销
         */
        @Transactional
        public void revoke(OperationOrderDO operationOrderDO, OperationOrderOperateRecordDO operateRecordDO,
                           OperationOrderOperateRecordDO lastOperateRecordDO) {
            //撤销后无法恢复工单状态
            operationOrderDO.setStatus(OperateConstant.ROLLBACK_STATUS);
            operationOrderMapper.updateById(operationOrderDO);
            operateRecordDO.setOperateType(OperateConstant.CHEXIAO_TYPE);
            operateRecordDO.setEndTime(LocalDateTime.now());
            operateRecordDO.setSpendTime(0L);
            operationOrderOperateRecordMapper.insert(operateRecordDO);
            //发送微信公众号消息--信息部内部消息
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //给报修人的状态反馈
            AdminUserDO repairer = adminUserService.getUser(Long.valueOf(operationOrderDO.getCreator()));
            AdminUserDO dealer = adminUserService.getUser(Long.valueOf(operationOrderDO.getDealUserId()));
            String repairerOpenId = repairer.getOpenid();
            String dealerOpenId = dealer.getOpenid();
            String code = operationOrderDO.getCode();
            String orderStatus = "已撤销";
            String operatorName = operateRecordDO.getUserNickName() + "撤销了工单";
            String time = operateRecordDO.getCreateTime().format(formatter);
            if (StringUtil.isNotBlank(repairerOpenId)) {
                orderStatusChangeNotice(repairerOpenId, code, orderStatus, operatorName, time);
            }
            if (StringUtil.isNotBlank(dealerOpenId)) {
                orderStatusChangeNotice(dealerOpenId, code, orderStatus, operatorName, time);
            }
        }
    }

    private void orderStatusChangeNotice(String repairerOpenId, String code, String orderStatus, String operatorName, String time) {

        try {
            List<String> openIdList = new ArrayList<>();
            openIdList.add(repairerOpenId);
            WechatNoticeVO wechatNoticeVO = new WechatNoticeVO();
            String templateId = "hGGuKzP2XQpO57rVcwQwYWn9V36keth4agPcuvLfyCo";  //工单派工消息模板
            wechatNoticeVO.setTemplate_id(templateId); //模板Id,templateId
            Map dataMap = new HashMap<>();
            Map<String, String> cs9 = new HashMap<>();
            cs9.put("value", orderStatus);
            dataMap.put("character_string9", code);
            Map<String, String> st5 = new HashMap<>();
            st5.put("value", orderStatus);
            dataMap.put("short_thing5", st5);
            Map<String, String> t14 = new HashMap<>();
            t14.put("value", operatorName);
            dataMap.put("thing14", t14);
            Map<String, String> time13 = new HashMap<>();
            time13.put("value", time);
            dataMap.put("time13", time13);
            wechatNoticeVO.setData(dataMap);
            wechatNoticeVO.setMiniprogram(wechatNoticeVO.createMiniProgram("appId", "/"));
            weChatSendMessageService.sendModelMessage(openIdList, wechatNoticeVO);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}