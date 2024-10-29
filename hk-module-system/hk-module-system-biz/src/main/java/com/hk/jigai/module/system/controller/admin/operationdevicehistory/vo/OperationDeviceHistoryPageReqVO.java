package com.hk.jigai.module.system.controller.admin.operationdevicehistory.vo;

import lombok.*;

import java.time.LocalDate;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 运维设备分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OperationDeviceHistoryPageReqVO extends PageParam {

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "设备编码")
    private String code;

    @Schema(description = "设备类型", example = "2")
    private Long deviceType;

    @Schema(description = "设备类型描述", example = "王五")
    private String deviceTypeName;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "标签code")
    private String labelCode;

    @Schema(description = "状态 0:在用,1:闲置,2:报废", example = "1")
    private Integer status;

    @Schema(description = "所属单位 0:恒科,1:轩达,2:其他")
    private Integer company;

    @Schema(description = "序列号")
    private String serialNumber;

    @Schema(description = "影响程度")
    private String effectLevel;

    @Schema(description = "编号名称", example = "赵六")
    private Long numberName;

    @Schema(description = "资产编号")
    private String assetNumber;

    @Schema(description = "mac地址1")
    private String macAddress1;

    @Schema(description = "mac地址2")
    private String macAddress2;

    @Schema(description = "生产日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] manufactureDate;

    @Schema(description = "质保日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] warrantyDate;

    @Schema(description = "是否需要巡检，0:是 1:否")
    private Integer needCheckFlag;

    @Schema(description = "设备部门", example = "23021")
    private Long deptId;

    @Schema(description = "设备部门名称", example = "王五")
    private String deptName;

    @Schema(description = "使用人", example = "24614")
    private Long userId;

    @Schema(description = "使用地点", example = "558")
    private Long addressId;

    @Schema(description = "地址名称")
    private String address;

    @Schema(description = "设备位置")
    private String location;

    @Schema(description = "ip1")
    private String ip1;

    @Schema(description = "ip2")
    private String ip2;

    @Schema(description = "设备分配登记人", example = "31961")
    private Long registerUserId;

    @Schema(description = "设备分配登记人姓名", example = "赵六")
    private String registerUserName;

    @Schema(description = "设备分配登记时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] registerDate;

    @Schema(description = "报废时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] scrapDate;

    @Schema(description = "报废类型", example = "2")
    private String scrapType;

    @Schema(description = "报废处理人", example = "18533")
    private Long scrapUserId;

    @Schema(description = "报废处理方式", example = "2")
    private String scrapDealType;

    @Schema(description = "报废说明", example = "随便")
    private String scrapRemark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "报废处理人", example = "张三")
    private String scrapUserName;

    @Schema(description = "使用人姓名", example = "李四")
    private String userNickName;

    @Schema(description = "设备关联Id", example = "12929")
    private Long deviceId;

}