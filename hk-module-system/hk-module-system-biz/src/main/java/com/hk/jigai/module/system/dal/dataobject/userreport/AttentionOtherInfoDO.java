package com.hk.jigai.module.system.dal.dataobject.userreport;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;
import com.hk.jigai.framework.mybatis.core.type.JsonLongSetTypeHandler;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
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
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> reportObject;
    /**
     * 部门id
     */
    private Long deptId;
}
