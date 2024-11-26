package com.hk.jigai.module.system.controller.admin.operation.vo;

import cn.hutool.core.date.DateTime;
import com.hk.jigai.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.hk.jigai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 运维设备分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OldOperationDevicePageReqVO extends PageParam {

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