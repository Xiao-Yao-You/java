package com.hk.jigai.module.system.dal.dataobject.reasonablesuggestion;

import com.hk.jigai.module.system.controller.admin.operation.vo.OperationDevicePictureSaveReqVO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 合理化建议 DO
 *
 * @author 邵志伟
 */
@TableName("hk_reasonable_suggestion")
@KeySequence("hk_reasonable_suggestion_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReasonableSuggestionDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 建议主题
     */
    private String title;
    /**
     * 建议类型
     */
    private Integer suggestionType;
    /**
     * 申报人id
     */
    private Long userId;
    /**
     * 申报人
     */
    private String nickname;
    /**
     * 申报人工号
     */
    private String workNum;
    /**
     * 手机号
     */
    private String phoneNum;
    /**
     * 申报部门_id
     */
    private Long deptId;
    /**
     * 申报部门
     */
    private String deptName;
    /**
     * 问题描述
     */
    private String problemDescription;
    /**
     * 解决方案
     */
    private String solution;
    /**
     * 效果预估
     */
    private String effectEstimation;
    /**
     * 建议状态
     */
    private Integer status;
    /**
     * 是否匿名
     */
    private String anonymous;
    /**
     * 附件地址
     */
    @TableField( updateStrategy = FieldStrategy.IGNORED)
    private String filePath;

    @TableField(exist = false)
    private List<OperationDevicePictureSaveReqVO> fileList;

}