package com.hk.jigai.module.system.controller.admin.operation.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 工单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OperationOrderPageReqVO extends PageParam {

    @Schema(description = "标题")
    private String title;

    @Schema(description = "工单编号")
    private String code;

    @Schema(description = "状态,00 待分配,01 待处理,02 进行中,03 挂起中,04 已处理,05 已完成,0501:无需处理,0502:无法排除故障,06 已撤销", example = "1")
    private String status;

    @Schema(description = "设备id", example = "15840")
    private Long deviceId;

    @Schema(description = "标签code")
    private String labelCode;

    @Schema(description = "设备名称", example = "赵六")
    private String deviceName;

    @Schema(description = "设备地点", example = "9013")
    private Long addressId;

    @Schema(description = "设备位置")
    private String location;

    @Schema(description = "提交人id", example = "31223")
    private Long submitUserId;

    @Schema(description = "提交人姓名", example = "王五")
    private String submitUserNickName;

    @Schema(description = "提交人手机号")
    private String submitUserMobile;

    @Schema(description = "请求类型", example = "2")
    private String requestType;

    @Schema(description = "问题类型", example = "2")
    private String questionType;

    @Schema(description = "紧急程度")
    private String level;

    @Schema(description = "描述")
    private String desc;

    @Schema(description = "类型,00 主动接单,01 指派工单,02 转交工单", example = "2")
    private String type;

    @Schema(description = "来源,00 线上报修,01 线下报修", example = "2")
    private String sourceType;

    @Schema(description = "处理人id", example = "31854")
    private Long dealUserId;

    @Schema(description = "处理人名称", example = "芋艿")
    private String dealUserNickName;

    @Schema(description = "分配人员id", example = "22442")
    private Long allocationUserId;

    @Schema(description = "分配人员昵称", example = "芋艿")
    private String allocationUserNickName;

    @Schema(description = "分配时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] allocationTime;

    @Schema(description = "分配耗时")
    private Long allocationConsume;

    @Schema(description = "现成确认时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] siteConfirmTime;

    @Schema(description = "现成确认耗时")
    private Long siteDonfirmConsume;

    @Schema(description = "处理完成时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] dealTime;

    @Schema(description = "运维人员处理耗时")
    private Long dealConsume;

    @Schema(description = "挂起时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] hangUpTime;

    @Schema(description = "挂起耗时")
    private Long hangUpConsume;

    @Schema(description = "完成时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] completeTime;

    @Schema(description = "完成耗时")
    private Long completeConsume;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}