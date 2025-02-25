package com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.poi.BorderStyleEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.hk.jigai.framework.excel.core.annotations.DictFormat;
import com.hk.jigai.framework.excel.core.convert.DictConvert;
import com.hk.jigai.module.system.controller.admin.reasonablesuggestion.export.ImageUrlConverter;
import com.hk.jigai.module.system.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 头背景设置
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, horizontalAlignment = HorizontalAlignmentEnum.CENTER, borderLeft = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN)
//标题高度
@HeadRowHeight(20)
//内容高度
@ContentRowHeight(60)
//内容居中,左、上、右、下的边框显示
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER, borderLeft = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN)

public class ReasonableSuggestionExportReqVO {

    @Schema(description = "建议主题")
    @ColumnWidth(50)
    @ExcelProperty("建议主题")
    private String title;

    @Schema(description = "建议类型", example = "1")
    @ColumnWidth(15)
    @ExcelProperty(value = "建议类型", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.SUGGESTION_TYPE)
    private Integer suggestionType;

    @Schema(description = "建议状态", example = "2")
    @ColumnWidth(15)
    @ExcelProperty(value = "建议状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.SUGGESTION_STATUS)
    private Integer status;

    @Schema(description = "申报人", example = "李四")
    @ColumnWidth(15)
    @ExcelProperty("申报人")
    private String nickname;

    @Schema(description = "申报人工号")
    @ColumnWidth(15)
    @ExcelProperty("申报人工号")
    private String workNum;

    @Schema(description = "手机号")
    @ColumnWidth(15)
    @ExcelProperty("手机号")
    private String phoneNum;

    @Schema(description = "申报部门", example = "王五")
    @ColumnWidth(15)
    @ExcelProperty("申报部门")
    private String deptName;

    @Schema(description = "问题描述", example = "你猜")
    @ColumnWidth(30)
    @ExcelProperty("问题描述")
    private String problemDescription;

    @Schema(description = "解决方案")
    @ColumnWidth(30)
    @ExcelProperty("解决方案")
    private String solution;

    @Schema(description = "效果预估")
    @ColumnWidth(30)
    @ExcelProperty("效果预估")
    private String effectEstimation;

    @Schema(description = "是否匿名")
    @ColumnWidth(5)
    @ExcelProperty(value = "是否匿名",converter = DictConvert.class)
    @DictFormat(DictTypeConstants.ANONYMOUS)
    private String anonymous;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ColumnWidth(25)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty(value = "照片", converter = ImageUrlConverter.class)
    @ColumnWidth(50)
    private List<URL> imgList;

}
