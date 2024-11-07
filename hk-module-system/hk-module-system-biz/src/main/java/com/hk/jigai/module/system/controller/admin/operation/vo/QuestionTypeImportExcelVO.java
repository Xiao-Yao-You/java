package com.hk.jigai.module.system.controller.admin.operation.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class QuestionTypeImportExcelVO {
    @ExcelProperty("问题编号")
    private String questionCode;

    @ExcelProperty("问题名称")
    private String name;

    @ExcelProperty("父级编号")
    private String parentCode;

    @ExcelProperty("设备类型")
    private String deviceType;

    @ExcelProperty("问题类型")
    private String questionType;

    @ExcelProperty("问题描述")
    private String description;

}