package com.hk.jigai.module.system.controller.admin.meetingroominfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 会议室基本信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MeetingRoomInfoRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21588")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "位置(0:指挥部,1:厂区,2:生活区)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("位置(0:指挥部,1:厂区,2:生活区)")
    private Integer position;

    @Schema(description = "房间号")
    @ExcelProperty("房间号")
    private String roomNo;

    @Schema(description = "容纳人数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("容纳人数")
    private Integer capacity;

    @Schema(description = "状态(0:开启,1:禁用)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态(0:开启,1:禁用)")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}