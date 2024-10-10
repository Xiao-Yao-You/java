package com.hk.jigai.module.system.service.operation;

import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderOperatePictureDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderOperateRecordDO;
import com.hk.jigai.module.system.dal.mysql.operation.OperationOrderOperatePictureMapper;
import com.hk.jigai.module.system.dal.mysql.operation.OperationOrderOperateRecordMapper;
import com.hk.jigai.module.system.service.scenecode.SceneCodeService;
import com.hk.jigai.module.system.util.operate.OperateConstant;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import com.hk.jigai.module.system.controller.admin.operation.vo.*;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationOrderDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.operation.OperationOrderMapper;

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
    private SceneCodeService sceneCodeService;
    @Override
    public Long createOperationOrder(OperationOrderSaveReqVO createReqVO) {
        // 插入
        OperationOrderDO operationOrder = BeanUtils.toBean(createReqVO, OperationOrderDO.class);
        operationOrderMapper.insert(operationOrder);
        String code = sceneCodeService.increment("REPAIR_ORDER");
        operationOrder.setCode(code);

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
        return operationOrderMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional
    public void operateOrder(OperationOrderReqVO updateReqVO) {
        //工单非空判断
        OperationOrderDO operationOrderDO = operationOrderMapper.selectById(updateReqVO.getOrderId());
        if (operationOrderDO == null) {
            throw exception(OPERATION_ORDER_NOT_EXISTS);
        }
        OperationOrderOperateRecordDO updateObj = BeanUtils.toBean(updateReqVO, OperationOrderOperateRecordDO.class);
        updateObj.setEndTime(LocalDateTime.now());
        updateObj.setOperateUserId(getLoginUserId());
        updateObj.setOperateUserNickName(getLoginUserNickname());
        //1.根据操作type具体处理，当前工单状态判断，以及获取每种操作的beginTime
        if(OperateConstant.PAIDAN_TYPE.equals(updateReqVO.getOperateType())){//00:派单
            if(!OperateConstant.WAIT_ALLOCATION_STATUS.equals(operationOrderDO.getStatus())){
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            updateObj.setBeginTime(operationOrderDO.getCreateTime());
            operationOrderDO.setType("01");
            operationOrderDO.setStatus(OperateConstant.WAIT_DEAL_STATUS);
            operationOrderDO.setDealUserId(updateReqVO.getUserId());
            operationOrderDO.setDealUserNickName(updateReqVO.getUserNickName());
            operationOrderDO.setAllocationTime(LocalDateTime.now());
            operationOrderDO.setAllocationConsume(Duration.between(updateObj.getBeginTime(),updateObj.getEndTime()).toMillis());
            operationOrderDO.setAllocationUserId(getLoginUserId());
            operationOrderDO.setAllocationUserNickName(getLoginUserNickname());
        }else if(OperateConstant.LINGDAN_TYPE.equals(updateReqVO.getOperateType())){//01:领单
            if(!OperateConstant.WAIT_ALLOCATION_STATUS.equals(operationOrderDO.getStatus())){
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            updateObj.setBeginTime(operationOrderDO.getCreateTime());
            operationOrderDO.setType("00");
            operationOrderDO.setStatus(OperateConstant.WAIT_DEAL_STATUS);
            operationOrderDO.setDealUserId(getLoginUserId());
            operationOrderDO.setDealUserNickName(getLoginUserNickname());
            operationOrderDO.setAllocationTime(LocalDateTime.now());
            operationOrderDO.setAllocationConsume(Duration.between(updateObj.getBeginTime(),updateObj.getEndTime()).toMillis());
            operationOrderDO.setAllocationUserId(getLoginUserId());
            operationOrderDO.setAllocationUserNickName(getLoginUserNickname());
        }else if(OperateConstant.TONGZUZHUANJIAO_TYPE.equals(updateReqVO.getOperateType())
                || OperateConstant.KUAZUZHUANJIAO_TYPE.equals(updateReqVO.getOperateType())){//0201:同组转交,0202:跨组转交
            if(!OperateConstant.WAIT_DEAL_STATUS.equals(operationOrderDO.getStatus())
                    && !OperateConstant.IN_GOING_STATUS.equals(operationOrderDO.getStatus())
                    && !OperateConstant.HANG_UP_STATUS.equals(operationOrderDO.getStatus())){
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            if(OperateConstant.WAIT_DEAL_STATUS.equals(operationOrderDO.getStatus())){
                updateObj.setBeginTime(operationOrderDO.getAllocationTime());
            }else if(OperateConstant.IN_GOING_STATUS.equals(operationOrderDO.getStatus())){
                updateObj.setBeginTime(operationOrderDO.getSiteConfirmTime());
            }else{
                updateObj.setBeginTime(operationOrderDO.getHangUpTime());
            }
            operationOrderDO.setType("02");
            operationOrderDO.setStatus(OperateConstant.WAIT_DEAL_STATUS);
            operationOrderDO.setDealUserId(updateReqVO.getUserId());
            operationOrderDO.setDealUserNickName(updateReqVO.getUserNickName());
        }else if(OperateConstant.XIANCHNAGQUEREN_TYPE.equals(updateReqVO.getOperateType())){//03:现场确认
            if(!OperateConstant.WAIT_DEAL_STATUS.equals(operationOrderDO.getStatus())){
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            if(CollectionUtils.isAnyEmpty(updateReqVO.getUrl())){
                throw exception(OPERATION_ORDER_OPERATE_NOT_PICTURE);
            }
            updateObj.setBeginTime(operationOrderDO.getAllocationTime());
            operationOrderDO.setStatus(OperateConstant.IN_GOING_STATUS);
            operationOrderDO.setSiteConfirmTime(LocalDateTime.now());
            operationOrderDO.setSiteDonfirmConsume(Duration.between(updateObj.getBeginTime(),updateObj.getEndTime()).toMillis());

        }else if(OperateConstant.GUAQI_TYPE.equals(updateReqVO.getOperateType())){//04:挂起
            if(!OperateConstant.IN_GOING_STATUS.equals(operationOrderDO.getStatus())){
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            updateObj.setBeginTime(operationOrderDO.getSiteConfirmTime());
            operationOrderDO.setStatus(OperateConstant.HANG_UP_STATUS);
            operationOrderDO.setHangUpTime(LocalDateTime.now());
            operationOrderDO.setHangUpConsume(Duration.between(updateObj.getBeginTime(),updateObj.getEndTime()).toMillis());
        }else if(OperateConstant.WANCHENG_TYPE.equals(updateReqVO.getOperateType())
                || OperateConstant.WANCHENG_WUXUCHULI_TYPE.equals(updateReqVO.getOperateType())
                || OperateConstant.WANCHENG_WUFACHULI_TYPE.equals(updateReqVO.getOperateType())){//05 已完成,0501:无需处理,0502:无法排除故障
            if(!OperateConstant.IN_GOING_STATUS.equals(operationOrderDO.getStatus())){
                throw exception(OPERATION_ORDER_OPERATE_ERROR);
            }
            updateObj.setBeginTime(operationOrderDO.getSiteConfirmTime());
            operationOrderDO.setStatus(OperateConstant.ALREADY_DEAL_STATUS);
            operationOrderDO.setDealTime(LocalDateTime.now());
            operationOrderDO.setDealConsume(Duration.between(updateObj.getBeginTime(),updateObj.getEndTime()).toMillis());
        }else if(OperateConstant.CHEXIAO_TYPE.equals(updateReqVO.getOperateType())){//06 已撤销
            updateObj.setBeginTime(operationOrderDO.getCreateTime());
            operationOrderDO.setStatus(OperateConstant.ROLLBACK_STATUS);
        }else {
            throw exception(OPERATION_ORDER_OPERATE_NOT_EXISTS);
        }
        updateObj.setSpendTime(Duration.between(updateObj.getBeginTime(),updateObj.getEndTime()).toMillis());

        //2.先存记录表
        operationOrderOperateRecordMapper.insert(updateObj);
        if(OperateConstant.XIANCHNAGQUEREN_TYPE.equals(updateReqVO.getOperateType())) {//03:现场确认
            List<OperationOrderOperatePictureDO> pictureList = new ArrayList<>();
            for (String url : updateReqVO.getUrl()) {
                OperationOrderOperatePictureDO picture = new OperationOrderOperatePictureDO();
                picture.setOperateRecordId(updateObj.getId());
                picture.setType("00");
                picture.setUrl(url);
                pictureList.add(picture);
            }
            operationOrderOperatePictureMapper.insertBatch(pictureList);
        }
        //3.更新工单表
        operationOrderMapper.updateById(operationOrderDO);
    }

}