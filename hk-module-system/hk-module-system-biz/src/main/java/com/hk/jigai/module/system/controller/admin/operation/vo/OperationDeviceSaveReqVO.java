package com.hk.jigai.module.system.controller.admin.operation.vo;

import com.hk.jigai.module.system.dal.dataobject.operation.OperationDeviceAccessoryDO;
import com.hk.jigai.module.system.dal.dataobject.operation.OperationDevicePictureDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 运维设备新增/修改 Request VO")
@Data
public class OperationDeviceSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "19177")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
//    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotEmpty(message = "设备编码不能为空")
    private String code;

    @Schema(description = "设备类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
//    @NotNull(message = "设备类型不能为空")
    private Long deviceType;

    @Schema(description = "设备类型描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
//    @NotEmpty(message = "设备类型描述不能为空")
    private String deviceTypeName;

    @Schema(description = "型号", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotEmpty(message = "型号不能为空")
    private String model;

    @Schema(description = "标签code")
    private String labelCode;

    @Schema(description = "状态 0:在用,1:闲置,2:报废", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
//    @NotNull(message = "状态 0:在用,1:闲置,2:报废不能为空")
    private Integer status;

    @Schema(description = "所属单位 0:恒科,1:轩达,2:其他", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotNull(message = "所属单位 0:恒科,1:轩达,2:其他不能为空")
    private Integer company;

    @Schema(description = "是否安装杀毒软件，0:是 1:否", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer antivirusInstalled;

    @Schema(description = "序列号", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotEmpty(message = "序列号不能为空")
    private String serialNumber;

    @Schema(description = "影响程度", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotEmpty(message = "影响程度不能为空")
    private String effectLevel;

    @Schema(description = "编号名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
//    @NotEmpty(message = "编号名称不能为空")
    private Long numberName;

    @Schema(description = "影响程度", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotEmpty(message = "影响程度不能为空")
    private String assetNumber;

    @Schema(description = "mac地址1", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotEmpty(message = "mac地址1不能为空")
    private String macAddress1;

    @Schema(description = "mac地址2")
    private String macAddress2;

    @Schema(description = "生产日期", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotNull(message = "生产日期不能为空")
    private LocalDate manufactureDate;

    @Schema(description = "质保日期", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotNull(message = "质保日期不能为空")
    private LocalDate warrantyDate;

    @Schema(description = "是否需要巡检，0:是 1:否", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotNull(message = "是否需要巡检，0:是 1:否不能为空")
    private Integer needCheckFlag;

    @Schema(description = "设备部门", example = "27960")
    private Long deptId;

    @Schema(description = "设备部门名称", example = "王五")
    private String deptName;

    @Schema(description = "使用人", example = "27028")
    private Long userId;

    @Schema(description = "使用地点", example = "32586")
    private List<Long> addressId;
    private String address;

    @Schema(description = "设备位置")
    private String location;

    @Schema(description = "ip1")
    private String ip1;

    @Schema(description = "ip2")
    private String ip2;

    @Schema(description = "设备分配登记人", example = "24873")
    private Long registerUserId;
    private String registerUserName;

    @Schema(description = "设备分配登记时间")
    private LocalDateTime registerDate;

    @Schema(description = "报废时间")
    private LocalDate scrapDate;

    @Schema(description = "报废类型", example = "1")
    private String scrapType;

    @Schema(description = "报废处理人", example = "10844")
    private Long scrapUserId;

    @Schema(description = "报废处理人", requiredMode = Schema.RequiredMode.REQUIRED, example = "10844")
    private String scrapUserName;

    @Schema(description = "报废处理方式")
    private String scrapDealType;

    @Schema(description = "报废说明")
    private String scrapRemark;

    @Schema(description = "使用人姓名")
    private String userNickName;

    @Schema(description = "设备图片list")
    private List<OperationDevicePictureSaveReqVO> pictureList;

    @Schema(description = "配置list")
    private List<OperationDeviceAccessorySaveReqVO> accessoryList;

}