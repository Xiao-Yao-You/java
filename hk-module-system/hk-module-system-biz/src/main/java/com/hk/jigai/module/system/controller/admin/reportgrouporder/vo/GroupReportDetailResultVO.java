package com.hk.jigai.module.system.controller.admin.reportgrouporder.vo;

import com.hk.jigai.module.system.dal.dataobject.reportgrouporderdetail.ReportGroupOrderDetailDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 小组工单处理月报详情 Response VO")
@Data
public class GroupReportDetailResultVO {

    @Schema(description = "小组月报集合")
    private List<ReportGroupOrderDetailDO> groupReportList;

}