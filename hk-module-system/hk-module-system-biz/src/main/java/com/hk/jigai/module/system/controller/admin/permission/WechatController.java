package com.hk.jigai.module.system.controller.admin.permission;

import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.module.system.service.wechat.MeetingReminderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.constraints.NotNull;

import static com.hk.jigai.framework.common.pojo.CommonResult.success;

@Tag(name = "微信 - 授权跳转接口")
@RestController
@RequestMapping("/wechat/open")
@Validated
public class WechatController {
    @Resource
    private MeetingReminderService meetingReminderService;

    @GetMapping("/getOpenid")
    @Operation(summary = "根据code查询openid")
    @PermitAll
    public CommonResult<String> wechatQueryOpenid(@NotNull String code) {
        return success(meetingReminderService.wechatQueryOpenid(code));
    }
}
