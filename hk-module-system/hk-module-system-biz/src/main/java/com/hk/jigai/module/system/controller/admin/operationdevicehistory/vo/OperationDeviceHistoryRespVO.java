package com.hk.jigai.module.system.controller.admin.operationdevicehistory.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.hk.jigai.module.system.dal.dataobject.operationdeviceaccessoryhistory.OperationDeviceAccessoryHistoryDO;
import com.hk.jigai.module.system.dal.dataobject.operationdevicepicturehistory.OperationDevicePictureHistoryDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Schema(description = "管理后台 - 运维设备 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperationDeviceHistoryRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "7628")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备编码")
    private String code;

    @Schema(description = "设备类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("设备类型")
    private Long deviceType;

    @Schema(description = "设备类型描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("设备类型描述")
    private String deviceTypeName;

    @Schema(description = "型号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("型号")
    private String model;

    @Schema(description = "标签code")
    @ExcelProperty("标签code")
    private String labelCode;

    @Schema(description = "状态 0:在用,1:闲置,2:报废", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态 0:在用,1:闲置,2:报废")
    private Integer status;

    @Schema(description = "所属单位 0:恒科,1:轩达,2:其他", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("所属单位 0:恒科,1:轩达,2:其他")
    private Integer company;

    @Schema(description = "序列号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("序列号")
    private String serialNumber;

    @Schema(description = "影响程度", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("影响程度")
    private String effectLevel;

    @Schema(description = "编号名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("编号名称")
    private Long numberName;

    @Schema(description = "资产编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("资产编号")
    private String assetNumber;

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

    @Schema(description = "是否需要巡检，0:是 1:否", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否需要巡检，0:是 1:否")
    private Integer needCheckFlag;

    @Schema(description = "设备部门", example = "23021")
    @ExcelProperty("设备部门")
    private Long deptId;

    @Schema(description = "设备部门名称", example = "王五")
    @ExcelProperty("设备部门名称")
    private String deptName;

    @Schema(description = "使用人", example = "24614")
    @ExcelProperty("使用人")
    private Long userId;

    @Schema(description = "使用地点", example = "558")
    @ExcelProperty("使用地点")
    private List<Long> addressId;

    @Schema(description = "地址名称")
    @ExcelProperty("地址名称")
    private String address;

    @Schema(description = "设备位置")
    @ExcelProperty("设备位置")
    private String location;

    @Schema(description = "ip1")
    @ExcelProperty("ip1")
    private String ip1;

    @Schema(description = "ip2")
    @ExcelProperty("ip2")
    private String ip2;

    @Schema(description = "设备分配登记人", example = "31961")
    @ExcelProperty("设备分配登记人")
    private Long registerUserId;

    @Schema(description = "设备分配登记人姓名", example = "赵六")
    @ExcelProperty("设备分配登记人姓名")
    private String registerUserName;

    @Schema(description = "设备分配登记时间")
    @ExcelProperty("设备分配登记时间")
    private LocalDateTime registerDate;

    @Schema(description = "报废时间")
    @ExcelProperty("报废时间")
    private LocalDateTime scrapDate;

    @Schema(description = "报废类型", example = "2")
    @ExcelProperty("报废类型")
    private String scrapType;

    @Schema(description = "报废处理人", example = "18533")
    @ExcelProperty("报废处理人")
    private Long scrapUserId;

    @Schema(description = "报废处理方式", example = "2")
    @ExcelProperty("报废处理方式")
    private String scrapDealType;

    @Schema(description = "报废说明", example = "随便")
    @ExcelProperty("报废说明")
    private String scrapRemark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "报废处理人", example = "张三")
    @ExcelProperty("报废处理人")
    private String scrapUserName;

    @Schema(description = "使用人姓名", example = "李四")
    @ExcelProperty("使用人姓名")
    private String userNickName;

    @Schema(description = "设备关联Id", example = "12929")
    @ExcelProperty("设备关联Id")
    private Long deviceId;

    List<OperationDevicePictureHistoryDO> operationDevicePictureHistoryDOS;

    List<OperationDeviceAccessoryHistoryDO> operationDeviceAccessoryHistoryDOS;

}