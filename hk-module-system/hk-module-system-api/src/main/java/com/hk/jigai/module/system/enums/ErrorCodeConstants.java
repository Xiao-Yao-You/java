package com.hk.jigai.module.system.enums;

import com.hk.jigai.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类
 * <p>
 * system 系统，使用 1-002-000-000 段
 */
public interface ErrorCodeConstants {
    // ========== AUTH 模块 1-002-000-000 ==========
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1_002_000_000, "登录失败，账号密码不正确");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1_002_000_001, "登录失败，账号被禁用");
    ErrorCode AUTH_LOGIN_CAPTCHA_CODE_ERROR = new ErrorCode(1_002_000_004, "验证码不正确，原因：{}");
    ErrorCode AUTH_THIRD_LOGIN_NOT_BIND = new ErrorCode(1_002_000_005, "未绑定账号，需要进行绑定");
    ErrorCode AUTH_TOKEN_EXPIRED = new ErrorCode(1_002_000_006, "Token 已经过期");
    ErrorCode AUTH_MOBILE_NOT_EXISTS = new ErrorCode(1_002_000_007, "手机号不存在");
    ErrorCode MINI_APP_CODE_NOT_SEND = new ErrorCode(1_002_000_008, "小程序code未传递");
    ErrorCode MINI_APP_OPEN_ID_ERROR = new ErrorCode(1_002_000_009, "获取小程序openId失败");
    ErrorCode MINI_APP_ADMIN_ERROR = new ErrorCode(1_002_000_010, "超级管理员请使用账号密码登录");
    ErrorCode OPEN_ID_NOT_MATCH_ACCOUNT = new ErrorCode(1_002_000_011, "微信和系统账号不匹配");

    // ========== 菜单模块 1-002-001-000 ==========
    ErrorCode MENU_NAME_DUPLICATE = new ErrorCode(1_002_001_000, "已经存在该名字的菜单");
    ErrorCode MENU_PARENT_NOT_EXISTS = new ErrorCode(1_002_001_001, "父菜单不存在");
    ErrorCode MENU_PARENT_ERROR = new ErrorCode(1_002_001_002, "不能设置自己为父菜单");
    ErrorCode MENU_NOT_EXISTS = new ErrorCode(1_002_001_003, "菜单不存在");
    ErrorCode MENU_EXISTS_CHILDREN = new ErrorCode(1_002_001_004, "存在子菜单，无法删除");
    ErrorCode MENU_PARENT_NOT_DIR_OR_MENU = new ErrorCode(1_002_001_005, "父菜单的类型必须是目录或者菜单");

    // ========== 角色模块 1-002-002-000 ==========
    ErrorCode ROLE_NOT_EXISTS = new ErrorCode(1_002_002_000, "角色不存在");
    ErrorCode ROLE_NAME_DUPLICATE = new ErrorCode(1_002_002_001, "已经存在名为【{}】的角色");
    ErrorCode ROLE_CODE_DUPLICATE = new ErrorCode(1_002_002_002, "已经存在编码为【{}】的角色");
    ErrorCode ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE = new ErrorCode(1_002_002_003, "不能操作类型为系统内置的角色");
    ErrorCode ROLE_IS_DISABLE = new ErrorCode(1_002_002_004, "名字为【{}】的角色已被禁用");
    ErrorCode ROLE_ADMIN_CODE_ERROR = new ErrorCode(1_002_002_005, "编码【{}】不能使用");

    // ========== 用户模块 1-002-003-000 ==========
    ErrorCode USER_USERNAME_EXISTS = new ErrorCode(1_002_003_000, "用户账号已经存在");
    ErrorCode USER_MOBILE_EXISTS = new ErrorCode(1_002_003_001, "手机号已经存在");
    ErrorCode USER_EMAIL_EXISTS = new ErrorCode(1_002_003_002, "邮箱已经存在");
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1_002_003_003, "用户不存在");
    ErrorCode USER_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_002_003_004, "导入用户数据不能为空！");
    ErrorCode IMPORT_LIST_IS_EMPTY = new ErrorCode(1_002_003_104, "导入数据不能为空！");
    ErrorCode USER_PASSWORD_FAILED = new ErrorCode(1_002_003_005, "用户密码校验失败");
    ErrorCode USER_IS_DISABLE = new ErrorCode(1_002_003_006, "名字为【{}】的用户已被禁用");
    ErrorCode USER_COUNT_MAX = new ErrorCode(1_002_003_008, "创建用户失败，原因：超过租户最大租户配额({})！");

    // ========== 部门模块 1-002-004-000 ==========
    ErrorCode DEPT_NAME_DUPLICATE = new ErrorCode(1_002_004_000, "已经存在该名字的部门");
    ErrorCode DEPT_PARENT_NOT_EXITS = new ErrorCode(1_002_004_001, "父级部门不存在");
    ErrorCode DEPT_NOT_FOUND = new ErrorCode(1_002_004_002, "当前部门不存在");
    ErrorCode DEPT_EXITS_CHILDREN = new ErrorCode(1_002_004_003, "存在子部门，无法删除");
    ErrorCode DEPT_PARENT_ERROR = new ErrorCode(1_002_004_004, "不能设置自己为父部门");
    ErrorCode DEPT_EXISTS_USER = new ErrorCode(1_002_004_005, "部门中存在员工，无法删除");
    ErrorCode DEPT_NOT_ENABLE = new ErrorCode(1_002_004_006, "部门({})不处于开启状态，不允许选择");
    ErrorCode DEPT_PARENT_IS_CHILD = new ErrorCode(1_002_004_007, "不能设置自己的子部门为父部门");

    // ========== 岗位模块 1-002-005-000 ==========
    ErrorCode POST_NOT_FOUND = new ErrorCode(1_002_005_000, "当前岗位不存在");
    ErrorCode POST_NOT_ENABLE = new ErrorCode(1_002_005_001, "岗位({}) 不处于开启状态，不允许选择");
    ErrorCode POST_NAME_DUPLICATE = new ErrorCode(1_002_005_002, "已经存在该名字的岗位");
    ErrorCode POST_CODE_DUPLICATE = new ErrorCode(1_002_005_003, "已经存在该标识的岗位");

    // ========== 字典类型 1-002-006-000 ==========
    ErrorCode DICT_TYPE_NOT_EXISTS = new ErrorCode(1_002_006_001, "当前字典类型不存在");
    ErrorCode DICT_TYPE_NOT_ENABLE = new ErrorCode(1_002_006_002, "字典类型不处于开启状态，不允许选择");
    ErrorCode DICT_TYPE_NAME_DUPLICATE = new ErrorCode(1_002_006_003, "已经存在该名字的字典类型");
    ErrorCode DICT_TYPE_TYPE_DUPLICATE = new ErrorCode(1_002_006_004, "已经存在该类型的字典类型");
    ErrorCode DICT_TYPE_HAS_CHILDREN = new ErrorCode(1_002_006_005, "无法删除，该字典类型还有字典数据");

    // ========== 字典数据 1-002-007-000 ==========
    ErrorCode DICT_DATA_NOT_EXISTS = new ErrorCode(1_002_007_001, "当前字典数据不存在");
    ErrorCode DICT_DATA_NOT_ENABLE = new ErrorCode(1_002_007_002, "字典数据({})不处于开启状态，不允许选择");
    ErrorCode DICT_DATA_VALUE_DUPLICATE = new ErrorCode(1_002_007_003, "已经存在该值的字典数据");

    // ========== 通知公告 1-002-008-000 ==========
    ErrorCode NOTICE_NOT_FOUND = new ErrorCode(1_002_008_001, "当前通知公告不存在");

    // ========== 短信渠道 1-002-011-000 ==========
    ErrorCode SMS_CHANNEL_NOT_EXISTS = new ErrorCode(1_002_011_000, "短信渠道不存在");
    ErrorCode SMS_CHANNEL_DISABLE = new ErrorCode(1_002_011_001, "短信渠道不处于开启状态，不允许选择");
    ErrorCode SMS_CHANNEL_HAS_CHILDREN = new ErrorCode(1_002_011_002, "无法删除，该短信渠道还有短信模板");

    // ========== 短信模板 1-002-012-000 ==========
    ErrorCode SMS_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_012_000, "短信模板不存在");
    ErrorCode SMS_TEMPLATE_CODE_DUPLICATE = new ErrorCode(1_002_012_001, "已经存在编码为【{}】的短信模板");
    ErrorCode SMS_TEMPLATE_API_ERROR = new ErrorCode(1_002_012_002, "短信 API 模板调用失败，原因是：{}");
    ErrorCode SMS_TEMPLATE_API_AUDIT_CHECKING = new ErrorCode(1_002_012_003, "短信 API 模版无法使用，原因：审批中");
    ErrorCode SMS_TEMPLATE_API_AUDIT_FAIL = new ErrorCode(1_002_012_004, "短信 API 模版无法使用，原因：审批不通过，{}");
    ErrorCode SMS_TEMPLATE_API_NOT_FOUND = new ErrorCode(1_002_012_005, "短信 API 模版无法使用，原因：模版不存在");

    // ========== 短信发送 1-002-013-000 ==========
    ErrorCode SMS_SEND_MOBILE_NOT_EXISTS = new ErrorCode(1_002_013_000, "手机号不存在");
    ErrorCode SMS_SEND_MOBILE_TEMPLATE_PARAM_MISS = new ErrorCode(1_002_013_001, "模板参数({})缺失");
    ErrorCode SMS_SEND_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_013_002, "短信模板不存在");

    // ========== 短信验证码 1-002-014-000 ==========
    ErrorCode SMS_CODE_NOT_FOUND = new ErrorCode(1_002_014_000, "验证码不存在");
    ErrorCode SMS_CODE_EXPIRED = new ErrorCode(1_002_014_001, "验证码已过期");
    ErrorCode SMS_CODE_USED = new ErrorCode(1_002_014_002, "验证码已使用");
    ErrorCode SMS_CODE_NOT_CORRECT = new ErrorCode(1_002_014_003, "验证码不正确");
    ErrorCode SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY = new ErrorCode(1_002_014_004, "超过每日短信发送数量");
    ErrorCode SMS_CODE_SEND_TOO_FAST = new ErrorCode(1_002_014_005, "短信发送过于频繁");
    ErrorCode SMS_CODE_IS_EXISTS = new ErrorCode(1_002_014_006, "手机号已被使用");
    ErrorCode SMS_CODE_IS_UNUSED = new ErrorCode(1_002_014_007, "验证码未被使用");

    // ========== 租户信息 1-002-015-000 ==========
    ErrorCode TENANT_NOT_EXISTS = new ErrorCode(1_002_015_000, "租户不存在");
    ErrorCode TENANT_DISABLE = new ErrorCode(1_002_015_001, "名字为【{}】的租户已被禁用");
    ErrorCode TENANT_EXPIRE = new ErrorCode(1_002_015_002, "名字为【{}】的租户已过期");
    ErrorCode TENANT_CAN_NOT_UPDATE_SYSTEM = new ErrorCode(1_002_015_003, "系统租户不能进行修改、删除等操作！");
    ErrorCode TENANT_NAME_DUPLICATE = new ErrorCode(1_002_015_004, "名字为【{}】的租户已存在");
    ErrorCode TENANT_WEBSITE_DUPLICATE = new ErrorCode(1_002_015_005, "域名为【{}】的租户已存在");
    ErrorCode TENANT_DUPLICATE = new ErrorCode(1_002_015_006, "当前用户的租户为【{}】，无需切换");


    // ========== 租户套餐 1-002-016-000 ==========
    ErrorCode TENANT_PACKAGE_NOT_EXISTS = new ErrorCode(1_002_016_000, "租户套餐不存在");
    ErrorCode TENANT_PACKAGE_USED = new ErrorCode(1_002_016_001, "租户正在使用该套餐，请给租户重新设置套餐后再尝试删除");
    ErrorCode TENANT_PACKAGE_DISABLE = new ErrorCode(1_002_016_002, "名字为【{}】的租户套餐已被禁用");

    // ========== 社交用户 1-002-018-000 ==========
    ErrorCode SOCIAL_USER_AUTH_FAILURE = new ErrorCode(1_002_018_000, "社交授权失败，原因是：{}");
    ErrorCode SOCIAL_USER_NOT_FOUND = new ErrorCode(1_002_018_001, "社交授权失败，找不到对应的用户");

    ErrorCode SOCIAL_CLIENT_WEIXIN_MINI_APP_PHONE_CODE_ERROR = new ErrorCode(1_002_018_200, "获得手机号失败");
    ErrorCode SOCIAL_CLIENT_NOT_EXISTS = new ErrorCode(1_002_018_201, "社交客户端不存在");
    ErrorCode SOCIAL_CLIENT_UNIQUE = new ErrorCode(1_002_018_202, "社交客户端已存在配置");

    // ========== OAuth2 客户端 1-002-020-000 =========
    ErrorCode OAUTH2_CLIENT_NOT_EXISTS = new ErrorCode(1_002_020_000, "OAuth2 客户端不存在");
    ErrorCode OAUTH2_CLIENT_EXISTS = new ErrorCode(1_002_020_001, "OAuth2 客户端编号已存在");
    ErrorCode OAUTH2_CLIENT_DISABLE = new ErrorCode(1_002_020_002, "OAuth2 客户端已禁用");
    ErrorCode OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS = new ErrorCode(1_002_020_003, "不支持该授权类型");
    ErrorCode OAUTH2_CLIENT_SCOPE_OVER = new ErrorCode(1_002_020_004, "授权范围过大");
    ErrorCode OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH = new ErrorCode(1_002_020_005, "无效 redirect_uri: {}");
    ErrorCode OAUTH2_CLIENT_CLIENT_SECRET_ERROR = new ErrorCode(1_002_020_006, "无效 client_secret: {}");

    // ========== OAuth2 授权 1-002-021-000 =========
    ErrorCode OAUTH2_GRANT_CLIENT_ID_MISMATCH = new ErrorCode(1_002_021_000, "client_id 不匹配");
    ErrorCode OAUTH2_GRANT_REDIRECT_URI_MISMATCH = new ErrorCode(1_002_021_001, "redirect_uri 不匹配");
    ErrorCode OAUTH2_GRANT_STATE_MISMATCH = new ErrorCode(1_002_021_002, "state 不匹配");
    ErrorCode OAUTH2_GRANT_CODE_NOT_EXISTS = new ErrorCode(1_002_021_003, "code 不存在");

    // ========== OAuth2 授权 1-002-022-000 =========
    ErrorCode OAUTH2_CODE_NOT_EXISTS = new ErrorCode(1_002_022_000, "code 不存在");
    ErrorCode OAUTH2_CODE_EXPIRE = new ErrorCode(1_002_022_001, "code 已过期");

    // ========== 邮箱账号 1-002-023-000 ==========
    ErrorCode MAIL_ACCOUNT_NOT_EXISTS = new ErrorCode(1_002_023_000, "邮箱账号不存在");
    ErrorCode MAIL_ACCOUNT_RELATE_TEMPLATE_EXISTS = new ErrorCode(1_002_023_001, "无法删除，该邮箱账号还有邮件模板");

    // ========== 邮件模版 1-002-024-000 ==========
    ErrorCode MAIL_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_024_000, "邮件模版不存在");
    ErrorCode MAIL_TEMPLATE_CODE_EXISTS = new ErrorCode(1_002_024_001, "邮件模版 code({}) 已存在");

    // ========== 邮件发送 1-002-025-000 ==========
    ErrorCode MAIL_SEND_TEMPLATE_PARAM_MISS = new ErrorCode(1_002_025_000, "模板参数({})缺失");
    ErrorCode MAIL_SEND_MAIL_NOT_EXISTS = new ErrorCode(1_002_025_001, "邮箱不存在");

    // ========== 站内信模版 1-002-026-000 ==========
    ErrorCode NOTIFY_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_026_000, "站内信模版不存在");
    ErrorCode NOTIFY_TEMPLATE_CODE_DUPLICATE = new ErrorCode(1_002_026_001, "已经存在编码为【{}】的站内信模板");

    // ========== 站内信模版 1-002-027-000 ==========

    // ========== 站内信发送 1-002-028-000 ==========
    ErrorCode NOTIFY_SEND_TEMPLATE_PARAM_MISS = new ErrorCode(1_002_028_000, "模板参数({})缺失");

    // ========== 厂区地图定位配置 1-002-029-000 ==========
    ErrorCode MAP_COORDINATE_INFO_NOT_EXISTS = new ErrorCode(1_002_029_000, "厂区地图定位详细信息不存在");
    ErrorCode MAP_FEEDBACK_NOT_EXISTS = new ErrorCode(1_002_029_001, "厂区地图反馈不存在");
    // ========== 单据编码类型配置 1-002-030-000 ==========
    ErrorCode SCENE_CODE_NOT_EXISTS = new ErrorCode(1_002_030_000, "单据编码类型配置不存在");
    ErrorCode SCENE_CODE_IS_IN_USE = new ErrorCode(1_002_030_001, "该单据编码已经被使用，无法修改或删除");
    ErrorCode SCENE_CODE_NOT_AVAILABLE = new ErrorCode(1_002_030_002, "单据编码目前不存在或者不可用，请核实");
    ErrorCode SCENE_CODE_ALREADY_EXIST = new ErrorCode(1_002_030_003, "该编码已存在，请核实");

    // ========== 单据编码类型配置 1-002-031-000 ==========
    ErrorCode MEETING_ROOM_INFO_NOT_EXISTS = new ErrorCode(1_002_031_000, "会议室基本信息不存在");
    ErrorCode USER_BOOK_MEETING_NOT_EXISTS = new ErrorCode(1_002_031_001, "会议预定信息不存在");
    ErrorCode USER_BOOK_MEETING_ALREADY_BOOKED = new ErrorCode(1_002_031_002, "该会议室该时间段有其他会议已预定，麻烦检查！");
    ErrorCode MEETING_SEND_TEMPLATE_FAILED = new ErrorCode(1_002_031_003, "发送会议模板消息异常！");

    // ========== 用户汇报配置 1-002-032-000 ==========
    ErrorCode USER_REPORT_NOT_EXISTS = new ErrorCode(1_002_032_000, "汇报信息不存在");
    ErrorCode USER_REPORT_ALREADY_COMMIT = new ErrorCode(1_002_032_001, "用户的汇报信息重复了！");
    ErrorCode REPORT_JOB_SCHEDULE_NOT_EXISTS = new ErrorCode(1_002_032_002, "汇报工作进度不存在");
    ErrorCode REPORT_JOB_PLAN_NOT_EXISTS = new ErrorCode(1_002_032_003, "汇报工作计划不存在");
    ErrorCode USER_REPORT_EXISTS = new ErrorCode(1_002_032_004, "当天已存在汇报内容");
    ErrorCode USER_REPORT_NOT_OPERATE = new ErrorCode(1_002_032_005, "当前用户无权限操作");
    ErrorCode USER_REPORT_INFO_NOT_EXISTS = new ErrorCode(1_002_032_006, "信息不存在");
    ErrorCode REPORT_ATTENTION_NOT_EXISTS = new ErrorCode(1_002_032_007, "关注信息不存在");
    ErrorCode REPORT_OBJECT_NOT_EXISTS = new ErrorCode(1_002_032_008, "汇报对象不存在");
    ErrorCode REPORT_ATTENTION_ALREADY_REPLOY = new ErrorCode(1_002_032_009, "已跟进不能取消关注！");

    // ========== 运维管理配置 1-002-033-000 ==========
    ErrorCode OPERATION_QUESTION_TYPE_NOT_EXISTS = new ErrorCode(1_002_033_000, "运维类型不存在");
    ErrorCode OPERATION_DEVICE_TYPE_NOT_EXISTS = new ErrorCode(1_002_033_001, "设备类型不存在");
    ErrorCode OPERATION_ADDRESS_NOT_EXISTS = new ErrorCode(1_002_033_002, "运维地址不存在");
    ErrorCode OPERATION_LABEL_NOT_EXISTS = new ErrorCode(1_002_033_003, "标签不存在");
    ErrorCode OPERATION_LABEL_NUM_CORRECT = new ErrorCode(1_002_033_004, "请正确输入数值");
    ErrorCode OPERATION_DEVICE_NOT_EXISTS = new ErrorCode(1_002_033_005, "设备信息不存在");
    ErrorCode OPERATION_ORDER_NOT_EXISTS = new ErrorCode(1_002_033_006, "工单不存在");
    ErrorCode OPERATION_ORDER_OPERATE_NOT_EXISTS = new ErrorCode(1_002_033_007, "操作类型不存在");
    ErrorCode OPERATION_ORDER_OPERATE_ERROR = new ErrorCode(1_002_033_008, "当前工单状态不可执行该操作");
    ErrorCode OPERATION_ORDER_OPERATE_NOT_PICTURE = new ErrorCode(1_002_033_009, "当前该操作图片必传");
    ErrorCode OPERATION_NOTICE_OBJECT_NOT_EXISTS = new ErrorCode(1_002_033_010, "消息通知对象设置不存在");
    ErrorCode OPERATION_DEVICE_HISTORY_NOT_EXISTS = new ErrorCode(1_002_033_011, "运维设备不存在");
    ErrorCode OPERATION_DEVICE_ACCESSORY_HISTORY_NOT_EXISTS = new ErrorCode(1_002_033_012, "运维设备配件表_快照不存在");
    ErrorCode OPERATION_DEVICE_PICTURE_HISTORY_NOT_EXISTS = new ErrorCode(1_002_033_013, "运维设备照片表_快照不存在");
    ErrorCode OPERATION_ORDER_NOT_BELONG = new ErrorCode(1_002_033_014, "无当前工单操作权限");
    ErrorCode OPERATION_SUB_ADDRESS_EXISTS = new ErrorCode(1_002_033_015, "当前地点下存在启用的子级地点，无法删除");
    ErrorCode OPERATION_DEVICE_MODEL_NOT_EXISTS = new ErrorCode(1_002_033_016, "设备型号不存在");
    ErrorCode OPERATION_DEVICE_MODEL_EXISTS = new ErrorCode(1_002_033_017, "设备型号已存在");
    ErrorCode INSPECTION_PROJECT_NOT_EXISTS = new ErrorCode(1_002_033_018, "巡检项目指标不存在");
    ErrorCode INSPECTION_RECORD_NOT_EXISTS = new ErrorCode(1_002_033_019, "设备巡检记录不存在");
    ErrorCode INSPECTION_RECORD_DETAIL_NOT_EXISTS = new ErrorCode(1_002_033_020, "巡检记录详情不存在");

    //抽奖
    ErrorCode PRIZE_DRAW_ACTIVITY_NOT_EXISTS = new ErrorCode(1_002_034_001, "抽奖活动已失效或不存在");
    ErrorCode PRIZE_DRAW_USER_NOT_EXISTS = new ErrorCode(1_002_034_002, "抽奖用户不存在");
    ErrorCode PRIZE_DRAW_USER_EXISTS = new ErrorCode(1_002_034_005, "您已参与本次活动");
    ErrorCode PRIZE_DRAW_USER_NOT_ENOUGH = new ErrorCode(1_002_034_006, "参与人数不足");
    ErrorCode PRIZE_DRAW_TOKEN = new ErrorCode(1_002_034_007, "token获取失败");
    ErrorCode PRIZE_DRAW_NOT_INFO = new ErrorCode(1_002_034_008, "信息获取失败");
    ErrorCode PRIZE_NOT_EXISTS = new ErrorCode(1_002_034_003, "奖品信息不存在");
    ErrorCode PRIZE_DRAW_OUT_USER_NOT_EXISTS = new ErrorCode(1_002_034_004, "场外参与人员不存在");

}
