package com.linus.config.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.linus.CrmServerApplication;
import com.linus.model.TDicValue;
import com.linus.result.DicEnum;

import java.util.List;

public class IntentionStateConverter implements Converter<Integer> {
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception{
        String cellIntentionStateName =  cellData.getStringValue();

        List<TDicValue> tDicValueList = (List<TDicValue>) CrmServerApplication.cacheMap.get(DicEnum.INTENTIONSTATE.getCode());
        for(TDicValue tDicValue : tDicValueList){
            Integer id = tDicValue.getId();
            String name = tDicValue.getTypeValue();
            if(cellIntentionStateName.equals(name)){
                return id;
            }
        }
        return -1;
    }
}
