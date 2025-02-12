package com.hk.jigai.module.system.util.operate;

public class OperateConstant {
    //操作类型，00:派单,01:领单,0201:同组转交,0202:跨组转交,03:现场确认,04:挂起,05 已完成,0501:无需处理,0502:无法排除故障,06:撤销

        public static final Integer CREATE_TYPE = 99;//创建
        public static final Integer PAIDAN_TYPE = 0;//派单
        public static final Integer LINGDAN_TYPE = 1;//领单
        public static final String TONGZUZHUANJIAO_TYPE = "0201";//同组转交
        public static final String KUAZUZHUANJIAO_TYPE = "0202";//跨组转交
        public static final Integer ZHUANJIAO_TYPE = 2;//转交
        public static final Integer XIANCHNAGQUEREN_TYPE = 3;//现场确认
        public static final Integer GUAQI_TYPE = 4;//挂起
        public static final Integer WANCHENG_TYPE = 5;//已完成
        public static final Integer WANCHENG_WUXUCHULI_TYPE = 501;//无需处理
        public static final Integer WANCHENG_WUFACHULI_TYPE = 502;//无法排除故障
        public static final Integer CHEXIAO_TYPE = 6;//撤销
        public static final Integer KAISHI_TYPE = 7;//开始
        public static final Integer OUTSOURCING_TYPE = 8;//委外维修
        public static final Integer OUTSOURCING_RESTART_TYPE = 9;//委外维修,重启操作


    //状态,00 待分配,01 待处理,02 进行中,03 挂起中,04 已处理,05 已完成,0501:无需处理,0502:无法排除故障,06 已撤销

    public static final String CREATE_STATUS = "99";//99 已创建
    public static final String WAIT_ALLOCATION_STATUS = "00";//00 待分配
    public static final String WAIT_DEAL_STATUS = "01";//01 待处理
    public static final String IN_GOING_STATUS = "02";//02 进行中
    public static final String HANG_UP_STATUS = "03";//03 挂起中
    public static final String OUTSOURCING_STATUS = "04";// 委外维修中
    public static final String ALREADY_DEAL_STATUS = "05";//04 已处理
    public static final String COMPLETE_STATUS = "06";//已完成
    public static final String COMPLETE_NO_NEED_DEAL_STATUS = "0601";//无需处理
    public static final String COMPLETE_CAN_NOT_DEAL_STATUS = "0602";//无法排除故障
    public static final String ROLLBACK_STATUS = "07";//撤销


    //操作类型方法名
    public static final String CHUANGJIAN = "create";//创建
    public static final String PAIDAN = "distributeLeaflets";//派单
    public static final String LINGDAN = "collectDocuments";//领单
    public static final String ZHUANJIAO = "transfer";//转交
    public static final String XIANCHNAGQUEREN = "confirm";//现场确认
    public static final String GUAQI = "hangUp";//挂起
    public static final String WANCHENG = "complete";//已完成
    public static final String CHEXIAO = "revoke";//撤销
    public static final String KAISHI = "restart";//再次开始
    public static final String OUTSOURCING = "outsourcing";//委外维修中
    public static final String WEIWAICHONGQI = "outsourcingRestart";//委外维修后重启

}
