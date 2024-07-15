package com.hk.jigai.module.system.controller.admin.userreport.vo;

import com.hk.jigai.module.system.dal.dataobject.userreport.ReportJobPlanDO;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportJobScheduleDO;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportObjectDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 用户汇报新增/修改 Request VO")
@Data
public class UserReportSaveReqVO {

    @Schema(description = "主键", example = "7668")
    private Long id;

    @Schema(description = "部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8060")
    @NotNull(message = "部门id不能为空")
    private Long deptId;

    @Schema(description = "报告对象list", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ReportObjectDO> reportObjectList;

    @Schema(description = "汇报日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "汇报日期不能为空")
    private LocalDate dateReport;

    @Schema(description = "类型(00:正常,01:补交)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "类型(00:正常,01:补交)不能为空")
    private String type;

    @Schema(description = "提交时间")
    private LocalDateTime commitTime;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "工作进度列表")
    private List<ReportJobScheduleDO> scheduleList;

    @Schema(description = "工作计划列表")
    private List<ReportJobPlanDO> planList;

}