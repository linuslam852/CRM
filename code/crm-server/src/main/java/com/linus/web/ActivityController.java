package com.linus.web;

import com.github.pagehelper.PageInfo;
import com.linus.model.TActivity;
import com.linus.result.R;
import com.linus.service.ActivityService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @GetMapping("/api/activitys")
    public R activityPage(@RequestParam(value = "current", required = false) Integer current){
        if(current == null){
            current = 1;
        }
        PageInfo<TActivity> pageInfo = activityService.getActivityByPage(current);

        return R.OK(pageInfo);
    }
}
