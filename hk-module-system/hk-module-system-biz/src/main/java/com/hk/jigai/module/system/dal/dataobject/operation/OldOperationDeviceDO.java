package com.hk.jigai.module.system.dal.dataobject.operation;

import com.baomidou.mybatisplus.annotation.*;
import com.hk.jigai.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 运维设备 DO
 *
 * @author 超级管理员
 */
@TableName(value = "CI", autoResultMap = true)
@KeySequence("hk_operation_device_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OldOperationDeviceDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long ciid;

    private String resourcename;

    private Long productid;

    private Long corporationid;

    private String displayserilno1;

    private String displayassettag1;

    private String displayserilno2;

    private String displayassettag2;

    private String assettag;

    private String serialno;

    private String barcode;

    private String location;

    private Long siteid;

    private String description;

    private Long impactid;

    private Long deptid;

    private Long stateid;

    private String regisetperson;

    private String userno;

    private Date registerdate;

    private String displayphoto;

    private String productphoto;

    private String gobalphoto;

    private Integer enable;

    private String ipaddresses1;

    private String ipaddresses2;

    private String macaddress1;

    private String macaddress2;

    private Date productdate;

    private String locationex;

}