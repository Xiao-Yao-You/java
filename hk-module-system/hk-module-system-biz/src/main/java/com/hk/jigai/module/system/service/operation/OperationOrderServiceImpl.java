package com.hk.jigai.module.system.service.operation;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;
import com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils;
import com.hk.jigai.module.system.controller.admin.operation.vo.OperationDevicePictureSaveReqVO;
import com.hk.jigai.module.system.controller.admin.operation.vo.OperationOrderPageReqVO;
import com.hk.jigai.module.system.controller.admin.operation.vo.OperationOrderReqVO;
import com.hk.jigai.module.system.controller.admin.operation.vo.OperationOrderSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderOperatePictureDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderOperateRecordDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationQuestionTypeDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.dal.mysql.operation.OperationOrderMapper;
import com.hk.jigai.module.system.dal.mysql.operation.OperationOrderOperatePictureMapper;
import com.hk.jigai.module.system.dal.mysql.operation.OperationOrderOperateRecordMapper;
import com.hk.jigai.module.system.dal.mysql.operation.OperationQuestionTypeMapper;
import com.hk.jigai.module.system.enums.OrderOperateEnum;
import com.hk.jigai.module.system.service.scenecode.SceneCodeService;
import com.hk.jigai.module.system.service.user.AdminUserService;
import com.hk.jigai.module.system.util.operate.OperateConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        return operationOrderMapper.selectById(id);
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
        operateRecordDO.setUserNickName(operationOrderReqVO.getUserNickName() == null ? SecurityFrameworkUtils.getLoginUserNickname() : operationOrderReqVO.getUserNickName());

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
            operationOrderMapper.updateById(operationOrderDO);
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
            operationOrderMapper.updateById(operationOrderDO);
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
            operationOrderMapper.updateById(operationOrderDO);
            operateRecordDO.setOperateType(OperateConstant.ZHUANJIAO_TYPE);
            operationOrderOperateRecordMapper.insert(operateRecordDO);
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
        }

        /**
         * 再次开始
         */
        @Transactional
        public void restart(OperationOrderDO operationOrderDO, OperationOrderOperateRecordDO operateRecordDO,
                            OperationOrderOperateRecordDO lastOperateRecordDO) {
            operationOrderDO.setStatus(OperateConstant.IN_GOING_STATUS);
            operationOrderDO.setHangUpConsume(lastOperateRecordDO.getSpendTime());
            operationOrderMapper.updateById(operationOrderDO);
            operateRecordDO.setOperateType(OperateConstant.KAISHI_TYPE);
            operationOrderOperateRecordMapper.insert(operateRecordDO);
        }

        /**
         * 完工
         */
        @Transactional
        public void complete(OperationOrderDO operationOrderDO, OperationOrderOperateRecordDO operateRecordDO,
                             OperationOrderOperateRecordDO lastOperateRecordDO) {

            switch (operationOrderDO.getCompleteResult()) {
                case 1:
                    operationOrderDO.setStatus(OperateConstant.COMPLETE_NO_NEED_DEAL_STATUS);
                    break;
                case 2:
                    operationOrderDO.setStatus(OperateConstant.COMPLETE_CAN_NOT_DEAL_STATUS);
                    break;
                default:
                    operationOrderDO.setStatus(OperateConstant.COMPLETE_STATUS);
            }
            operationOrderDO.setCompleteTime(LocalDateTime.now());
            operationOrderMapper.updateById(operationOrderDO);
            operateRecordDO.setOperateType(OperateConstant.WANCHENG_TYPE);
            operationOrderOperateRecordMapper.insert(operateRecordDO);

        }

        /**
         * 撤销
         */
        @Transactional
        public void revoke(OperationOrderDO operationOrderDO, OperationOrderOperateRecordDO operateRecordDO,
                           OperationOrderOperateRecordDO lastOperateRecordDO) {
            operationOrderDO.setStatus(OperateConstant.ROLLBACK_STATUS);
            operationOrderMapper.updateById(operationOrderDO);
            operateRecordDO.setOperateType(OperateConstant.CHEXIAO_TYPE);
            operateRecordDO.setEndTime(LocalDateTime.now());
            operateRecordDO.setSpendTime(0L);
            operationOrderOperateRecordMapper.insert(operateRecordDO);
        }
    }
}