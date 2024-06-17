package com.hk.jigai.module.system.dal.mysql.mapcoordinateinfo;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.dal.dataobject.mapcoordinateinfo.MapCoordinateInfoDO;
import com.hk.jigai.module.system.util.MapCoordinateInfo.MapCoordinateInfoConstant;
import org.apache.ibatis.annotations.Mapper;
import com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo.*;

/**
 * 厂区地图定位详细信息 Mapper
 *
 * @author 恒科信改
 */
@Mapper
public interface MapCoordinateInfoMapper extends BaseMapperX<MapCoordinateInfoDO> {

    default PageResult<MapCoordinateInfoDO> selectPage(MapCoordinateInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MapCoordinateInfoDO>()
                .eqIfPresent(MapCoordinateInfoDO::getZoneType, MapCoordinateInfoConstant.DEFAULT_ZONE_TYPE)
                .eqIfPresent(MapCoordinateInfoDO::getType, reqVO.getType())
                .eqIfPresent(MapCoordinateInfoDO::getDescription, reqVO.getDescription())
                .eqIfPresent(MapCoordinateInfoDO::getImage, reqVO.getImage())
                .eqIfPresent(MapCoordinateInfoDO::getLongitude, reqVO.getLongitude())
                .eqIfPresent(MapCoordinateInfoDO::getLatitude, reqVO.getLatitude())
                .betweenIfPresent(MapCoordinateInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MapCoordinateInfoDO::getId));
    }

    List<MapCoordinateInfoAllVO> getMapCoordinateInfoAll();

}