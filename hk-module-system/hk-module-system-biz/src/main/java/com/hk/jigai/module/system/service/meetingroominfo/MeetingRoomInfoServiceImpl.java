package com.hk.jigai.module.system.service.meetingroominfo;

import com.hk.jigai.module.system.enums.ErrorCodeConstants;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.*;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingRoomInfoDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import com.hk.jigai.module.system.dal.mysql.meetingroominfo.MeetingRoomInfoMapper;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.*;

/**
 * 会议室基本信息 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class MeetingRoomInfoServiceImpl implements MeetingRoomInfoService {

    @Resource
    private MeetingRoomInfoMapper meetingRoomInfoMapper;

    @Override
    public Long createMeetingRoomInfo(MeetingRoomInfoSaveReqVO createReqVO) {
        // 插入
        MeetingRoomInfoDO meetingRoomInfo = BeanUtils.toBean(createReqVO, MeetingRoomInfoDO.class);
        meetingRoomInfoMapper.insert(meetingRoomInfo);
        // 返回
        return meetingRoomInfo.getId();
    }

    @Override
    public void updateMeetingRoomInfo(MeetingRoomInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateMeetingRoomInfoExists(updateReqVO.getId());
        // 更新
        MeetingRoomInfoDO updateObj = BeanUtils.toBean(updateReqVO, MeetingRoomInfoDO.class);
        meetingRoomInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteMeetingRoomInfo(Long id) {
        // 校验存在
        validateMeetingRoomInfoExists(id);
        // 删除
        meetingRoomInfoMapper.deleteById(id);
    }

    private void validateMeetingRoomInfoExists(Long id) {
        if (meetingRoomInfoMapper.selectById(id) == null) {
            throw exception(MEETING_ROOM_INFO_NOT_EXISTS);
        }
    }

    @Override
    public MeetingRoomInfoDO getMeetingRoomInfo(Long id) {
        return meetingRoomInfoMapper.selectById(id);
    }

    @Override
    public PageResult<MeetingRoomInfoDO> getMeetingRoomInfoPage(MeetingRoomInfoPageReqVO pageReqVO) {
        return meetingRoomInfoMapper.selectPage(pageReqVO);
    }

}