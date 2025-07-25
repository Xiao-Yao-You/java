package com.hk.jigai.module.system.enums;

/**
 * System 字典类型的枚举类
 *
 * @author 恒科技改
 */
public interface DictTypeConstants {

    // ======================= 通用 =======================
    String USER_TYPE = "user_type"; // 用户类型
    String COMMON_STATUS = "common_status"; // 系统状态
    String ASSETS_COMPANY = "assets_company"; // 资产所属公司

    // ======================= 办公工具 =======================
    String SUGGESTION_STATUS = "adoption_status"; // 合理化建议状态
    String SUGGESTION_TYPE = "suggestion_type"; // 合理化建议类型
    String ANONYMOUS = "anonymous"; // 合理化建议类型

    // ======================= 工单 =======================
    String REPAIR_ORDER_STATUS = "repair_order_status"; // 工单处理状态
    String REPAIR_ORDER_TYPE = "repair_request_type"; // 请求类型
    String HK_LEVEL = "hk_level"; // 紧急程度
    String SOURCE_TYPE = "source_type"; // 工单来源
    String ORDER_TYPE = "order_type"; // 工单类型

    // ======================= 运维设备 =======================
    String REPAIR_DEVICE_STATUS = "repair_device_status"; // 设备（使用）状态
    String REPAIR_DEVICE_ANTIVIRUS = "repair_device_antivirus"; // 设备是否安装杀毒软件

    // ======================= SYSTEM 模块 =======================
    String USER_SEX = "system_user_sex"; // 用户性别
    String LOGIN_TYPE = "system_login_type"; // 登录日志的类型
    String LOGIN_RESULT = "system_login_result"; // 登录结果
    String ERROR_CODE_TYPE = "system_error_code_type"; // 错误码的类型枚举
    String SMS_CHANNEL_CODE = "system_sms_channel_code"; // 短信渠道编码
    String SMS_TEMPLATE_TYPE = "system_sms_template_type"; // 短信模板类型
    String SMS_SEND_STATUS = "system_sms_send_status"; // 短信发送状态
    String SMS_RECEIVE_STATUS = "system_sms_receive_status"; // 短信接收状态


}
