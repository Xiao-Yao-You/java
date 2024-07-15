package com.hk.jigai.module.system.controller.admin.meetingroominfo;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import static com.hk.jigai.framework.common.pojo.CommonResult.success;

import com.hk.jigai.framework.excel.core.util.ExcelUtils;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;
import static com.hk.jigai.framework.apilog.core.enums.OperateTypeEnum.*;

import com.hk.jigai.module.system.controller.admin.meetingroominfo.vo.*;
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.MeetingRoomInfoDO;
import com.hk.jigai.module.system.service.meetingroominfo.MeetingRoomInfoService;
import redis.clients.jedis.Jedis;

@Tag(name = "管理后台 - 会议室基本信息")
@RestController
@RequestMapping("/meeting")
@Validated
public class MeetingRoomInfoController {

    @Resource
    private MeetingRoomInfoService meetingRoomInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建会议室基本信息")
    @PreAuthorize("@ss.hasPermission('hk:meeting-room-info:create')")
    public CommonResult<Long> createMeetingRoomInfo(@Valid @RequestBody MeetingRoomInfoSaveReqVO createReqVO) {
        return success(meetingRoomInfoService.createMeetingRoomInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新会议室基本信息")
    @PreAuthorize("@ss.hasPermission('hk:meeting-room-info:update')")
    public CommonResult<Boolean> updateMeetingRoomInfo(@Valid @RequestBody MeetingRoomInfoSaveReqVO updateReqVO) {
        meetingRoomInfoService.updateMeetingRoomInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除会议室基本信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:meeting-room-info:delete')")
    public CommonResult<Boolean> deleteMeetingRoomInfo(@RequestParam("id") Long id) {
        meetingRoomInfoService.deleteMeetingRoomInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得会议室基本信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:meeting-room-info:query')")
    public CommonResult<MeetingRoomInfoRespVO> getMeetingRoomInfo(@RequestParam("id") Long id) {
        MeetingRoomInfoDO meetingRoomInfo = meetingRoomInfoService.getMeetingRoomInfo(id);
        return success(BeanUtils.toBean(meetingRoomInfo, MeetingRoomInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得会议室基本信息分页")
    @PreAuthorize("@ss.hasPermission('hk:meeting-room-info:query')")
    public CommonResult<PageResult<MeetingRoomInfoRespVO>> getMeetingRoomInfoPage(@Valid MeetingRoomInfoPageReqVO pageReqVO) {
        PageResult<MeetingRoomInfoDO> pageResult = meetingRoomInfoService.getMeetingRoomInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MeetingRoomInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出会议室基本信息 Excel")
    @PreAuthorize("@ss.hasPermission('hk:meeting-room-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMeetingRoomInfoExcel(@Valid MeetingRoomInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MeetingRoomInfoDO> list = meetingRoomInfoService.getMeetingRoomInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "会议室基本信息.xls", "数据", MeetingRoomInfoRespVO.class,
                        BeanUtils.toBean(list, MeetingRoomInfoRespVO.class));
    }

}