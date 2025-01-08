package com.hk.jigai.module.system.controller.admin.prizedrawoutuser.vo;

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
public class PrizeDrawOutUserImportExcelVO {
    @ExcelProperty("姓名")
    private String nickname;

    @ExcelProperty("部门")
    private String dept;

    @ExcelProperty("工号")
    private String workNum;

    @ExcelProperty("手机号")
    private String mobile;


}