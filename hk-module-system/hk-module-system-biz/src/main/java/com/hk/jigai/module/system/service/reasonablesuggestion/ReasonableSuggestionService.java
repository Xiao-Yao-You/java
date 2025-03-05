package com.hk.jigai.module.system.service.reasonablesuggestion;

import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo.ReasonableSuggestionPageReqVO;
import com.hk.jigai.module.system.controller.admin.reasonablesuggestion.vo.ReasonableSuggestionSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.reasonablesuggestion.ReasonableSuggestionDO;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.util.List;

/**
 * 合理化建议 Service 接口
 *
 * @author 邵志伟
 */
public interface ReasonableSuggestionService {

    /**
     * 创建合理化建议
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long create(@Valid ReasonableSuggestionSaveReqVO createReqVO);

    /**
     * 更新合理化建议
     *
     * @param updateReqVO 更新信息
     */
    void update(@Valid ReasonableSuggestionSaveReqVO updateReqVO);

    /**
     * 删除合理化建议
     *
     * @param id 编号
     */
    void delete(Long id);

    /**
     * 获得合理化建议
     *
     * @param id 编号
     * @return 合理化建议
     */
    ReasonableSuggestionDO get(Long id);

    /**
     * 获得合理化建议分页
     *
     * @param pageReqVO 分页查询
     * @return 合理化建议分页
     */
    PageResult<ReasonableSuggestionDO> getPage(ReasonableSuggestionPageReqVO pageReqVO);

    /**
     * 获得合理化建议分页
     *
     * @param pageReqVO 分页查询
     * @return 合理化建议分页
     */
    PageResult<ReasonableSuggestionDO> getPageForApp(ReasonableSuggestionPageReqVO pageReqVO);

    /**
     * 审核合理化建议
     *
     * @param id
     * @param examineType
     */
    void examine(Long id, Integer examineType,String remark);

    /**
     * 设置单条已读
     *
     * @param id
     */
    void read(Long id);

    /**
     * 一键已读
     */
    void allRead();

    /**
     * 获取所有未审核的合理化建议
     *
     * @return
     */
    List<ReasonableSuggestionDO> getAllSuggestion(ReasonableSuggestionPageReqVO pageReqVO);

    /**
     * 导出数据
     *
     * @param list
     */
    void exportData(List<ReasonableSuggestionDO> list, HttpServletResponse response) throws MalformedURLException;
}