package com.hk.jigai.module.system.service.mapcoordinateinfo;

import javax.validation.*;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo.MapFeedbackPageReqVO;
import com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo.MapFeedbackSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.mapcoordinateinfo.MapFeedbackDO;

/**
 * 厂区地图反馈 Service 接口
 *
 * @author 恒科信改
 */
public interface MapFeedbackService {

    /**
     * 创建厂区地图反馈
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createMapFeedback(@Valid MapFeedbackSaveReqVO createReqVO);

    /**
     * 更新厂区地图反馈
     *
     * @param updateReqVO 更新信息
     */
    void updateMapFeedback(@Valid MapFeedbackSaveReqVO updateReqVO);

    /**
     * 删除厂区地图反馈
     *
     * @param id 编号
     */
    void deleteMapFeedback(Integer id);

    /**
     * 获得厂区地图反馈
     *
     * @param id 编号
     * @return 厂区地图反馈
     */
    MapFeedbackDO getMapFeedback(Integer id);

    /**
     * 获得厂区地图反馈分页
     *
     * @param pageReqVO 分页查询
     * @return 厂区地图反馈分页
     */
    PageResult<MapFeedbackDO> getMapFeedbackPage(MapFeedbackPageReqVO pageReqVO);

}