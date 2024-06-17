package com.hk.jigai.framework.excel.core.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.hk.jigai.framework.common.util.collection.CollectionUtils;
import com.hk.jigai.module.system.api.dept.dto.DeptRespDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 部门对象转换
 */
@Slf4j
public class DeptConverter implements Converter<List<DeptRespDTO>> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return List.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public List<DeptRespDTO> convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return Converter.super.convertToJavaData(cellData, contentProperty, globalConfiguration);
    }

    @Override
    public List<DeptRespDTO> convertToJavaData(ReadConverterContext<?> context) throws Exception {
        return Converter.super.convertToJavaData(context);
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<List<DeptRespDTO>> context) throws Exception {
        StringBuffer sb = new StringBuffer("");
        if(!CollectionUtils.isAnyEmpty(context.getValue())){
            context.getValue().forEach(dto->{sb.append(dto.getName()).append("\n");});
        }
        return new WriteCellData(sb.toString());
    }
}
