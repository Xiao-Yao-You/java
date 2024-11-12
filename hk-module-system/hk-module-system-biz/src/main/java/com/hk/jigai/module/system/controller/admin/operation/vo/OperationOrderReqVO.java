package com.hk.jigai.module.system.controller.admin.operation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 运维团队操作工单 Request VO")
@Data
public class OperationOrderReqVO {
    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "22882")
    private Long id;

    @Schema(description = "工单编号")
    private String code;

    @Schema(description = "工单id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22882")
    private Long orderId;

    @Schema(description = "id派单转交时必填", example = "31854")
    private Long userId;

    @Schema(description = "人名称派单转交时必填", example = "芋艿")
    private String userNickName;

    @Schema(description = "操作类型，00:派单,01:领单,0201:同组转交,0202:跨组转交,03:现场确认,04:挂起,05 已完成,0501:无需处理,0502:无法排除故障,06:撤销", example = "00")
    private Integer operateType;

    @Schema(description = "图片URL,现场确认必填")
    private List<OperationDevicePictureSaveReqVO> pictureList;

    @Schema(description = "说明或者原因", example = "芋艿")
    private String remark;

    @Schema(description = "处理人id")
    private Long operateUserId;

    @Schema(description = "处理人名称")
    private String operateUserNickName;

    @Schema(description = "开始时间")
    private LocalDateTime beginTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "操作方法")
    private String operateMethod;

    @Schema(description = "处理结果")
    private Integer completeResult;

    @Schema(description = "请求类型")
    private String requestType;

    @Schema(description = "问题类型")
    private Long questionType;

    @Schema(description = "紧急程度")
    private String level;


}