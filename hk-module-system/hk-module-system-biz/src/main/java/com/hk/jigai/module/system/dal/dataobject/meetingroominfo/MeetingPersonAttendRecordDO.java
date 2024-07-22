package com.hk.jigai.module.system.dal.dataobject.meetingroominfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 参会人员记录 DO
 *
 * @author 超级管理员
 */
@TableName("hk_meeting_person_attend_record")
@KeySequence("hk_meeting_person_attend_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingPersonAttendRecordDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 预定会议ID
     */
    private Long meetingBookId;
    /**
     * 参会人员
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String userNickName;

}