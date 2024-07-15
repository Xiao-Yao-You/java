package com.hk.jigai.module.system.controller.admin.meetingroominfo;

import com.hk.jigai.module.system.service.meetingroominfo.MeetingPersonAttendRecordService;
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
import com.hk.jigai.module.system.dal.dataobject.meetingroominfo.UserBookMeetingDO;
import com.hk.jigai.module.system.service.meetingroominfo.UserBookMeetingService;

@Tag(name = "管理后台 - 用户预定会议记录")
@RestController
@RequestMapping("/bookMeeting")
@Validated
public class UserBookMeetingController {

    @Resource
    private UserBookMeetingService userBookMeetingService;

    @Resource
    private MeetingPersonAttendRecordService meetingPersonAttendRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建用户预定会议记录")
    @PreAuthorize("@ss.hasPermission('hk:user-book-meeting:create')")
    public CommonResult<Long> createUserBookMeeting(@Valid @RequestBody UserBookMeetingSaveReqVO createReqVO) {
        return success(userBookMeetingService.createUserBookMeeting(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户预定会议记录")
    @PreAuthorize("@ss.hasPermission('hk:user-book-meeting:update')")
    public CommonResult<Boolean> updateUserBookMeeting(@Valid @RequestBody UserBookMeetingSaveReqVO updateReqVO) {
        userBookMeetingService.updateUserBookMeeting(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户预定会议记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:user-book-meeting:delete')")
    public CommonResult<Boolean> deleteUserBookMeeting(@RequestParam("id") Long id) {
        userBookMeetingService.deleteUserBookMeeting(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户预定会议记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:user-book-meeting:query')")
    public CommonResult<UserBookMeetingRespVO> getUserBookMeeting(@RequestParam("id") Long id) {
        UserBookMeetingDO userBookMeeting = userBookMeetingService.getUserBookMeeting(id);
        UserBookMeetingRespVO result = BeanUtils.toBean(userBookMeeting, UserBookMeetingRespVO.class);
        //参会人员列表
        if(result != null){
            List<Long> userIdList = meetingPersonAttendRecordService.getMeetingPersonAttendRecord(id);
            result.setJoinUserIdList(userIdList);
        }
        return success(result);
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户预定会议记录分页")
    @PreAuthorize("@ss.hasPermission('hk:user-book-meeting:query')")
    public CommonResult<PageResult<UserBookMeetingRespVO>> getUserBookMeetingPage(@Valid UserBookMeetingPageReqVO pageReqVO) {
        PageResult<UserBookMeetingDO> pageResult = userBookMeetingService.getUserBookMeetingPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, UserBookMeetingRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户预定会议记录 Excel")
    @PreAuthorize("@ss.hasPermission('hk:user-book-meeting:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportUserBookMeetingExcel(@Valid UserBookMeetingPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<UserBookMeetingDO> list = userBookMeetingService.getUserBookMeetingPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户预定会议记录.xls", "数据", UserBookMeetingRespVO.class,
                        BeanUtils.toBean(list, UserBookMeetingRespVO.class));
    }

}