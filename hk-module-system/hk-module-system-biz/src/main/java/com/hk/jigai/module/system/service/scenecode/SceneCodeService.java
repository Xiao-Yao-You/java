package com.hk.jigai.module.system.service.scenecode;

import java.util.*;
import javax.validation.*;
import com.hk.jigai.module.system.controller.admin.scenecode.vo.*;
import com.hk.jigai.module.system.dal.dataobject.scenecode.SceneCodeDO;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.PageParam;

/**
 * 单据编码类型配置 Service 接口
 *
 * @author 恒科信改
 */
public interface SceneCodeService {

    /**
     * 创建单据编码类型配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createSceneCode(@Valid SceneCodeSaveReqVO createReqVO);

    /**
     * 更新单据编码类型配置
     *
     * @param updateReqVO 更新信息
     */
    void updateSceneCode(@Valid SceneCodeSaveReqVO updateReqVO);

    /**
     * 删除单据编码类型配置
     *
     * @param id 编号
     */
    void deleteSceneCode(Integer id);

    /**
     * 获得单据编码类型配置
     *
     * @param id 编号
     * @return 单据编码类型配置
     */
    SceneCodeDO getSceneCode(Integer id);

    /**
     * 获得单据编码类型配置分页
     *
     * @param pageReqVO 分页查询
     * @return 单据编码类型配置分页
     */
    PageResult<SceneCodeDO> getSceneCodePage(SceneCodePageReqVO pageReqVO);

    /**
     * 获取各种收据号
      * @param scenceDto
     * @return
     */
    String increment(SceneCodeDO scenceDto);

}