package com.hk.jigai.module.system.service.mapcoordinateinfo;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo.*;
import com.hk.jigai.module.system.dal.dataobject.mapcoordinateinfo.MapCoordinateInfoDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.mapcoordinateinfo.MapCoordinateInfoMapper;

import java.util.List;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 厂区地图定位详细信息 Service 实现类
 *
 * @author 恒科信改
 */
@Service
@Validated
public class MapCoordinateInfoServiceImpl implements MapCoordinateInfoService {

    @Resource
    private MapCoordinateInfoMapper mapCoordinateInfoMapper;

    @Override
    public Integer createMapCoordinateInfo(MapCoordinateInfoSaveReqVO createReqVO) {
        // 插入
        MapCoordinateInfoDO mapCoordinateInfo = BeanUtils.toBean(createReqVO, MapCoordinateInfoDO.class);
        mapCoordinateInfoMapper.insert(mapCoordinateInfo);
        // 返回
        return mapCoordinateInfo.getId();
    }

    @Override
    public void updateMapCoordinateInfo(MapCoordinateInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateMapCoordinateInfoExists(updateReqVO.getId());
        // 更新
        MapCoordinateInfoDO updateObj = BeanUtils.toBean(updateReqVO, MapCoordinateInfoDO.class);
        mapCoordinateInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteMapCoordinateInfo(Integer id) {
        // 校验存在
        validateMapCoordinateInfoExists(id);
        // 删除
        mapCoordinateInfoMapper.deleteById(id);
    }

    private void validateMapCoordinateInfoExists(Integer id) {
        if (mapCoordinateInfoMapper.selectById(id) == null) {
            throw exception(MAP_COORDINATE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public MapCoordinateInfoDO getMapCoordinateInfo(Integer id) {
        return mapCoordinateInfoMapper.selectById(id);
    }

    @Override
    public PageResult<MapCoordinateInfoDO> getMapCoordinateInfoPage(MapCoordinateInfoPageReqVO pageReqVO) {
        return mapCoordinateInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MapCoordinateInfoAllVO> getMapCoordinateInfoAll() {
        return mapCoordinateInfoMapper.getMapCoordinateInfoAll();
    }

}