package com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo;

import com.hk.jigai.module.system.controller.admin.operation.vo.OperationDevicePictureSaveReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 合理化建议新增/修改 Request VO")
@Data
public class ReasonableSuggestionSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28121")
    private Long id;

    @Schema(description = "建议主题")
    private String title;

    @Schema(description = "建议类型", example = "1")
    private Integer suggestionType;

    @Schema(description = "申报人id", example = "31928")
    private Long userId;

    @Schema(description = "申报人", example = "李四")
    private String nickname;

    @Schema(description = "申报人工号")
    private String workNum;

    @Schema(description = "手机号")
    private String phoneNum;

    @Schema(description = "申报部门_id", example = "17535")
    private Long deptId;

    @Schema(description = "申报部门", example = "王五")
    private String deptName;

    @Schema(description = "问题描述", example = "你猜")
    private String problemDescription;

    @Schema(description = "解决方案")
    private String solution;

    @Schema(description = "效果预估")
    private String effectEstimation;

    @Schema(description = "建议状态", example = "2")
    private Integer status;

    @Schema(description = "是否匿名")
    private String anonymous;

    @Schema(description = "附件地址")
    private String filePath;

    @Schema(description = "附件地址")
    private List<OperationDevicePictureSaveReqVO> fileList;

    @Schema(description = "审核备注")
    private String remark;
}