package com.hk.jigai.module.system.service.userreport;

import com.hk.jigai.module.system.controller.admin.userreport.vo.UserReportPageReqVO;
import com.hk.jigai.module.system.controller.admin.userreport.vo.UserReportSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.userreport.UserReportDO;
import com.hk.jigai.module.system.dal.mysql.userreport.UserReportMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.USER_REPORT_NOT_EXISTS;

/**
 * 用户汇报 Service 实现类
 *
 * @author 超级管理员
 */
@Service
@Validated
public class UserReportServiceImpl implements UserReportService {

    @Resource
    private UserReportMapper userReportMapper;

    @Override
    public Long createUserReport(UserReportSaveReqVO createReqVO) {
        // 插入
        UserReportDO userReport = BeanUtils.toBean(createReqVO, UserReportDO.class);
        userReportMapper.insert(userReport);
        // 返回
        return userReport.getId();
    }

    @Override
    public void updateUserReport(UserReportSaveReqVO updateReqVO) {
        // 校验存在
        validateUserReportExists(updateReqVO.getId());
        // 更新
        UserReportDO updateObj = BeanUtils.toBean(updateReqVO, UserReportDO.class);
        userReportMapper.updateById(updateObj);
    }

    @Override
    public void deleteUserReport(Long id) {
        // 校验存在
        validateUserReportExists(id);
        // 删除
        userReportMapper.deleteById(id);
    }

    private void validateUserReportExists(Long id) {
        if (userReportMapper.selectById(id) == null) {
            throw exception(USER_REPORT_NOT_EXISTS);
        }
    }

    @Override
    public UserReportDO getUserReport(Long id) {
        return userReportMapper.selectById(id);
    }

    @Override
    public PageResult<UserReportDO> getUserReportPage(UserReportPageReqVO pageReqVO) {
        return userReportMapper.selectPage(pageReqVO);
    }

}