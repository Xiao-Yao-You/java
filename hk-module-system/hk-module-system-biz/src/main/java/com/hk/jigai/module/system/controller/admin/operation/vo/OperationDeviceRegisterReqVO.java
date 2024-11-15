package com.hk.jigai.module.system.controller.admin.operation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 运维设备新增/修改 Request VO")
@Data
public class OperationDeviceRegisterReqVO {

    @Schema(description = "设备主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "设备部门", requiredMode = Schema.RequiredMode.REQUIRED, example = "27960")
    private Long deptId;

    @Schema(description = "设备部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    private String deptName;

    @Schema(description = "使用人", example = "27028")
    private Long userId;

    private String userNickName;

    @Schema(description = "使用地点", requiredMode = Schema.RequiredMode.REQUIRED, example = "32586")
    private List<Long> addressIdList;

    @Schema(description = "地点")
    private String address;

    @Schema(description = "设备位置", requiredMode = Schema.RequiredMode.REQUIRED)
    private String location;

    @Schema(description = "ip1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ip1;

    @Schema(description = "ip2")
    private String ip2;

    @Schema(description = "设备分配登记人", requiredMode = Schema.RequiredMode.REQUIRED, example = "24873")
    private Long registerUserId;

    private String registerUserName;

    @Schema(description = "设备分配登记时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime registerDate;

    @Schema(description = "设备照片", requiredMode = Schema.RequiredMode.REQUIRED)
    List<OperationDevicePictureSaveReqVO> pictureList;
}