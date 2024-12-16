package com.hk.jigai.module.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别的枚举值
 *
 * @author 恒科技改
 */
@Getter
@AllArgsConstructor
public enum OrderOperateEnum {
    CHUANGJIAN("create"),   //创建
    PAIDAN("distributeLeaflets"),   //派单
    LINGDAN("collectDocuments"),    //领单
    ZHUANJIAO("transfer"),     //转交
    XIANCHNAGQUEREN("confirm"), //现场确认
    GUAQI("hangUp"),//挂起
    WANCHENG("complete"),//已完成
    CHEXIAO("revoke"),//撤销
    KAISHI("restart"),//重新开始
    WEIWAI("outsourcing"),//委外维修
    WEIWAICHONGQI("outsourcingRestart");//委外维修结束后重启
    private final String value;

}
