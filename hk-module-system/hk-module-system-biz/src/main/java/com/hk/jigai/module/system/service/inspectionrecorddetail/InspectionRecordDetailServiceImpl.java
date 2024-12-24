package com.hk.jigai.module.system.service.inspectionrecorddetail;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.module.system.controller.admin.inspectionrecorddetail.vo.InspectionRecordDetailPageReqVO;
import com.hk.jigai.module.system.controller.admin.inspectionrecorddetail.vo.InspectionRecordDetailSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.inspectionrecorddetail.InspectionRecordDetailDO;
import com.hk.jigai.module.system.dal.mysql.inspectionrecorddetail.InspectionRecordDetailMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.INSPECTION_RECORD_DETAIL_NOT_EXISTS;

/**
 * 巡检记录详情 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class InspectionRecordDetailServiceImpl implements InspectionRecordDetailService {

    @Resource
    private InspectionRecordDetailMapper inspectionRecordDetailMapper;

    @Override
    public Long createInspectionRecordDetail(InspectionRecordDetailSaveReqVO createReqVO) {
        // 插入
        InspectionRecordDetailDO inspectionRecordDetail = BeanUtils.toBean(createReqVO, InspectionRecordDetailDO.class);
        inspectionRecordDetailMapper.insert(inspectionRecordDetail);
        // 返回
        return inspectionRecordDetail.getId();
    }

    @Override
    public void updateInspectionRecordDetail(InspectionRecordDetailSaveReqVO updateReqVO) {
        // 校验存在
        validateInspectionRecordDetailExists(updateReqVO.getId());
        // 更新
        InspectionRecordDetailDO updateObj = BeanUtils.toBean(updateReqVO, InspectionRecordDetailDO.class);
        inspectionRecordDetailMapper.updateById(updateObj);
    }

    @Override
    public void deleteInspectionRecordDetail(Long id) {
        // 校验存在
        validateInspectionRecordDetailExists(id);
        // 删除
        inspectionRecordDetailMapper.deleteById(id);
    }

    private void validateInspectionRecordDetailExists(Long id) {
        if (inspectionRecordDetailMapper.selectById(id) == null) {
            throw exception(INSPECTION_RECORD_DETAIL_NOT_EXISTS);
        }
    }

    @Override
    public InspectionRecordDetailDO getInspectionRecordDetail(Long id) {
        return inspectionRecordDetailMapper.selectById(id);
    }

    @Override
    public PageResult<InspectionRecordDetailDO> getInspectionRecordDetailPage(InspectionRecordDetailPageReqVO pageReqVO) {
        return inspectionRecordDetailMapper.selectPage(pageReqVO);
    }

}