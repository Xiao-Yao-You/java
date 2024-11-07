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
public class AddressImportExcelVO {
    @ExcelProperty("序号")
    private String addressCode;

    @ExcelProperty("地点名称")
    private String addressName;

    @ExcelProperty("父级地点")
    private String parentCode;

    @ExcelProperty("软件负责人")
    private String softManager;

    @ExcelProperty("硬件负责人")
    private String hardManager;

}