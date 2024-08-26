package com.hk.jigai.module.system.dal.dataobject.userreport;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;
import com.hk.jigai.framework.mybatis.core.type.JsonLongSetTypeHandler;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@TableName(autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttentionOtherInfoDO extends BaseDO {

    /**
     * 内容
     */
    private String content;
    /**
     * 报告发起人id
     */
    private Long userId;
    /**
     * 报告发起人姓名昵称
     */
    private String userNickName;
    /**
     * 关联事项的内容
     */
    private String connectContent;
    /**
     * 汇报日期
     */
    private LocalDate dateReport;
    /**
     * 汇报对象集合
     */
    private Set<Long> reportObject;
    /**
     * 部门id
     */
    private Long deptId;
}
