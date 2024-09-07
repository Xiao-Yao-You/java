package com.hk.jigai.module.infra.job.job;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.quartz.core.handler.JobHandler;
import com.hk.jigai.framework.tenant.core.context.TenantContextHolder;
import com.hk.jigai.module.infra.controller.admin.job.vo.job.BaseInfoVO;
import com.hk.jigai.module.infra.enums.job.JobConstant;
import com.hk.jigai.module.infra.job.job.dto.DeptDTO;
import com.hk.jigai.module.infra.service.job.BaseInfoService;
import com.hk.jigai.module.system.dal.dataobject.dept.DeptDO;
import com.hk.jigai.module.system.dal.dataobject.dept.PostDO;
import com.hk.jigai.module.system.dal.dataobject.dept.UserDeptDO;
import com.hk.jigai.module.system.dal.dataobject.dept.UserPostDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.dal.dataobject.userreport.ReportJobScheduleDO;
import com.hk.jigai.module.system.dal.mysql.dept.DeptMapper;
import com.hk.jigai.module.system.dal.mysql.dept.PostMapper;
import com.hk.jigai.module.system.dal.mysql.dept.UserDeptMapper;
import com.hk.jigai.module.system.dal.mysql.dept.UserPostMapper;
import com.hk.jigai.module.system.dal.mysql.user.AdminUserMapper;
import com.hk.jigai.module.system.enums.common.SexEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Component
public class BaseInfoUpdateJob implements JobHandler {

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private PostMapper postMapper;

    @Resource
    private AdminUserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserPostMapper userPostMapper;

    @Resource
    private UserDeptMapper userDeptMapper;

    @Resource
    private BaseInfoService baseInfoService;

    @Override
    public String execute(String param) throws Exception {
        int deptid;
        int postId;
        int userId;
        int userDeptId;
        int userPostId;
        Map<String, DeptDO> deptDBAllMap = new HashMap<>();
        Map<String, PostDO> postDBAllMap = new HashMap<>();
        TenantContextHolder.setTenantId(new Long(JobConstant.TENANT_ID));
        deptid = deptMapper.queryMaxId().intValue() + 1;
        postId = postMapper.queryMaxId().intValue() + 1;
        userId = userMapper.queryMaxId().intValue() + 1;
        userDeptId = userDeptMapper.queryMaxId().intValue() + 1;
        userPostId = userPostMapper.queryMaxId().intValue() + 1;
        //1.恒科信改，信息获取接口调用
        List<BaseInfoVO> baseInfoList = baseInfoService.callPerson("http://172.21.27.12:9877/person", HttpMethod.POST);
        if(CollectionUtils.isAnyEmpty(baseInfoList)){
            return "";
        }

        //test 一条数据
//        List<BaseInfoVO> b = new ArrayList<>();
//        BaseInfoVO bb = new BaseInfoVO();
//        bb.setGender("男");
//        bb.setPhone("19522714581");
//        bb.setPersonName("吕远恒");
//        bb.setPersonNO("10001184");
//        bb.setBirthday("1999-07-02");
//        bb.setPersonType("EHR员工");
//        bb.setDep_fullName("长丝部/长丝MN区/长丝M区生产/长丝M2生产/运转班/甲班（南）");
//        bb.setPersonJob("长丝生产运转组长");
//        b.add(bb);
        //2.部门跟职位基表处理
        //先查询现有的数据，dept post，如果存在则更新，如果不存在则插入
        List<String> personNO = new ArrayList<>();

        List<DeptDO> deptListDBAll = deptMapper.selectList();
        List<PostDO> postListDBAll = postMapper.selectList();

        for(DeptDO deptDO : deptListDBAll){
            deptDBAllMap.put(deptDO.getFullName(),deptDO);
        }
        for(PostDO postDO : postListDBAll){
            postDBAllMap.put(postDO.getName(), postDO);
        }
        for(BaseInfoVO baseInfo : baseInfoList){
            //当前用户的部门与职业，目前基表是否有，如果有则更新，没有则插入
            String deptFullName = "/" + baseInfo.getDep_fullName();
            DeptDO currentDept = deptDBAllMap.get(deptFullName);
            if(currentDept == null){
                String[] deptArray = deptFullName.split(JobConstant.SPLIT_CHAR);
                if(deptArray != null && deptArray.length > 0){
                    Long parentId = new Long(JobConstant.ROOT_DEPT_ID);
                    StringBuffer deptFullNameBuffer = new StringBuffer("");
                    for(String deptName : deptArray) {
                        if(StringUtils.isNotEmpty(deptName)){
                            deptFullNameBuffer.append("/" + deptName);
                            DeptDO queryDept = deptDBAllMap.get(deptFullNameBuffer.toString());
                            if(queryDept == null){
                                Long newId = new Long(deptid++);
                                DeptDO deptDO = new DeptDO();
                                deptDO.setId(newId);
                                deptDO.setName(deptName);
                                deptDO.setFullName(deptFullNameBuffer.toString());
                                deptDO.setParentId(parentId);
                                deptDO.setSort(new Integer(String.valueOf(newId)));
                                deptDO.setStatus(new Integer(0));
                                deptMapper.insert(deptDO);
                                deptDBAllMap.put(deptFullNameBuffer.toString(),deptDO);
                                parentId = newId;
                                continue;
                            }else{
                                parentId = queryDept.getId();
                                continue;
                            }
                        }
                    }
                }
                currentDept = deptDBAllMap.get(deptFullName);
            }
            String postName = baseInfo.getPersonJob();
            PostDO currentPost = postDBAllMap.get(postName);
            if(currentPost == null){
                PostDO postDTO = new PostDO();
                postDTO.setId(new Long(postId));
                postDTO.setName(postName);
                postDTO.setCode(baseInfo.getPersonType());
                postDTO.setSort(new Integer(String.valueOf(postId)));
                postDTO.setStatus(new Integer(0));
                postMapper.insert(postDTO);
                postDBAllMap.put(postName,postDTO);
                currentPost = postDTO;
                postId++;
            }

            //用户以及用户的职业与部门存在则更新
            AdminUserDO adminUserDO = userMapper.selectOne(new QueryWrapper<AdminUserDO>().lambda().eq(AdminUserDO::getUsername,baseInfo.getPersonNO()));
            if(adminUserDO == null){
                adminUserDO = new AdminUserDO();
                adminUserDO.setId(new Long(userId));
                if("男".equals(baseInfo.getGender())){
                    adminUserDO.setSex(SexEnum.MALE.getSex());
                }else if("女".equals(baseInfo.getGender())){
                    adminUserDO.setSex(SexEnum.FEMALE.getSex());
                }else{
                    adminUserDO.setSex(SexEnum.UNKNOWN.getSex());
                }
                Set<Long> userPostSet = new HashSet<>();
                userPostSet.add(currentPost.getId());
                adminUserDO.setPostIds(userPostSet);
                adminUserDO.setPassword(passwordEncoder.encode(JobConstant.FIRST_PASSWORD));
                adminUserDO.setStatus(0);
                adminUserDO.setUsername(baseInfo.getPersonNO());
                adminUserDO.setNickname(baseInfo.getPersonName());
                adminUserDO.setMobile(baseInfo.getPhone());
                userMapper.insert(adminUserDO);
                userId++;
            }else{
                Set<Long> userPostSet = new HashSet<>();
                userPostSet.add(currentPost.getId());
                adminUserDO.setPostIds(userPostSet);
                adminUserDO.setStatus(0);
                adminUserDO.setUsername(baseInfo.getPersonNO());
                adminUserDO.setNickname(baseInfo.getPersonName());
                adminUserDO.setMobile(baseInfo.getPhone());
                userMapper.updateById(adminUserDO);
            }

            UserDeptDO userDept = userDeptMapper.selectOne(new QueryWrapper<UserDeptDO>()
                    .lambda().eq(UserDeptDO::getUserId, adminUserDO.getId()).eq(UserDeptDO::getDeptId, currentDept.getId()));
            if(userDept == null){
                userDeptMapper.deleteByUserId(adminUserDO.getId());
                userDept = new UserDeptDO();
                userDept.setId(new Long(userDeptId));
                userDept.setUserId(adminUserDO.getId());
                userDept.setDeptId(currentDept.getId());
                userDeptMapper.insert(userDept);
                userDeptId++;
            }
            UserPostDO userPost = userPostMapper.selectOne(new QueryWrapper<UserPostDO>()
                    .lambda().eq(UserPostDO::getUserId, adminUserDO.getId()).eq(UserPostDO::getPostId, currentPost.getId()));
            if(userPost == null){
                userPostMapper.deleteByUserId( adminUserDO.getId());
                userPost = new UserPostDO();
                userPost.setId(new Long(userPostId));
                userPost.setUserId(adminUserDO.getId());
                userPost.setPostId(currentPost.getId());
                userPostMapper.insert(userPost);
                userPostId++;
            }
            personNO.add(baseInfo.getPersonNO());
        }
        //处理删除的用户
        List<AdminUserDO> invalidUser = userMapper.selectList(new QueryWrapper<AdminUserDO>().lambda()
                .notIn(AdminUserDO::getUsername,personNO)
                .between(AdminUserDO::getId,500,999999999));
        if(!CollectionUtils.isAnyEmpty(invalidUser)){
            List<Long> userIdList = new ArrayList<>();
            for(AdminUserDO userDO : invalidUser){
                userIdList.add(userDO.getId());
            }
            userDeptMapper.delete(new QueryWrapper<UserDeptDO>().lambda().in(UserDeptDO::getUserId,userIdList));
            userPostMapper.delete(new QueryWrapper<UserPostDO>().lambda().in(UserPostDO::getUserId,userIdList));
            userMapper.delete(new QueryWrapper<AdminUserDO>().lambda().in(AdminUserDO::getId,userIdList));
        }
        return "BaseInfoUpdateJob 执行完成";
    }
}
