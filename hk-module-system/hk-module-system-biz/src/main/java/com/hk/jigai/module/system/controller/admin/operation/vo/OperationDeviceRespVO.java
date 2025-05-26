package com.hk.jigai.module.system.controller.admin.operation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 运维设备 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperationDeviceRespVO {
    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19177")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("设备名称")
    private String name;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备编码")
    private String code;

    @Schema(description = "设备类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long deviceType;

    @Schema(description = "设备类型描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("设备类型")
    private String deviceTypeName;

    @Schema(description = "型号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long model;

    @ExcelProperty("设备型号")
    private String modelName;

    @Schema(description = "标签code")
    @ExcelProperty("二维码标签")
    private String labelCode;

    @Schema(description = "状态 0:在用,1:闲置,2:报废", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    // @ExcelProperty("状态 0:在用,1:闲置,2:报废")
    private Integer status;

    @Schema(description = "所属单位 0:恒科,1:轩达,2:其他", requiredMode = Schema.RequiredMode.REQUIRED)
    // @ExcelProperty("所属单位 0:恒科,1:轩达,2:其他")
    private Integer company;

    @Schema(description = "序列号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("序列号")
    private String serialNumber;

    @Schema(description = "影响程度", requiredMode = Schema.RequiredMode.REQUIRED)
    // @ExcelProperty("影响程度")
    private Long effectLevel;

    @Schema(description = "编码规则 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    private Long numberName;

    @Schema(description = "编码规则", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    private String numberNameStr;

    @Schema(description = "资产编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("资产编号")
    private String assetNumber;

    @Schema(description = "是否需要巡检，0:是 1:否", requiredMode = Schema.RequiredMode.REQUIRED)
    // @ExcelProperty("是否需要巡检，0:是 1:否")
    private Integer needCheckFlag;

    @Schema(description = "设备部门", example = "27960")
    // @ExcelProperty("设备部门")
    private Long deptId;

    @Schema(description = "设备部门名称", example = "王五")
    @ExcelProperty("使用部门")
    private String deptName;

    @Schema(description = "使用人", example = "27028")
    // @ExcelProperty("使用人")
    private Long userId;

    @ExcelProperty("使用人")
    private String userNickName;

    @Schema(description = "使用地点", example = "32586")
    private List<Long> addressId;

    @Schema(description = "使用地点名称", example = "32586")
    @ExcelProperty("所在地点")
    private String address;

    @Schema(description = "设备位置")
    @ExcelProperty("设备位置")
    private String location;

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

    @Schema(description = "设备分配登记人", example = "24873")
    // @ExcelProperty("设备分配登记人")
    private Long registerUserId;
    private String registerUserName;

    @Schema(description = "设备分配登记时间")
    // @ExcelProperty("设备分配登记时间")
    private LocalDateTime registerDate;

    @Schema(description = "报废时间")
    // @ExcelProperty("报废时间")
    private LocalDate scrapDate;

    @Schema(description = "报废类型", example = "1")
    // @ExcelProperty("报废类型")
    private String scrapType;

    @Schema(description = "报废处理人", example = "10844")
    // @ExcelProperty("报废处理人")
    private Long scrapUserId;

    private String scrapUserName;

    @Schema(description = "报废处理方式")
    // @ExcelProperty("报废处理方式")
    private String scrapDealType;

    @Schema(description = "报废说明", example = "你说的对")
    // @ExcelProperty("报废说明")
    private String scrapRemark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    // @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "生产日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("生产日期")
    private LocalDate manufactureDate;

    @Schema(description = "质保日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("质保日期")
    private LocalDate warrantyDate;

    @Schema(description = "设备图片list")
    private List<OperationDevicePictureSaveReqVO> pictureList;

    @Schema(description = "设备图片")
    private List<OperationDevicePictureSaveReqVO> devicePictureList;

    @Schema(description = "分配图片")
    private List<OperationDevicePictureSaveReqVO> distributePictureList;

    @Schema(description = "报废图片")
    private List<OperationDevicePictureSaveReqVO> scrapPictureList;

    @Schema(description = "配置list")
    private List<OperationDeviceAccessorySaveReqVO> accessoryList;

    @Schema(description = "问题类型明文")
    private String questionTypeStr;

    //1:新，2：旧
    private Integer flag;
}