package com.hk.jigai.module.system.controller.admin.userreport.vo;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.module.system.dal.dataobject.userreport.UserSummaryReportDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 用户汇报统计 Response VO")
@Data
public class SummaryRespVO {
    @Schema(description = "未提交用户姓名")
    private List<String> notSubmitUserNameList;

    @Schema(description = "汇报列表")
    private PageResult<UserSummaryReportDO> reportList;

}
