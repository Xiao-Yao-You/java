package com.hk.jigai.module.system.service.mapcoordinateinfo;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo.*;
import com.hk.jigai.module.system.dal.dataobject.mapcoordinateinfo.MapCoordinateInfoDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;

/**
 * 厂区地图定位详细信息 Service 接口
 *
 * @author 恒科信改
 */
public interface MapCoordinateInfoService {

    /**
     * 创建厂区地图定位详细信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createMapCoordinateInfo(@Valid MapCoordinateInfoSaveReqVO createReqVO);

    /**
     * 更新厂区地图定位详细信息
     *
     * @param updateReqVO 更新信息
     */
    void updateMapCoordinateInfo(@Valid MapCoordinateInfoSaveReqVO updateReqVO);

    /**
     * 删除厂区地图定位详细信息
     *
     * @param id 编号
     */
    void deleteMapCoordinateInfo(Integer id);

    /**
     * 获得厂区地图定位详细信息
     *
     * @param id 编号
     * @return 厂区地图定位详细信息
     */
    MapCoordinateInfoDO getMapCoordinateInfo(Integer id);

    /**
     * 获得厂区地图定位详细信息分页
     *
     * @param pageReqVO 分页查询
     * @return 厂区地图定位详细信息分页
     */
    PageResult<MapCoordinateInfoDO> getMapCoordinateInfoPage(MapCoordinateInfoPageReqVO pageReqVO);

    /**
     * 查询所有的厂区定位
     * @return
     */
    List<MapCoordinateInfoAllVO> getMapCoordinateInfoAll(String factoryCode);

}