package com.hk.jigai.module.system.controller.admin.user.vo.user;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.hk.jigai.module.system.framework.operatelog.core.DeptParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "管理后台 - 用户信息下部分信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDeptRespVO {

    @Schema(description = "部门编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @DiffLogField(name = "部门", function = DeptParseFunction.NAME)
    private Long id;

    @Schema(description = "部门名称", example = "恒科")
    private String name;

    @Schema(description = "父部门 ID", example = "1024")
    private Long parentId;
}
