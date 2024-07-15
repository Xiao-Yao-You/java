package com.hk.jigai.module.system.service.mapcoordinateinfo;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.module.system.dal.dataobject.mapcoordinateinfo.MapFeedbackDO;
import com.hk.jigai.module.system.dal.mysql.mapcoordinateinfo.MapFeedbackMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo.*;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;
/**
 * 厂区地图反馈 Service 实现类
 *
 * @author 恒科信改
 */
@Service
@Validated
public class MapFeedbackServiceImpl implements MapFeedbackService {

    @Resource
    private MapFeedbackMapper mapFeedbackMapper;

    @Override
    public Integer createMapFeedback(MapFeedbackSaveReqVO createReqVO) {
        // 插入
        MapFeedbackDO mapFeedback = BeanUtils.toBean(createReqVO, MapFeedbackDO.class);
        mapFeedbackMapper.insert(mapFeedback);
        // 返回
        return mapFeedback.getId();
    }

    @Override
    public void updateMapFeedback(MapFeedbackSaveReqVO updateReqVO) {
        // 校验存在
        validateMapFeedbackExists(updateReqVO.getId());
        // 更新
        MapFeedbackDO updateObj = BeanUtils.toBean(updateReqVO, MapFeedbackDO.class);
        mapFeedbackMapper.updateById(updateObj);
    }

    @Override
    public void deleteMapFeedback(Integer id) {
        // 校验存在
        validateMapFeedbackExists(id);
        // 删除
        mapFeedbackMapper.deleteById(id);
    }

    private void validateMapFeedbackExists(Integer id) {
        if (mapFeedbackMapper.selectById(id) == null) {
            throw exception(MAP_FEEDBACK_NOT_EXISTS);
        }
    }

    @Override
    public MapFeedbackDO getMapFeedback(Integer id) {
        return mapFeedbackMapper.selectById(id);
    }

    @Override
    public PageResult<MapFeedbackDO> getMapFeedbackPage(MapFeedbackPageReqVO pageReqVO) {
        return mapFeedbackMapper.selectPage(pageReqVO);
    }
}