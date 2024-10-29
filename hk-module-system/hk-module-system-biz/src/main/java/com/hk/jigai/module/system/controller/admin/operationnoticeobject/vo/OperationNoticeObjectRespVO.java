package com.hk.jigai.module.system.controller.admin.operationnoticeobject.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 消息通知对象设置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperationNoticeObjectRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "11946")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "消息通知对象", example = "17974")
    @ExcelProperty("消息通知对象")
    private Long userId;

    private String nickName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}