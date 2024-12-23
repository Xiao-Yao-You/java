package com.hk.jigai.module.system.service.inspectionrecord;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.inspectionrecord.vo.InspectionRecordPageReqVO;
import com.hk.jigai.module.system.controller.admin.inspectionrecord.vo.InspectionRecordSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.inspectionrecord.InspectionRecordDO;

/**
 * 设备巡检记录 Service 接口
 *
 * @author 邵志伟
 */
public interface InspectionRecordService {

    /**
     * 创建设备巡检记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInspectionRecord(@Valid InspectionRecordSaveReqVO createReqVO);

    /**
     * 更新设备巡检记录
     *
     * @param updateReqVO 更新信息
     */
    void updateInspectionRecord(@Valid InspectionRecordSaveReqVO updateReqVO);

    /**
     * 删除设备巡检记录
     *
     * @param id 编号
     */
    void deleteInspectionRecord(Long id);

    /**
     * 获得设备巡检记录
     *
     * @param id 编号
     * @return 设备巡检记录
     */
    InspectionRecordDO getInspectionRecord(Long id);

    /**
     * 获得设备巡检记录分页
     *
     * @param pageReqVO 分页查询
     * @return 设备巡检记录分页
     */
    PageResult<InspectionRecordDO> getInspectionRecordPage(InspectionRecordPageReqVO pageReqVO);

}