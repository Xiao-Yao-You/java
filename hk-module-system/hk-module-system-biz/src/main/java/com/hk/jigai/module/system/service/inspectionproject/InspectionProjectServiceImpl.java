package com.hk.jigai.module.system.service.inspectionproject;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;
import com.hk.jigai.module.system.controller.admin.inspectionproject.vo.InspectionProjectPageReqVO;
import com.hk.jigai.module.system.controller.admin.inspectionproject.vo.InspectionProjectSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.inspectionproject.InspectionProjectDO;
import com.hk.jigai.module.system.dal.mysql.inspectionproject.InspectionProjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.INSPECTION_PROJECT_NOT_EXISTS;


/**
 * 巡检项目指标 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class InspectionProjectServiceImpl implements InspectionProjectService {

    @Resource
    private InspectionProjectMapper inspectionProjectMapper;

    @Override
    public Long createInspectionProject(InspectionProjectSaveReqVO createReqVO) {
        // 插入
        InspectionProjectDO inspectionProject = BeanUtils.toBean(createReqVO, InspectionProjectDO.class);
        inspectionProjectMapper.insert(inspectionProject);
        // 返回
        return inspectionProject.getId();
    }

    @Override
    public void updateInspectionProject(InspectionProjectSaveReqVO updateReqVO) {
        // 校验存在
        validateInspectionProjectExists(updateReqVO.getId());
        // 更新
        InspectionProjectDO updateObj = BeanUtils.toBean(updateReqVO, InspectionProjectDO.class);
        inspectionProjectMapper.updateById(updateObj);
    }

    @Override
    public void deleteInspectionProject(Long id) {
        // 校验存在
        validateInspectionProjectExists(id);
        // 删除
        inspectionProjectMapper.deleteById(id);
    }

    private void validateInspectionProjectExists(Long id) {
        if (inspectionProjectMapper.selectById(id) == null) {
            throw exception(INSPECTION_PROJECT_NOT_EXISTS);
        }
    }

    @Override
    public InspectionProjectDO getInspectionProject(Long id) {
        return inspectionProjectMapper.selectById(id);
    }

    @Override
    public PageResult<InspectionProjectDO> getInspectionProjectPage(InspectionProjectPageReqVO pageReqVO) {
        return inspectionProjectMapper.selectPage(pageReqVO);
    }

}