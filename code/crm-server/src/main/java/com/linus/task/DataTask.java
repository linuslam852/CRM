package com.linus.task;

import com.linus.CrmServerApplication;
import com.linus.model.TActivity;
import com.linus.model.TDicType;
import com.linus.model.TDicValue;
import com.linus.model.TProduct;
import com.linus.result.DicEnum;
import com.linus.service.ActivityService;
import com.linus.service.DicTypeService;
import com.linus.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@Component
public class DataTask {

    @Resource
    private DicTypeService dicTypeService;

    @Resource
    private ProductService productService;

    @Resource
    private ActivityService activityService;

    @Scheduled(fixedDelayString = "${project.task.delay}", zone = "Asia/Shanghai",timeUnit = TimeUnit.MILLISECONDS,initialDelay = 1000)
    public void task(){
       List<TDicType> tDicTypeList =  dicTypeService.loadAllDicData();
       tDicTypeList.forEach(tDicType -> {
           String typeCode = tDicType.getTypeCode();
           List<TDicValue> tDicValueList = tDicType.getDicValueList();
           CrmServerApplication.cacheMap.put(typeCode,tDicValueList);
       });

       List<TProduct> tProductList = productService.getAllOnSaleProduct();
       CrmServerApplication.cacheMap.put(DicEnum.PRODUCT.getCode(), tProductList);

       //查詢目前正在進行的活動
        List<TActivity> tActivitiesList = activityService.getOnGoingActivities();
        CrmServerApplication.cacheMap.put(DicEnum.ACTIVITY.getCode(), tActivitiesList);


    }
}

