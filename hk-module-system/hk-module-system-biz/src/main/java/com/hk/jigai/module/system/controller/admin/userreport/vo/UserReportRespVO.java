package com.hk.jigai.module.system.controller.admin.userreport.vo;

import com.hk.jigai.module.system.dal.dataobject.userreport.ReportAttentionDO;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportJobPlanDO;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportJobScheduleDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户汇报 Response VO")
@Data
@ExcelIgnoreUnannotated
public class UserReportRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "7668")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21060")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8060")
    @ExcelProperty("部门id")
    private Long deptId;

    @Schema(description = "汇报日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("汇报日期")
    private LocalDate dateReport;

    @Schema(description = "提交时间")
    @ExcelProperty("提交时间")
    private LocalDateTime commitTime;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "用户昵称", example = "王五")
    @ExcelProperty("用户昵称")
    private String userNickName;

    @Schema(description = "领导查看状态(00:未查看,01已查看)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("领导查看状态(00:未查看,01已查看)")
    private String checkStatus;

    @Schema(description = "类型(00:正常,01:补交,02:缺)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("类型(00:正常,01:补交,02:缺)")
    private String type;

    @Schema(description = "所属部门", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("所属部门")
    private String deptName;

    @Schema(description = "汇报对象")
    @ExcelProperty("汇报对象")
    private Set<Long> reportObject;

    @Schema(description = "汇报对象集合")
    private List<AdminUserDO> userList;

    @Schema(description = "工作进度集合")
    private List<ReportJobScheduleDO> reportJobScheduleDOList;

    @Schema(description = "工作计划集合")
    private List<ReportJobPlanDO> reportJobPlanDOList;

    @Schema(description = "工作进度关注集合")
    private List<ReportAttentionDO> reportAttentionList;
}