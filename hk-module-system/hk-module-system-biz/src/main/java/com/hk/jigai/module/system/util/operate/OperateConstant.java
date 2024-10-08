package com.hk.jigai.module.system.util.operate;

public class OperateConstant {
    //操作类型，00:派单,01:领单,0201:同组转交,0202:跨组转交,03:现场确认,04:挂起,05 已完成,0501:无需处理,0502:无法排除故障,06:撤销
    public static final String PAIDAN_TYPE = "00";//派单
    public static final String LINGDAN_TYPE = "01";//领单
    public static final String TONGZUZHUANJIAO_TYPE = "0201";//同组转交
    public static final String KUAZUZHUANJIAO_TYPE = "0202";//跨组转交
    public static final String XIANCHNAGQUEREN_TYPE = "03";//现场确认
    public static final String GUAQI_TYPE = "04";//挂起
    public static final String WANCHENG_TYPE = "05";//已完成
    public static final String WANCHENG_WUXUCHULI_TYPE = "0501";//无需处理
    public static final String WANCHENG_WUFACHULI_TYPE = "0502";//无法排除故障
    public static final String CHEXIAO_TYPE = "06";//撤销

    //状态,00 待分配,01 待处理,02 进行中,03 挂起中,04 已处理,05 已完成,0501:无需处理,0502:无法排除故障,06 已撤销

    public static final String WAIT_ALLOCATION_STATUS = "00";//00 待分配
    public static final String WAIT_DEAL_STATUS = "01";//01 待处理
    public static final String IN_GOING_STATUS = "02";//02 进行中
    public static final String HANG_UP_STATUS = "03";//03 挂起中
    public static final String ALREADY_DEAL_STATUS = "04";//04 已处理
    public static final String COMPLETE_STATUS = "05";//已完成
    public static final String COMPLETE_NO_NEED_DEAL_STATUS = "0501";//无需处理
    public static final String COMPLETE_CAN_NOT_DEAL_STATUS = "0502";//无法排除故障
    public static final String ROLLBACK_STATUS = "06";//撤销
}
