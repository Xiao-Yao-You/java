package com.hk.jigai.module.system.service.inspectionrecorddetail;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.module.system.controller.admin.inspectionrecorddetail.vo.InspectionRecordDetailPageReqVO;
import com.hk.jigai.module.system.controller.admin.inspectionrecorddetail.vo.InspectionRecordDetailSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.inspectionrecorddetail.InspectionRecordDetailDO;

import javax.validation.Valid;

/**
 * 巡检记录详情 Service 接口
 *
 * @author 邵志伟
 */
public interface InspectionRecordDetailService {

    /**
     * 创建巡检记录详情
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInspectionRecordDetail(@Valid InspectionRecordDetailSaveReqVO createReqVO);

    /**
     * 更新巡检记录详情
     *
     * @param updateReqVO 更新信息
     */
    void updateInspectionRecordDetail(@Valid InspectionRecordDetailSaveReqVO updateReqVO);

    /**
     * 删除巡检记录详情
     *
     * @param id 编号
     */
    void deleteInspectionRecordDetail(Long id);

    /**
     * 获得巡检记录详情
     *
     * @param id 编号
     * @return 巡检记录详情
     */
    InspectionRecordDetailDO getInspectionRecordDetail(Long id);

    /**
     * 获得巡检记录详情分页
     *
     * @param pageReqVO 分页查询
     * @return 巡检记录详情分页
     */
    PageResult<InspectionRecordDetailDO> getInspectionRecordDetailPage(InspectionRecordDetailPageReqVO pageReqVO);

}