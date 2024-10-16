package com.hk.jigai.module.system.dal.dataobject.operation;

import com.hk.jigai.module.system.controller.admin.operation.vo.OperationDevicePictureSaveReqVO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工单操作记录 DO
 *
 * @author 超级管理员
 */
@TableName("hk_operation_order_operate_record")
@KeySequence("hk_operation_order_operate_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationOrderOperateRecordDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 工单id
     */
    private Long orderId;
    /**
     * 操作类型，00:派单,01:领单,0201:同组转交,0202:跨组转交,03:现场确认,04:挂起,05 已完成,0501:无需处理,0502:无法排除故障,06:撤销
     */
    private String operateType;
    /**
     * 操作人员
     */
    private Long operateUserId;
    /**
     * 操作人员
     */
    private String operateUserNickName;
    /**
     * 开始时间
     */
    private LocalDateTime beginTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 耗时
     */
    private Long spendTime;
    /**
     * 人员
     */
    private Long userId;
    /**
     * 人员昵称
     */
    private String userNickName;
    /**
     * 说明/原因
     */
    private String remark;

    @TableField(exist = false)
    private List<OperationDevicePictureSaveReqVO> picList;

}