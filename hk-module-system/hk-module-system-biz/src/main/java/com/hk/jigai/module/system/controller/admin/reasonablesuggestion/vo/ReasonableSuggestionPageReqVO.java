package com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo;

import com.hk.jigai.module.system.controller.admin.operation.vo.OperationDevicePictureSaveReqVO;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hk.jigai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 合理化建议分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ReasonableSuggestionPageReqVO extends PageParam {

    @Schema(description = "建议主题")
    private String title;

    @Schema(description = "建议类型", example = "1")
    private Integer suggestionType;

    @Schema(description = "申报人", example = "李四")
    private String nickname;

    @Schema(description = "申报部门", example = "王五")
    private String deptName;

    private String anonymous;

    @Schema(description = "建议状态", example = "2")
    private Integer status;

    @Schema(description = "附件地址")
    private String filePath;

    @Schema(description = "附件地址")
    private List<OperationDevicePictureSaveReqVO> fileList;

    private Long userId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;
    @Schema(description = "创建人")
    private Long creator;

}