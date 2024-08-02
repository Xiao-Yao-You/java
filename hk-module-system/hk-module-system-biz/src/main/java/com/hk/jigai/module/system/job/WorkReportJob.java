package com.hk.jigai.module.system.job;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.tenant.core.aop.TenantIgnore;
import com.hk.jigai.framework.tenant.core.job.TenantJob;
import com.hk.jigai.module.system.dal.dataobject.dept.DeptDO;
import com.hk.jigai.module.system.dal.dataobject.dept.UserDeptDO;
import com.hk.jigai.module.system.dal.dataobject.permission.RoleMenuDO;
import com.hk.jigai.module.system.dal.dataobject.permission.UserRoleDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.dal.dataobject.userreport.UserReportDO;
import com.hk.jigai.module.system.dal.mysql.dept.DeptMapper;
import com.hk.jigai.module.system.dal.mysql.dept.UserDeptMapper;
import com.hk.jigai.module.system.dal.mysql.permission.RoleMenuMapper;
import com.hk.jigai.module.system.dal.mysql.permission.UserRoleMapper;
import com.hk.jigai.module.system.dal.mysql.user.AdminUserMapper;
import com.hk.jigai.module.system.dal.mysql.userreport.UserReportMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class WorkReportJob {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserReportMapper userReportMapper;

    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private UserDeptMapper userDeptMapper;

    //新增工作汇报的菜单id
    private final Long createReportMenuId = 2780L;

    @Scheduled(cron = "0 0 1 * * ?")
    @TenantJob
    public void reportScheduled() {
        //所有需要提交日报的人员userId-deptId集合
        List<String> allUserDeptStr = new ArrayList<>();

        //所有已经提交日报的人员userId-deptId集合
        List<String> userDeptStr = new ArrayList<>();
        //通过“新增工作汇报功能权限”，获取需要进行工作汇报的人员名单
        List<RoleMenuDO> distinctRoles = roleMenuMapper.selectList(new QueryWrapper<RoleMenuDO>().select("DISTINCT ROLE_ID")
                .lambda().eq(RoleMenuDO::getMenuId, createReportMenuId));
        //通过角色ID集合，查询角色下的人员
        if (CollectionUtil.isNotEmpty(distinctRoles)) {
            List<Long> distinctRoleIds = distinctRoles.stream().map(p -> p.getRoleId()).collect(Collectors.toList());
            List<UserRoleDO> userRoleDOS = userRoleMapper.selectList(new QueryWrapper<UserRoleDO>().select("DISTINCT USER_ID").lambda()
                    .in(UserRoleDO::getRoleId, distinctRoleIds));
            if (CollectionUtil.isNotEmpty(userRoleDOS)) {
                //所有有新增工作汇报权限的用户id
                List<Long> userIds = userRoleDOS.stream().map(p -> p.getUserId()).collect(Collectors.toList());
                List<UserDeptDO> userDeptDOS = userDeptMapper.selectList(new QueryWrapper<UserDeptDO>().lambda().in(UserDeptDO::getUserId, userIds));
                if (CollectionUtil.isNotEmpty(userRoleDOS)) {
                    allUserDeptStr = userDeptDOS.stream().map(p -> p.getUserId() + "-" + p.getDeptId()).collect(Collectors.toList());
                }
            }
        }
        //查询前一天所有提交工作汇报的人员
        List<UserReportDO> userReportDOS = userReportMapper.selectList(new QueryWrapper<UserReportDO>().lambda()
                .eq(UserReportDO::getDateReport, LocalDate.now().minusDays(1)));
        if (CollectionUtil.isNotEmpty(userReportDOS)) {
            userDeptStr = userReportDOS.stream().map(p -> p.getUserId() + "-" + p.getDeptId()).collect(Collectors.toList());
        }
        //去除存在项
        allUserDeptStr = new ArrayList<>();
        List<String> finalUserDeptStr = userDeptStr;
        List<String> diff = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(allUserDeptStr)) {
            diff = allUserDeptStr.stream().filter(i -> !finalUserDeptStr.contains(i)).collect(Collectors.toList());
        }
        if (CollectionUtil.isNotEmpty(diff)) {
            List<UserReportDO> userReportInsertList = new ArrayList<>();
            for (String diffId : diff) {
                long userId = Long.parseLong(diffId.split("-")[0]);
                long deptId = Long.parseLong(diffId.split("-")[1]);
                UserReportDO userReportDO = new UserReportDO();
                userReportDO.setUserId(userId);
                AdminUserDO adminUserDO = adminUserMapper.selectById(userId);
                userReportDO.setUserNickName(adminUserDO == null ? "未命名" : adminUserDO.getNickname());
                userReportDO.setDeptId(deptId);
                DeptDO deptDO = deptMapper.selectById(deptId);
                userReportDO.setDeptName(deptDO == null ? "未命名" : deptDO.getName());
                userReportDO.setDateReport(LocalDate.now().minusDays(1));
                userReportDO.setCheckStatus("0");
                userReportDO.setType("2");
                userReportInsertList.add(userReportDO);
            }
            userReportMapper.insertBatch(userReportInsertList);
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("日报处理成功");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

    }

}
