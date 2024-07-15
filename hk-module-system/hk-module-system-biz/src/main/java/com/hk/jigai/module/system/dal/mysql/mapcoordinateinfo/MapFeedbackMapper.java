package com.hk.jigai.module.system.dal.mysql.mapcoordinateinfo;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo.MapFeedbackPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.mapcoordinateinfo.MapFeedbackDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 厂区地图反馈 Mapper
 *
 * @author 恒科信改
 */
@Mapper
public interface MapFeedbackMapper extends BaseMapperX<MapFeedbackDO> {

    default PageResult<MapFeedbackDO> selectPage(MapFeedbackPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MapFeedbackDO>()
                .eqIfPresent(MapFeedbackDO::getMapId, reqVO.getMapId())
                .eqIfPresent(MapFeedbackDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(MapFeedbackDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MapFeedbackDO::getId));
    }

}