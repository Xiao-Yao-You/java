package com.hk.jigai.module.system.controller.admin.operation.vo;

import com.hk.jigai.framework.excel.core.annotations.DictFormat;
import com.hk.jigai.framework.excel.core.convert.DictConvert;
import com.hk.jigai.module.system.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

@Schema(description = "管理后台 - 运维设备档案 Excel Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperationDeviceRespExportVO {
    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("设备名称")
    private String name;

    @Schema(description = "设备类型描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("设备类型")
    private String deviceTypeName;

    @Schema(description = "状态 0:在用,1:闲置,2:报废", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "设备状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.REPAIR_DEVICE_STATUS)
    private Integer status;

    @ExcelProperty("设备型号")
    private String modelName;

    @Schema(description = "标签code")
    @ExcelProperty("二维码标签")
    private String labelCode;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备编码")
    private String code;

    @Schema(description = "序列号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("序列号")
    private String serialNumber;

    @Schema(description = "资产编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("资产编号")
    private String assetNumber;

    @Schema(description = "是否安装杀毒软件，0:是 1:否")
    @ExcelProperty(value = "是否安装杀毒软件", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.REPAIR_DEVICE_ANTIVIRUS)
    private Integer antivirusInstalled;

    @Schema(description = "所属公司 0:恒科,1:轩达,2:其他", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "所属公司", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.ASSETS_COMPANY)
    private Integer company;

    @ExcelProperty("使用人")
    private String userNickName;

    @Schema(description = "设备位置")
    @ExcelProperty("设备位置")
    private String location;

    @Schema(description = "设备部门名称", example = "王五")
    @ExcelProperty("使用部门")
    private String deptName;

    @Schema(description = "使用地点名称", example = "32586")
    @ExcelProperty("所在地点")
    private String address;

    @Schema(description = "ip1")
    @ExcelProperty("IP 1")
    private String ip1;

    @Schema(description = "IP 2")
    @ExcelProperty("IP 2")
    private String ip2;

    @Schema(description = "mac地址1", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("mac地址1")
    private String macAddress1;

    @Schema(description = "mac地址2")
    @ExcelProperty("mac地址2")
    private String macAddress2;

    @Schema(description = "生产日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("生产日期")
    private LocalDate manufactureDate;

    @Schema(description = "质保日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("质保日期")
    private LocalDate warrantyDate;
}
