package com.hk.jigai.module.system.job;

import com.hk.jigai.framework.tenant.core.job.TenantJob;
import com.hk.jigai.module.system.dal.mysql.permission.RoleMenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//@Slf4j
//@Component
public class OrderReportJob {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Scheduled(cron = "*/5 * * * * ?")
    @TenantJob
    public void reportScheduled() {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

    }

}
