package com.hk.jigai.module.system.controller.admin.operation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 工单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperationOrderRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "22882")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "工单编号")
    @ExcelProperty("工单编号")
    private String code;

    @Schema(description = "标题")
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "状态,00 待分配,01 待处理,02 进行中,03 挂起中,04 已处理,05 已完成,0501:无需处理,0502:无法排除故障,06 已撤销", example = "1")
    @ExcelProperty("状态,00 待分配,01 待处理,02 进行中,03 挂起中,04 已处理,05 已完成,0501:无需处理,0502:无法排除故障,06 已撤销")
    private String status;

    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15840")
    @ExcelProperty("设备id")
    private Long deviceId;

    @Schema(description = "标签code")
    @ExcelProperty("标签code")
    private String labelCode;

    @Schema(description = "设备名称", example = "赵六")
    @ExcelProperty("设备名称")
    private String deviceName;

    @Schema(description = "设备地点", example = "9013")
    @ExcelProperty("设备地点")
    private Long addressId;

    @Schema(description = "设备位置")
    @ExcelProperty("设备位置")
    private String location;

    @Schema(description = "提交人id", example = "31223")
    @ExcelProperty("提交人id")
    private Long submitUserId;

    @Schema(description = "提交人姓名", example = "王五")
    @ExcelProperty("提交人姓名")
    private String submitUserNickName;

    @Schema(description = "提交人手机号")
    @ExcelProperty("提交人手机号")
    private String submitUserMobile;

    @Schema(description = "请求类型", example = "2")
    @ExcelProperty("请求类型")
    private String requestType;

    @Schema(description = "问题类型", example = "2")
    @ExcelProperty("问题类型")
    private Long questionType;

    @Schema(description = "紧急程度")
    @ExcelProperty("紧急程度")
    private String level;

    @Schema(description = "描述")
    @ExcelProperty("描述")
    private String description;

    @Schema(description = "类型,00 主动接单,01 指派工单,02 转交工单", example = "2")
    @ExcelProperty("类型,00 主动接单,01 指派工单,02 转交工单")
    private String type;

    @Schema(description = "来源,00 线上报修,01 线下报修", example = "2")
    @ExcelProperty("来源,1 线上报修,2 线下报修")
    private Integer sourceType;

    @Schema(description = "处理人id", example = "31854")
    @ExcelProperty("处理人id")
    private Long dealUserId;

    @Schema(description = "处理人名称", example = "芋艿")
    @ExcelProperty("处理人名称")
    private String dealUserNickName;

    @Schema(description = "分配人员id", example = "22442")
    @ExcelProperty("分配人员id")
    private Long allocationUserId;

    @Schema(description = "分配人员昵称", example = "芋艿")
    @ExcelProperty("分配人员昵称")
    private String allocationUserNickName;

    @Schema(description = "分配时间")
    @ExcelProperty("分配时间")
    private LocalDateTime allocationTime;

    @Schema(description = "分配耗时")
    @ExcelProperty("分配耗时")
    private Long allocationConsume;

    @Schema(description = "现成确认时间")
    @ExcelProperty("现成确认时间")
    private LocalDateTime siteConfirmTime;

    @Schema(description = "现成确认耗时")
    @ExcelProperty("现成确认耗时")
    private Long siteDonfirmConsume;

    @Schema(description = "处理完成时间")
    @ExcelProperty("处理完成时间")
    private LocalDateTime dealTime;

    @Schema(description = "运维人员处理耗时")
    @ExcelProperty("运维人员处理耗时")
    private Long dealConsume;

    @Schema(description = "挂起时间")
    @ExcelProperty("挂起时间")
    private LocalDateTime hangUpTime;

    @Schema(description = "挂起耗时")
    @ExcelProperty("挂起耗时")
    private Long hangUpConsume;

    @Schema(description = "完成时间")
    @ExcelProperty("完成时间")
    private LocalDateTime completeTime;

    @Schema(description = "完成耗时")
    @ExcelProperty("完成耗时")
    private Long completeConsume;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "问题类型明文")
    private String questionTypeStr;

}