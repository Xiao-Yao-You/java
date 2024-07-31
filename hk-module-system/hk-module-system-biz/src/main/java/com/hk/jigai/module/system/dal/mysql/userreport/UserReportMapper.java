package com.hk.jigai.module.system.dal.mysql.userreport;

import java.util.*;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hk.jigai.framework.mybatis.core.mapper.BaseMapperX;
import com.hk.jigai.module.system.controller.admin.userreport.vo.UserReportPageReqVO;
import com.hk.jigai.module.system.dal.dataobject.userreport.UserReportDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户汇报 Mapper
 *
 * @author 超级管理员
 */
@Mapper
public interface UserReportMapper extends BaseMapperX<UserReportDO> {

    default PageResult<UserReportDO> selectPage(UserReportPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserReportDO>()
                .betweenIfPresent(UserReportDO::getDateReport, reqVO.getDateReport())
                .likeIfPresent(UserReportDO::getUserNickName, reqVO.getUserNickName())
                .orderByDesc(UserReportDO::getId));
    }

}