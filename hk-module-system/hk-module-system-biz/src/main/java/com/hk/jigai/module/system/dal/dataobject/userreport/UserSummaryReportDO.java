package com.hk.jigai.module.system.dal.dataobject.userreport;

import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserSummaryReportDO extends BaseDO {
    @Schema(description = "用户昵称")
    private String userNickName;

    @Schema(description = "汇报id")
    private Long userReportId;

    @Schema(description = "汇报日期")
    private LocalDate dateReport;

    @Schema(description = "提交时间")
    private LocalDateTime commitTime;

    @Schema(description = "工作内容")
    private String content;

    @Schema(description = "完成情况")
    private String situation;

    @Schema(description = "进度id")
    private Long reportScheduleId;

    @Schema(description = "关联待跟进的事项工作内容")
    private String connectContent;

    @Schema(description = "关联待跟进的事项id")
    private Long connectId;

    @Schema(description = "关注userId,有值表示已关注")
    private Long attentionUserId;

    @Schema(description = "0:工作进度,1:工作计划")
    private Long type;

    @Schema(description = "部门领导id")
    private Long userId;

    @Schema(description = "部门领导昵称")
    private String userName;

    @Schema(description = "部门领导批复")
    private String reply;

    @Schema(description = "跟进状态,0:未跟进,1:已跟进")
    private String replyStatus;

}