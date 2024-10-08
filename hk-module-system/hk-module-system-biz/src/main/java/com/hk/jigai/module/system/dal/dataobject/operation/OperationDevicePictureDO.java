package com.hk.jigai.module.system.dal.dataobject.operation;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 运维设备照片表 DO
 *
 * @author 超级管理员
 */
@TableName("hk_operation_device_picture")
@KeySequence("hk_operation_device_picture_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDevicePictureDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 设备id
     */
    private Long deviceId;
    /**
     * 类型0:设备照片,1:现场照片,2:报废照片
     */
    private String type;
    /**
     * 图片URL
     */
    private String url;

}