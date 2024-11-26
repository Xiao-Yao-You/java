package com.hk.jigai.module.system.controller.admin.operationdevicemodel.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class ModelImportExcelVO {

    @ExcelProperty("设备类型")
    private String deviceType;

    @ExcelProperty("型号")
    private String model;

    @ExcelProperty("状态")
    private Integer status;

    @ExcelProperty("备注")
    private String remark;

}