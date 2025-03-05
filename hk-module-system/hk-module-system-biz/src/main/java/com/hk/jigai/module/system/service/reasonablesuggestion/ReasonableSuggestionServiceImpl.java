package com.hk.jigai.module.system.service.reasonablesuggestion;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hk.jigai.framework.excel.core.util.CustomImageModifyStrategy;
import com.hk.jigai.framework.excel.core.util.ExcelUtils;
import com.hk.jigai.framework.security.core.util.SecurityFrameworkUtils;
import com.hk.jigai.module.system.controller.admin.operation.vo.OperationDevicePictureSaveReqVO;
import com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo.ReasonableSuggestionExportReqVO;
import com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo.ReasonableSuggestionPageReqVO;
import com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo.ReasonableSuggestionSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.dept.DeptDO;
import com.hk.jigai.module.system.dal.dataobject.reasonablesuggestion.ReasonableSuggestionDO;
import com.hk.jigai.module.system.dal.mysql.dept.DeptMapper;
import com.hk.jigai.module.system.dal.mysql.reasonablesuggestion.ReasonableSuggestionMapper;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.annotation.Validated;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.hk.jigai.framework.common.pojo.PageResult;
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
        reasonableSuggestionDO.setStatus(5);//未审核
        List<OperationDevicePictureSaveReqVO> fileList = createReqVO.getFileList();
        if (CollectionUtils.isNotEmpty(fileList)) {
            List<String> urls = fileList.stream().map(p -> p.getUrl()).collect(Collectors.toList());
            String filePath = String.join(";", urls);
            reasonableSuggestionDO.setFilePath(filePath);
        }
//        DeptDO deptDO = deptMapper.selectById(createReqVO.getDeptId());
//        reasonableSuggestionDO.setDeptName(deptDO.getName());
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
//        DeptDO deptDO = deptMapper.selectById(updateObj.getDeptId());
//        updateObj.setDeptName(deptDO.getName());
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
    public void examine(Long id, Integer examineType, String remark) {
        // 校验存在
        validateExists(id);
        ReasonableSuggestionDO reasonableSuggestionDO = reasonableSuggestionMapper.selectById(id);
        reasonableSuggestionDO.setStatus(examineType);
        reasonableSuggestionDO.setRemark(remark);
        reasonableSuggestionMapper.updateById(reasonableSuggestionDO);
    }

    /**
     * 更新单条已读状态
     *
     * @param id
     */
    @Override
    public void read(Long id) {
        // 校验存在
        validateExists(id);
        ReasonableSuggestionDO reasonableSuggestionDO = reasonableSuggestionMapper.selectById(id);
        reasonableSuggestionDO.setStatus(4);
        reasonableSuggestionMapper.updateById(reasonableSuggestionDO);
    }

    @Override
    public void allRead() {
        //将所有未读建议设置成已读状态
        reasonableSuggestionMapper.update(new UpdateWrapper<ReasonableSuggestionDO>().lambda()
                .set(ReasonableSuggestionDO::getStatus, 4)
                .eq(ReasonableSuggestionDO::getStatus, 5));
    }

    @Override
    public List<ReasonableSuggestionDO> getAllSuggestion(ReasonableSuggestionPageReqVO pageReqVO) {
        //查询所有未审核的合理化,4:已读
        pageReqVO.setPageSize(10000);
        pageReqVO.setPageNo(1);
//        List<ReasonableSuggestionDO> reasonableSuggestionDOS = reasonableSuggestionMapper.selectList(new QueryWrapper<ReasonableSuggestionDO>().lambda()
//                .eq(ReasonableSuggestionDO::getStatus, 4));
        PageResult<ReasonableSuggestionDO> reasonableSuggestionDOPageResult = reasonableSuggestionMapper.selectPage(pageReqVO);
        List<ReasonableSuggestionDO> reasonableSuggestionDOS = reasonableSuggestionDOPageResult.getList();
        reasonableSuggestionDOS.forEach(r -> {
            if ("1".equals(r.getAnonymous())) {
                r.setNickname("— —");
                r.setWorkNum("— —");
            }
        });
        return reasonableSuggestionDOS;
    }

    @Override
    public void exportData(List<ReasonableSuggestionDO> dataSource, HttpServletResponse response) {

        // 输出文件路径
//        String fileName = "D://合理化建议.xlsx";

        try {
            List<ReasonableSuggestionExportReqVO> reasonableSuggestionExportReqVOS = new ArrayList<>();
            for (ReasonableSuggestionDO reasonableSuggestionDO : dataSource) {
                ReasonableSuggestionExportReqVO reasonableSuggestionExportReqVO = BeanUtils.toBean(reasonableSuggestionDO, ReasonableSuggestionExportReqVO.class);
                String filePaths = reasonableSuggestionDO.getFilePath();
                if (StringUtil.isNotBlank(filePaths)) {
                    String[] split = filePaths.split(";");
                    List<URL> urlList = new ArrayList<>();
                    if (split.length > 0) {
                        List<String> urlStr = Arrays.asList(split);
                        for (String url : urlStr) {
                            urlList.add(new URL(url));
                        }
                    }
                    reasonableSuggestionExportReqVO.setImgList(urlList);
                }
                reasonableSuggestionExportReqVOS.add(reasonableSuggestionExportReqVO);
            }
            // 图片列最大图片数
            AtomicReference<Integer> maxImageSize = new AtomicReference<>(0);
            reasonableSuggestionExportReqVOS.forEach(item -> {
                // 最大图片数大小
                if (!CollectionUtils.isEmpty(item.getImgList()) && item.getImgList().size() > maxImageSize.get()) {
                    maxImageSize.set(item.getImgList().size());
                }
            });

            ExcelUtils.write2(response, "合理化建议.xls", "数据", ReasonableSuggestionExportReqVO.class,
                    BeanUtils.toBean(reasonableSuggestionExportReqVOS, ReasonableSuggestionExportReqVO.class), new CustomImageModifyStrategy(100, 32));

        } catch (Exception e) {
            System.out.println("导出异常");
        }
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


    @Override
    public PageResult<ReasonableSuggestionDO> getPageForApp(ReasonableSuggestionPageReqVO pageReqVO) {
        pageReqVO.setCreator(SecurityFrameworkUtils.getLoginUserId());
        return reasonableSuggestionMapper.selectPage(pageReqVO);
    }

}