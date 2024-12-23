package com.hk.jigai.module.system.dal.dataobject.inspectionrecorddetail;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 巡检记录详情 DO
 *
 * @author 邵志伟
 */
@TableName("hk_inspection_record_detail")
@KeySequence("hk_inspection_record_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InspectionRecordDetailDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 记录id
     */
    private Long recordId;
    /**
     * 巡检项目id
     */
    private Long inspectionProjectId;
    /**
     * 巡检项目
     */
    private String inspectionProject;
    /**
     * 巡检项目指标
     */
    private String inspectionIndicators;
    /**
     * 巡检结构
     */
    private Integer result;
    /**
     * 说明/原因
     */
    private String remark;
    /**
     * 附件
     */
    private String filePath;
    /**
     * 工单id
     */
    private Long orderId;
    /**
     * 整改状态
     */
    private Integer 
correctionStatus;

}