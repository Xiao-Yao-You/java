package com.hk.jigai.module.system.dal.dataobject.meetingroominfo;

import com.hk.jigai.framework.mybatis.core.type.JsonLongSetTypeHandler;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户预定会议记录 DO
 *
 * @author 超级管理员
 */
@TableName("hk_user_book_meeting")
@KeySequence("hk_user_book_meeting_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBookMeetingDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户name
     */
    private Long userNickName;
    /**
     * 用户联系电话
     */
    private String userPhone;
    /**
     * 会议室ID
     */
    private Long meetingRoomId;
    /**
     * 会议室name
     */
    private Long meetingRoomName;

    /**
     * 名称
     */
    private String subject;
    /**
     * 会议日期
     */
    private LocalDate dateMeeting;
    /**
     * 会议开始
     */
    private Integer startTime;
    /**
     * 会议结束
     */
    private Integer endTime;
    /**
     * 设备数组
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> equipment;
    /**
     * 总人数
     */
    private Integer capacity;
    /**
     * 备注
     */
    private String remark;

    /**
     * 状态, 1 已撤销，0 正常
     */
    private Integer status;

}