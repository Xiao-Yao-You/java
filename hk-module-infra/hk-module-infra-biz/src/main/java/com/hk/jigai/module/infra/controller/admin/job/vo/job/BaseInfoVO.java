package com.hk.jigai.module.infra.controller.admin.job.vo.job;

import com.hk.jigai.module.system.controller.admin.user.vo.user.UserDeptRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Schema(description = "管理后台 - 获取用户基本信息定时任务创建/修改 Request VO")
@Data
public class BaseInfoVO {
    private Long id;
    private int staffType;
    private String sysName;
    private String personNO;
    private String personName;
    private String personType;
    private String personJob;
    private String gender;
    private Integer sex;
    private String birthday;
    private String dep_fullName;
    private String nation;
    private String phone;
    private Set<Long> userPostSet;
}
