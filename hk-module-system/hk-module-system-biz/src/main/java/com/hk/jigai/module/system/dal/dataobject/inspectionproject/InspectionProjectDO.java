package com.hk.jigai.module.system.dal.dataobject.inspectionproject;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 巡检项目指标 DO
 *
 * @author 邵志伟
 */
@TableName("hk_inspection_project")
@KeySequence("hk_inspection_project_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InspectionProjectDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 巡检项目
     */
    private String inspectionProject;
    /**
     * 项目指标
     */
    private String inspectionIndicators;
    /**
     * 状态：0可用，1禁用
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

    /**
     * 父级id
     */
    private Long parentId;

}