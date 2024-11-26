package com.hk.jigai.module.system.controller.admin.operationdevicemodel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 设备型号 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperationDeviceModelRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "12398")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "型号")
    @ExcelProperty("型号")
    private String model;

    @Schema(description = "部门状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    private Long deviceTypeId;

}