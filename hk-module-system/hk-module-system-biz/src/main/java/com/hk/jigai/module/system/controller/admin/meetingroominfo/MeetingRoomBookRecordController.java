package com.hk.jigai.module.system.controller.admin.meetingroominfo;

import com.hk.jigai.framework.common.pojo.CommonResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import static com.hk.jigai.framework.common.pojo.CommonResult.success;
import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.*;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingRoomBookRecordDO;
import com.hk.jigai.module.system.service.meetingroominfo.MeetingRoomBookRecordService;

import java.util.List;

@Tag(name = "管理后台 - 会议室预定记录")
@RestController
@RequestMapping("/meetingRoomBookRecord")
@Validated
public class MeetingRoomBookRecordController {

    @Resource
    private MeetingRoomBookRecordService meetingRoomBookRecordService;

    @GetMapping("/get")
    @Operation(summary = "获得会议室预定记录")
    @PreAuthorize("@ss.hasPermission('hk:meeting-room-book-record:query')")
    public CommonResult<List<MeetingRoomBookRecordRespVO>> getMeetingRoomBookRecord(MeetingRoomBookRecordReqVO req) {
              return success(meetingRoomBookRecordService.getMeetingRoomBookRecord(req));
    }


}