package com.hk.jigai.module.system.service.inspectionproject;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.module.system.controller.admin.inspectionproject.vo.InspectionProjectPageReqVO;
import com.hk.jigai.module.system.controller.admin.inspectionproject.vo.InspectionProjectSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.inspectionproject.InspectionProjectDO;

import javax.validation.Valid;

/**
 * 巡检项目指标 Service 接口
 *
 * @author 邵志伟
 */
public interface InspectionProjectService {

    /**
     * 创建巡检项目指标
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInspectionProject(@Valid InspectionProjectSaveReqVO createReqVO);

    /**
     * 更新巡检项目指标
     *
     * @param updateReqVO 更新信息
     */
    void updateInspectionProject(@Valid InspectionProjectSaveReqVO updateReqVO);

    /**
     * 删除巡检项目指标
     *
     * @param id 编号
     */
    void deleteInspectionProject(Long id);

    /**
     * 获得巡检项目指标
     *
     * @param id 编号
     * @return 巡检项目指标
     */
    InspectionProjectDO getInspectionProject(Long id);

    /**
     * 获得巡检项目指标分页
     *
     * @param pageReqVO 分页查询
     * @return 巡检项目指标分页
     */
    PageResult<InspectionProjectDO> getInspectionProjectPage(InspectionProjectPageReqVO pageReqVO);

}