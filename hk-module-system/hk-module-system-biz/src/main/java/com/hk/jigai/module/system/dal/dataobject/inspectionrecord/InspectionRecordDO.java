package com.hk.jigai.module.system.dal.dataobject.inspectionrecord;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备巡检记录 DO
 *
 * @author 邵志伟
 */
@TableName("hk_inspection_record")
@KeySequence("hk_inspection_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InspectionRecordDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 设备编码
     */
    private String deviceCode;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备id	
     */
    private Long deviceId;
    /**
     * 设备型号
     */
    private String deviceModel;
    /**
     * 设备型号id
     */
    private Long deviceModelId;
    /**
     * 设备序列号
     */
    private String deviceSerial;
    /**
     * 设备二维码
     */
    private String deviceLabelCode;
    /**
     * 设备资产编号
     */
    private String deviceAssetNum;
    /**
     * 设备地点Id
     */
    private Long deviceAddressId;
    /**
     * 设备地点
     */
    private String deviceAddrss;
    /**
     * 设备位置
     */
    private String deviceLocation;
    /**
     * 任务状态
     */
    private Integer taskStatus;
    /**
     * 计划巡检日期
     */
    private LocalDateTime checkPlanTime;
    /**
     * 实际完成日期
     */
    private LocalDateTime completeTime;
    /**
     * 巡检人
     */
    private Long checkExecutor;
    /**
     * 巡检人姓名
     */
    private String checkExecutorName;
    /**
     * 巡检结果
     */
    private Integer checkResult;
    /**
     * 附件
     */
    private String filePath;
    /**
     * 备注
     */
    private String remark;

}