package com.hk.jigai.module.system.service.wechat;

import java.time.LocalDateTime;

public interface MeetingReminderService {

    /**
     * 创建新会议时
     * @param meetingId
     * @param meetingTime
     */
    void scheduleReminder(Long meetingId, LocalDateTime meetingTime);

    /**
     * 任何会议有调整是
     * @param meetingId
     */
    void updateReminder(Long meetingId);

    /**
     * openid
     * @param code
     * @return
     */
    String wechatQueryOpenid(String code);
}
