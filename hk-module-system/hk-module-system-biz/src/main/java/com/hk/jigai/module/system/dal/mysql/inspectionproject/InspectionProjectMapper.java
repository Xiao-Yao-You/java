package com.hk.jigai.module.system.dal.mysql.inspectionproject;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.module.system.controller.admin.inspectionproject.vo.InspectionProjectPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.inspectionproject.InspectionProjectDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 巡检项目指标 Mapper
 *
 * @author 邵志伟
 */
@Mapper
public interface InspectionProjectMapper extends BaseMapperX<InspectionProjectDO> {

    default PageResult<InspectionProjectDO> selectPage(InspectionProjectPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InspectionProjectDO>()
                .eqIfPresent(InspectionProjectDO::getInspectionProject, reqVO.getInspectionProject())
                .orderByDesc(InspectionProjectDO::getId));
    }

}