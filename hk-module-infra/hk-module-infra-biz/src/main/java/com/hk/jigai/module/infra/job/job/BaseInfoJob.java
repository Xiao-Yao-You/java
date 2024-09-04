package com.hk.jigai.module.infra.job.job;

import cn.hutool.core.collection.CollectionUtil;
import com.hk.jigai.framework.common.enums.CommonStatusEnum;
import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.framework.quartz.core.handler.JobHandler;
import com.hk.jigai.framework.tenant.core.context.TenantContextHolder;
import com.hk.jigai.module.infra.controller.admin.job.vo.job.BaseInfoVO;
import com.hk.jigai.module.infra.enums.job.JobConstant;
import com.hk.jigai.module.infra.job.job.dto.DeptDTO;
import com.hk.jigai.module.infra.job.job.dto.PostDTO;
import com.hk.jigai.module.infra.service.job.BaseInfoService;
import com.hk.jigai.module.infra.service.job.JobLogService;
import com.hk.jigai.module.system.dal.dataobject.dept.DeptDO;
import com.hk.jigai.module.system.dal.dataobject.dept.PostDO;
import com.hk.jigai.module.system.dal.dataobject.dept.UserDeptDO;
import com.hk.jigai.module.system.dal.dataobject.dept.UserPostDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.dal.mysql.dept.DeptMapper;
import com.hk.jigai.module.system.dal.mysql.dept.PostMapper;
import com.hk.jigai.module.system.dal.mysql.dept.UserDeptMapper;
import com.hk.jigai.module.system.dal.mysql.dept.UserPostMapper;
import com.hk.jigai.module.system.dal.mysql.user.AdminUserMapper;
import com.hk.jigai.module.system.enums.common.SexEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static com.hk.jigai.framework.common.util.collection.CollectionUtils.convertList;

@Slf4j
@Component
public class BaseInfoJob implements JobHandler {

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

    private static int deptid;
    private static int postId;
    private static int userId;
    private static int userDeptId;
    private static int userPostId;

    @Override
    public String execute(String param) throws Exception {
        deptid = 500;
        postId = 500;
        userId = 500;
        userDeptId = 500;
        userPostId = 500;

        TenantContextHolder.setTenantId(new Long(JobConstant.TENANT_ID));

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
        DeptDTO root = new DeptDTO(new Long(JobConstant.ROOT_DEPT_ID),JobConstant.ROOT_DEPT_NAME,null);//民用丝部/内贸部/差别化销售部/恒科差别化产品二部
        List<DeptDO> deptList = initDeptList();
        List<UserDeptDO> userDeptDOS = new ArrayList<>();

        List<PostDO> postList = new ArrayList();
        List<UserPostDO> userPostDOS = new ArrayList<>();
        Map<String,Long> postMap = new HashMap<>();
        for(BaseInfoVO baseInfo : baseInfoList){
            baseInfo.setId(new Long(userId));
            if("男".equals(baseInfo.getGender())){
                baseInfo.setSex(SexEnum.MALE.getSex());
            }else if("女".equals(baseInfo.getGender())){
                baseInfo.setSex(SexEnum.FEMALE.getSex());
            }else{
                baseInfo.setSex(SexEnum.UNKNOWN.getSex());
            }
            String deptDesc = baseInfo.getDep_fullName();
            if(StringUtils.isNotBlank(deptDesc)){
                buildDapartmentTree(new Long(userId), root,deptDesc,deptList,userDeptDOS);
            }
            String postName = baseInfo.getPersonJob();
            if(StringUtils.isNotBlank(postName)){
                if(postMap.get(postName) == null){
                    postMap.put(postName, new Long(postId));
                    PostDO postDTO = new PostDO();
                    postDTO.setId(new Long(postId));
                    postDTO.setName(postName);
                    postDTO.setCode(baseInfo.getPersonType());
                    postDTO.setSort(new Integer(String.valueOf(postId)));
                    postDTO.setStatus(new Integer(0));
                    postList.add(postDTO);
                    postId++;
                }
                UserPostDO userPostDO = new UserPostDO();
                Long newUserPostId = new Long(userPostId++);
                userPostDO.setId(newUserPostId);
                userPostDO.setPostId(postMap.get(postName));
                userPostDO.setUserId(new Long(userId));
                userPostDOS.add(userPostDO);

                Set<Long> userPostSet = new HashSet<>();
                userPostSet.add(userPostDO.getId());
                baseInfo.setUserPostSet(userPostSet);
            }
            userId++;
        }
        //部门基表全删全插 todo 根据部门全称先查后插,如果已经存在则简单更新，如果不存在插入
        if(!CollectionUtils.isAnyEmpty(deptList)){
            deptMapper.insertBatch(deptList);
        }
        //职位基表全删全插 todo 根据职位全称先查后插,如果已经存在则简单更新，如果不存在插入
        if(!CollectionUtils.isAnyEmpty(postList)){
            postMapper.insertBatch(postList);
        }
        //4.人员处理

        //4.1 插入人员  todo 根据员工no先查后插,如果已经存在则简单更新，如果不存在插入
        userMapper.insertBatch(CollectionUtils.convertList(baseInfoList,
                baseInfo -> new AdminUserDO().setId(baseInfo.getId()).setStatus(0).setPassword(passwordEncoder.encode(JobConstant.FIRST_PASSWORD))
                        .setUsername(baseInfo.getPersonNO()).setNickname(baseInfo.getPersonName()).setSex(baseInfo.getSex())
                        .setMobile(baseInfo.getPhone()).setPostIds(baseInfo.getUserPostSet())));
        // 4.2 插入关联岗位  todo 根据userId 查询是否有数据，如果存在则更新，不存在则插入
        if (CollectionUtil.isNotEmpty(userPostDOS)){
            userPostMapper.insertBatch(userPostDOS);
        }
        // 4.3 插入关联部门  todo 根据userId 查询是否有数据，如果存在则更新，不存在则插入
        if (CollectionUtil.isNotEmpty(userDeptDOS)) {
            userDeptMapper.insertBatch(userDeptDOS);
        }
        return "BaseInfoJob 执行完成";
    }

    private List<DeptDO> initDeptList(){
        List<DeptDO> deptList = new ArrayList<>();
        DeptDO firstDO = new DeptDO();
        firstDO.setSort(JobConstant.ROOT_DEPT_ID);
        firstDO.setId(new Long(JobConstant.ROOT_DEPT_ID));
        firstDO.setName(JobConstant.ROOT_DEPT_NAME);
        firstDO.setParentId(null);
        firstDO.setStatus(new Integer(0));
        deptList.add(firstDO);
        return deptList;
    }

    private static Long buildDapartmentTree(Long userId, DeptDTO root, String deptDesc, List<DeptDO> deptDOList, List<UserDeptDO> userDeptDOS){
        Long returnId = new Long(0);
        String[] deptArray = deptDesc.split(JobConstant.SPLIT_CHAR);
        if(deptArray != null && deptArray.length > 0){
            Long parentId = new Long(JobConstant.ROOT_DEPT_ID);
            StringBuffer deptFullName = new StringBuffer("");
            for(String deptName : deptArray) {
                deptFullName.append("/" + deptName);
                DeptDTO currentDept =  findDepartmentByName(root, deptName, parentId);
                if(currentDept == null){
                    Long newId = new Long(deptid++);
                    currentDept = new DeptDTO(newId,deptName,parentId);
                    DeptDO deptDO = new DeptDO();
                    deptDO.setId(newId);
                    deptDO.setName(deptName);
                    deptDO.setFullName(deptFullName.toString());
                    deptDO.setParentId(parentId);
                    deptDO.setSort(new Integer(String.valueOf(newId)));
                    deptDO.setStatus(new Integer(0));
                    deptDOList.add(deptDO);
                    DeptDTO parentDept = findParentDepartment(root, parentId);
                    List<DeptDTO> childList = parentDept.getChildren();
                    if(CollectionUtils.isAnyEmpty(childList)){
                        childList = new ArrayList<>();
                        childList.add(currentDept);
                        parentDept.setChildren(childList);
                    }else{
                        childList.add(currentDept);
                    }
                    parentId = newId;
                    returnId = newId;
                    continue;
                }else{
                    parentId = currentDept.getId();
                    returnId = currentDept.getId();
                    continue;
                }
            }
        }
        UserDeptDO userDeptDO = new UserDeptDO();
        Long newUserDepId = new Long(userDeptId++);
        userDeptDO.setId(newUserDepId);
        userDeptDO.setUserId(userId);
        userDeptDO.setDeptId(returnId);
        userDeptDOS.add(userDeptDO);
        return returnId;
    }

    private static DeptDTO findParentDepartment(DeptDTO department, Long parentId) {
        if (department.getId().equals(parentId)) {
            return department;
        }
        for (DeptDTO child : department.getChildren()) {
            DeptDTO result = findParentDepartment(child, parentId);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static DeptDTO findDepartmentByName(DeptDTO department, String name, Long parentId) {
        if (department.getName().equals(name) && department.getParentId().equals(parentId)) {
            return department;
        }
        for (DeptDTO child : department.getChildren()) {
            DeptDTO result = findDepartmentByName(child, name, parentId);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    //    public static void main(String[] args) {
//        DeptDTO root = new DeptDTO(new Long(0),"恒科信改",null);
//        String deptDesc = "民用丝部/内贸部/差别化销售部/恒科差别化产品二部";
//        Long newId = buildDapartmentTree(root, deptDesc);
//        System.out.println(newId);
//        System.out.println(root.getChildren().size());
//        String deptDesc2 = "民用丝部2/内贸部/差别化销售部2/恒科差别化产品二部2";
//        Long newId2 = buildDapartmentTree(root, deptDesc2);
//        System.out.println(newId2);
//        System.out.println(root.getChildren().size());
//        String deptDesc3 = "民用丝部/内贸部/差别化销售部2/恒科差别化产品二部2";
//        Long newId3 = buildDapartmentTree(root, deptDesc3,);
//        System.out.println(newId3);
//        System.out.println(root.getChildren().size());
//    }

}
