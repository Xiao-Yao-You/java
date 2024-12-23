package com.hk.jigai.module.system.service.inspectionrecord;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.module.system.controller.admin.inspectionrecord.vo.InspectionRecordPageReqVO;
import com.hk.jigai.module.system.controller.admin.inspectionrecord.vo.InspectionRecordSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.inspectionrecord.InspectionRecordDO;
import com.hk.jigai.module.system.dal.mysql.inspectionrecord.InspectionRecordMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.INSPECTION_RECORD_NOT_EXISTS;


/**
 * 设备巡检记录 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class InspectionRecordServiceImpl implements InspectionRecordService {

    @Resource
    private InspectionRecordMapper inspectionRecordMapper;

    @Override
    public Long createInspectionRecord(InspectionRecordSaveReqVO createReqVO) {
        // 插入
        InspectionRecordDO inspectionRecord = BeanUtils.toBean(createReqVO, InspectionRecordDO.class);
        inspectionRecordMapper.insert(inspectionRecord);
        // 返回
        return inspectionRecord.getId();
    }

    @Override
    public void updateInspectionRecord(InspectionRecordSaveReqVO updateReqVO) {
        // 校验存在
        validateInspectionRecordExists(updateReqVO.getId());
        // 更新
        InspectionRecordDO updateObj = BeanUtils.toBean(updateReqVO, InspectionRecordDO.class);
        inspectionRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteInspectionRecord(Long id) {
        // 校验存在
        validateInspectionRecordExists(id);
        // 删除
        inspectionRecordMapper.deleteById(id);
    }

    private void validateInspectionRecordExists(Long id) {
        if (inspectionRecordMapper.selectById(id) == null) {
            throw exception(INSPECTION_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public InspectionRecordDO getInspectionRecord(Long id) {
        return inspectionRecordMapper.selectById(id);
    }

    @Override
    public PageResult<InspectionRecordDO> getInspectionRecordPage(InspectionRecordPageReqVO pageReqVO) {
        return inspectionRecordMapper.selectPage(pageReqVO);
    }

}