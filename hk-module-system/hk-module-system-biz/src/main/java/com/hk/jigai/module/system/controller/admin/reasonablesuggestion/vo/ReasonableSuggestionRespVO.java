package com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo;

import com.hk.jigai.module.system.controller.admin.operation.vo.OperationDevicePictureSaveReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 合理化建议 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReasonableSuggestionRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "28121")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "建议主题")
    @ExcelProperty("建议主题")
    private String title;

    @Schema(description = "建议类型", example = "1")
    @ExcelProperty("建议类型")
    private Integer suggestionType;

    @Schema(description = "申报人id", example = "31928")
    @ExcelProperty("申报人id")
    private Long userId;

    @Schema(description = "申报人", example = "李四")
    @ExcelProperty("申报人")
    private String nickname;

    @Schema(description = "申报人工号")
    @ExcelProperty("申报人工号")
    private String workNum;

    @Schema(description = "手机号")
    @ExcelProperty("手机号")
    private String phoneNum;

    @Schema(description = "申报部门_id", example = "17535")
    @ExcelProperty("申报部门_id")
    private Long deptId;

    @Schema(description = "申报部门", example = "王五")
    @ExcelProperty("申报部门")
    private String deptName;

    @Schema(description = "问题描述", example = "你猜")
    @ExcelProperty("问题描述")
    private String problemDescription;

    @Schema(description = "解决方案")
    @ExcelProperty("解决方案")
    private String solution;

    @Schema(description = "效果预估")
    @ExcelProperty("效果预估")
    private String effectEstimation;

    @Schema(description = "建议状态", example = "2")
    @ExcelProperty("建议状态")
    private Integer status;

    @Schema(description = "是否匿名")
    @ExcelProperty("是否匿名")
    private String anonymous;

    @Schema(description = "附件地址")
    @ExcelProperty("附件地址")
    private String filePath;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "附件地址")
    private List<OperationDevicePictureSaveReqVO> fileList;

}