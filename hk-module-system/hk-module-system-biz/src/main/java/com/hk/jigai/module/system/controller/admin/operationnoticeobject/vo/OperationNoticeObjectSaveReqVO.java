package com.hk.jigai.module.system.controller.admin.operationnoticeobject.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 消息通知对象设置新增/修改 Request VO")
@Data
public class OperationNoticeObjectSaveReqVO {

    private Long id;

    @Schema(description = "消息通知对象", example = "17974")
    private List<Long> userId;

}