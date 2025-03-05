package com.hk.jigai.module.system.controller.admin.operationgroup;

import com.hk.jigai.module.system.controller.admin.operationgroup.vo.OperationGroupPageReqVO;
import com.hk.jigai.module.system.controller.admin.operationgroup.vo.OperationGroupRespVO;
import com.hk.jigai.module.system.controller.admin.operationgroup.vo.OperationGroupSaveReqVO;
import com.hk.jigai.module.system.dal.dataobject.operationgroup.OperationGroupDO;
import com.hk.jigai.module.system.dal.dataobject.user.AdminUserDO;
import com.hk.jigai.module.system.service.operationgroup.OperationGroupService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.hk.jigai.framework.common.pojo.PageParam;
import com.hk.jigai.framework.common.pojo.PageResult;
import com.hk.jigai.framework.common.pojo.CommonResult;
import com.hk.jigai.framework.common.util.object.BeanUtils;

import static com.hk.jigai.framework.common.pojo.CommonResult.success;

import com.hk.jigai.framework.excel.core.util.ExcelUtils;

import com.hk.jigai.framework.apilog.core.annotation.ApiAccessLog;

import static com.hk.jigai.framework.apilog.core.enums.OperateTypeEnum.*;


@Tag(name = "管理后台 - 运维小组")
@RestController
@RequestMapping("/operation-group")
@Validated
public class OperationGroupController {

    @Resource
    private OperationGroupService operationGroupService;

    @PostMapping("/create")
    @Operation(summary = "创建运维小组")
    @PreAuthorize("@ss.hasPermission('hk:operation-group:create')")
    public CommonResult<Long> createOperationGroup(@Valid @RequestBody OperationGroupSaveReqVO createReqVO) {
        return success(operationGroupService.createOperationGroup(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新运维小组")
    @PreAuthorize("@ss.hasPermission('hk:operation-group:update')")
    public CommonResult<Boolean> updateOperationGroup(@Valid @RequestBody OperationGroupSaveReqVO updateReqVO) {
        operationGroupService.updateOperationGroup(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除运维小组")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:operation-group:delete')")
    public CommonResult<Boolean> deleteOperationGroup(@RequestParam("id") Long id) {
        operationGroupService.deleteOperationGroup(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得运维小组")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:operation-group:query')")
    public CommonResult<OperationGroupRespVO> getOperationGroup(@RequestParam("id") Long id) {
        OperationGroupDO operationGroup = operationGroupService.getOperationGroup(id);
        return success(BeanUtils.toBean(operationGroup, OperationGroupRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得运维小组分页")
    @PreAuthorize("@ss.hasPermission('hk:operation-group:query')")
    public CommonResult<PageResult<OperationGroupRespVO>> getOperationGroupPage(@Valid OperationGroupPageReqVO pageReqVO) {
        PageResult<OperationGroupDO> pageResult = operationGroupService.getOperationGroupPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OperationGroupRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出运维小组 Excel")
    @PreAuthorize("@ss.hasPermission('hk:operation-group:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOperationGroupExcel(@Valid OperationGroupPageReqVO pageReqVO,
                                          HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperationGroupDO> list = operationGroupService.getOperationGroupPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "运维小组.xls", "数据", OperationGroupRespVO.class,
                BeanUtils.toBean(list, OperationGroupRespVO.class));
    }

    @GetMapping("/getAllGroup")
    @Operation(summary = "获取所有分组")
    public CommonResult<List<OperationGroupRespVO>> getAllGroup() {
        List<OperationGroupDO> list = operationGroupService.getAllGroup();
        return success(BeanUtils.toBean(list, OperationGroupRespVO.class));
    }

    @GetMapping("/getGroupUsers")
    @Operation(summary = "根据分组id获取所有分组")
    public CommonResult<List<AdminUserDO>> getGroupUsers(@RequestParam("groupId") Long groupId) {
        List<AdminUserDO> list = operationGroupService.getGroupUsers(groupId);
        return success(list);
    }

    @GetMapping("/getGroupByUserId")
    @Operation(summary = "根据用户id获取所在分组")
    public CommonResult<List<OperationGroupRespVO>> getGroupByUserId(@RequestParam("userId") Long userId) {
        List<OperationGroupDO> list = operationGroupService.getGroupByUserId(userId);
        return success(BeanUtils.toBean(list, OperationGroupRespVO.class));
    }


}