package com.linus.web;

import com.linus.CrmServerApplication;
import com.linus.model.TActivity;
import com.linus.model.TDicValue;
import com.linus.model.TProduct;
import com.linus.result.DicEnum;
import com.linus.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DicController {

    @GetMapping("/api/dicvalue/{typeCode}")
    public R dicData(@PathVariable("typeCode")String typeCode){
        if(typeCode.equals(DicEnum.ACTIVITY.getCode())){
            List<TActivity> tActivityList = (List<TActivity>) CrmServerApplication.cacheMap.get(typeCode);
            return R.OK(tActivityList);
        }else if(typeCode.equals(DicEnum.PRODUCT.getCode())){
            List<TProduct> tProductList = (List<TProduct>) CrmServerApplication.cacheMap.get(typeCode);
            return R.OK(tProductList);
        } else{
            List<TDicValue> tDicValueList = (List<TDicValue>) CrmServerApplication.cacheMap.get(typeCode);
            return R.OK(tDicValueList);
        }
    }

}
