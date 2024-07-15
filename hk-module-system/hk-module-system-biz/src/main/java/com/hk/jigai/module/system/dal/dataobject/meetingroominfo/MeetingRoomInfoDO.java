package com.hk.jigai.module.system.dal.dataobject.meetingroominfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 会议室基本信息 DO
 *
 * @author 超级管理员
 */
@TableName("hk_meeting_room_info")
@KeySequence("hk_meeting_room_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoomInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 位置(0:指挥部,1:厂区,2:生活区)
     */
    private Integer position;
    /**
     * 房间号
     */
    private String roomNo;
    /**
     * 容纳人数
     */
    private Integer capacity;
    /**
     * 状态(0:开启,1:禁用)
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}