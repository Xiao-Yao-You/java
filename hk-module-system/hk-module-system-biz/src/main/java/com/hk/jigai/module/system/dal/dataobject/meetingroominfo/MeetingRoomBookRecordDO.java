package com.hk.jigai.module.system.dal.dataobject.meetingroominfo;

import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 会议室预定记录 DO
 *
 * @author 超级管理员
 */
@TableName("hk_meeting_room_book_record")
@KeySequence("hk_meeting_room_book_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoomBookRecordDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 会议室ID
     */
    private Long meetingRoomId;
    /**
     * 会议预定ID
     */
    private Long meetingBookId;
    /**
     * 会议日期
     */
    private LocalDate dateMeeting;
    /**
     * 时间节点，共48个
     */
    private Long timeKey;
    /**
     * 名称
     */
    private String subject;
    /**
     * 用户联系电话
     */
    private String userPhone;

    /**
     * 状态(0:正常,1:禁用)
     */
    private Integer status;

    /**
     * 其他参会者
     */
    private String otherAttend;

}