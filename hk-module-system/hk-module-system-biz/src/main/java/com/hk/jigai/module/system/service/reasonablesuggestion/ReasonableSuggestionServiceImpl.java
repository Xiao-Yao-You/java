package com.hk.jigai.module.system.service.reasonablesuggestion;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hk.jigai.module.system.controller.admin.operation.vo.OperationDevicePictureSaveReqVO;
import com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo.ReasonableSuggestionPageReqVO;
import com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo.ReasonableSuggestionSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.dept.DeptDO;
import com.hk.jigai.module.system.dal.dataobject.reasonablesuggestion.ReasonableSuggestionDO;
import com.hk.jigai.module.system.dal.mysql.dept.DeptMapper;
import com.hk.jigai.module.system.dal.mysql.reasonablesuggestion.ReasonableSuggestionMapper;
import jodd.util.CollectionUtil;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.util.object.BeanUtils;


import static com.hk.jigai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hk.jigai.module.system.enums.ErrorCodeConstants.REASONABLE_SUGGESTION_NOT_EXISTS;

/**
 * 合理化建议 Service 实现类
 *
 * @author 邵志伟
 */
@Service
@Validated
public class ReasonableSuggestionServiceImpl implements ReasonableSuggestionService {

    @Resource
    private ReasonableSuggestionMapper reasonableSuggestionMapper;

    @Resource
    private DeptMapper deptMapper;

    @Override
    public Long create(ReasonableSuggestionSaveReqVO createReqVO) {
        // 插入
        ReasonableSuggestionDO reasonableSuggestionDO = BeanUtils.toBean(createReqVO, ReasonableSuggestionDO.class);
        reasonableSuggestionDO.setStatus(1);//未审核
        List<OperationDevicePictureSaveReqVO> fileList = createReqVO.getFileList();
        if (CollectionUtils.isNotEmpty(fileList)) {
            List<String> urls = fileList.stream().map(p -> p.getUrl()).collect(Collectors.toList());
            String filePath = String.join(";", urls);
            reasonableSuggestionDO.setFilePath(filePath);
        }
        reasonableSuggestionDO.setStatus(1);
        DeptDO deptDO = deptMapper.selectById(createReqVO.getDeptId());
        reasonableSuggestionDO.setDeptName(deptDO.getName());
        reasonableSuggestionMapper.insert(reasonableSuggestionDO);
        // 返回
        return reasonableSuggestionDO.getId();
    }

    @Override
    public void update(ReasonableSuggestionSaveReqVO updateReqVO) {
        // 校验存在
        validateExists(updateReqVO.getId());
        // 更新
        ReasonableSuggestionDO updateObj = BeanUtils.toBean(updateReqVO, ReasonableSuggestionDO.class);
        List<OperationDevicePictureSaveReqVO> fileList = updateReqVO.getFileList();
        if (CollectionUtils.isNotEmpty(fileList)) {
            List<String> urls = fileList.stream().map(p -> p.getUrl()).collect(Collectors.toList());
            String filePath = String.join(";", urls);
            updateObj.setFilePath(filePath);
        } else {
            updateObj.setFilePath(null);
        }
        DeptDO deptDO = deptMapper.selectById(updateObj.getDeptId());
        updateObj.setDeptName(deptDO.getName());
        reasonableSuggestionMapper.updateById(updateObj);
    }

    @Override
    public void delete(Long id) {
        // 校验存在
        validateExists(id);
        // 删除
        reasonableSuggestionMapper.deleteById(id);
    }

    private void validateExists(Long id) {
        if (reasonableSuggestionMapper.selectById(id) == null) {
            throw exception(REASONABLE_SUGGESTION_NOT_EXISTS);
        }
    }

    @Override
    public void examine(Long id, Integer examineType) {
        // 校验存在
        validateExists(id);
        ReasonableSuggestionDO reasonableSuggestionDO = reasonableSuggestionMapper.selectById(id);
        reasonableSuggestionDO.setStatus(examineType);
        reasonableSuggestionMapper.updateById(reasonableSuggestionDO);
    }

    @Override
    public ReasonableSuggestionDO get(Long id) {

        ReasonableSuggestionDO reasonableSuggestionDO = reasonableSuggestionMapper.selectById(id);
        String filePath = reasonableSuggestionDO.getFilePath();
        if (StringUtil.isNotBlank(filePath)) {
            String[] split = filePath.split(";");
            List<String> strings = Arrays.asList(split);
            List<OperationDevicePictureSaveReqVO> list = new ArrayList<>();
            for (String string : strings) {
                OperationDevicePictureSaveReqVO operationDevicePictureSaveReqVO = new OperationDevicePictureSaveReqVO();
                operationDevicePictureSaveReqVO.setUrl(string);
                list.add(operationDevicePictureSaveReqVO);
            }
            reasonableSuggestionDO.setFileList(list);
        }
        return reasonableSuggestionDO;
    }

    @Override
    public PageResult<ReasonableSuggestionDO> getPage(ReasonableSuggestionPageReqVO pageReqVO) {
        return reasonableSuggestionMapper.selectPage(pageReqVO);
    }

}