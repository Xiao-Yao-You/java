package com.hk.jigai.module.system.controller.admin.mapcoordinateinfo;

import com.hk.jigai.module.system.dal.dataobject.scenecode.SceneCodeDO;
import com.hk.jigai.module.system.service.scenecode.SceneCodeService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.security.PermitAll;
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

import com.hk.jigai.module.system.controller.admin.mapcoordinateinfo.vo.*;
import com.hk.jigai.module.system.dal.dataobject.mapcoordinateinfo.MapCoordinateInfoDO;
import com.hk.jigai.module.system.service.mapcoordinateinfo.MapCoordinateInfoService;

@Tag(name = "管理后台 - 厂区地图定位详细信息")
@RestController
@RequestMapping("/map-coordinate-info")
@Validated
public class MapCoordinateInfoController {

    @Resource
    private MapCoordinateInfoService mapCoordinateInfoService;

    @Resource
    private SceneCodeService sceneCodeService;

    @PostMapping("/create")
    @Operation(summary = "创建厂区地图定位详细信息")
    @PreAuthorize("@ss.hasPermission('hk:map-coordinate-info:create')")
    public CommonResult<Integer> createMapCoordinateInfo(@Valid @RequestBody MapCoordinateInfoSaveReqVO createReqVO) {
        return success(mapCoordinateInfoService.createMapCoordinateInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新厂区地图定位详细信息")
    @PreAuthorize("@ss.hasPermission('hk:map-coordinate-info:update')")
    public CommonResult<Boolean> updateMapCoordinateInfo(@Valid @RequestBody MapCoordinateInfoSaveReqVO updateReqVO) {
        mapCoordinateInfoService.updateMapCoordinateInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除厂区地图定位详细信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hk:map-coordinate-info:delete')")
    public CommonResult<Boolean> deleteMapCoordinateInfo(@RequestParam("id") Integer id) {
        mapCoordinateInfoService.deleteMapCoordinateInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得厂区地图定位详细信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hk:map-coordinate-info:query')")
    public CommonResult<MapCoordinateInfoRespVO> getMapCoordinateInfo(@RequestParam("id") Integer id) {
        MapCoordinateInfoDO mapCoordinateInfo = mapCoordinateInfoService.getMapCoordinateInfo(id);
        return success(BeanUtils.toBean(mapCoordinateInfo, MapCoordinateInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得厂区地图定位详细信息分页")
    @PreAuthorize("@ss.hasPermission('hk:map-coordinate-info:query')")
    public CommonResult<PageResult<MapCoordinateInfoRespVO>> getMapCoordinateInfoPage(@Valid MapCoordinateInfoPageReqVO pageReqVO) {
        PageResult<MapCoordinateInfoDO> pageResult = mapCoordinateInfoService.getMapCoordinateInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MapCoordinateInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出厂区地图定位详细信息 Excel")
    @PreAuthorize("@ss.hasPermission('hk:map-coordinate-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMapCoordinateInfoExcel(@Valid MapCoordinateInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MapCoordinateInfoDO> list = mapCoordinateInfoService.getMapCoordinateInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "厂区地图定位详细信息.xls", "数据", MapCoordinateInfoRespVO.class,
                        BeanUtils.toBean(list, MapCoordinateInfoRespVO.class));
    }

    @GetMapping("/queryAll")
    @PermitAll
    @Operation(summary = "获得厂区地图定位详细信息分页")
    public CommonResult<MapCoordinateInfoAllRespVO> getMapCoordinateInfoAll() {
        MapCoordinateInfoAllRespVO result = new MapCoordinateInfoAllRespVO();
        result.setList(mapCoordinateInfoService.getMapCoordinateInfoAll());
//        try{
//                    SceneCodeDO dto = new SceneCodeDO();
//        dto.setSuffix("000000");
//        dto.setInfix("yyyMMdd");
//        dto.setStart(100);
//        dto.setDescription("测试");
//        dto.setStatus("00");
//        dto.setKey("pay");
//        dto.setId(11);
//        dto.setStep(10);
//        dto.setType("02");
//        dto.setPrefix("wft");
//        sceneCodeService.increment(dto);
//        } catch (Exception e){
//
//        }
        return success(result);
    }
}