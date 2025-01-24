package com.hk.jigai.module.system.controller.admin.scenecode.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class SceneCodeImportExcelVO {

    @ExcelProperty("编码编号")
    private String keyCode;

    @ExcelProperty("编码名称")
    private String description;

    @ExcelProperty("前缀")
    private String prefix;

    @ExcelProperty("中缀")
    private String infix;

    @ExcelProperty("后缀")
    private String suffix;

    @ExcelProperty("重置规则")
    private String type;

    @ExcelProperty("起始值")
    private Integer start;

    @ExcelProperty("步长")
    private Integer step;

    @ExcelProperty("类型")
    private Integer codeType;

}