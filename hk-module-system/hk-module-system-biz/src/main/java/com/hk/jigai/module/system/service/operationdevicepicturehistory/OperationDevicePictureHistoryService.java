package com.hk.jigai.module.system.service.operationdevicepicturehistory;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.module.system.controller.admin.operationdevicepicturehistory.vo.OperationDevicePictureHistoryPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationdevicepicturehistory.vo.OperationDevicePictureHistorySaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicepicturehistory.OperationDevicePictureHistoryDO;

/**
 * 运维设备照片表_快照 Service 接口
 *
 * @author 邵志伟
 */
public interface OperationDevicePictureHistoryService {

    /**
     * 创建运维设备照片表_快照
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOperationDevicePictureHistory(@Valid OperationDevicePictureHistorySaveReqVO createReqVO);

    /**
     * 更新运维设备照片表_快照
     *
     * @param updateReqVO 更新信息
     */
    void updateOperationDevicePictureHistory(@Valid OperationDevicePictureHistorySaveReqVO updateReqVO);

    /**
     * 删除运维设备照片表_快照
     *
     * @param id 编号
     */
    void deleteOperationDevicePictureHistory(Long id);

    /**
     * 获得运维设备照片表_快照
     *
     * @param id 编号
     * @return 运维设备照片表_快照
     */
    OperationDevicePictureHistoryDO getOperationDevicePictureHistory(Long id);

    /**
     * 获得运维设备照片表_快照分页
     *
     * @param pageReqVO 分页查询
     * @return 运维设备照片表_快照分页
     */
    PageResult<OperationDevicePictureHistoryDO> getOperationDevicePictureHistoryPage(OperationDevicePictureHistoryPageReqVO pageReqVO);

}