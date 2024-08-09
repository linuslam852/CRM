package com.linus.web;

import com.github.pagehelper.PageInfo;
import com.linus.model.TActivity;
import com.linus.query.ActivityQuery;
import com.linus.query.UserQuery;
import com.linus.result.R;
import com.linus.service.ActivityService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @GetMapping("/api/activitys")
    public R activityPage(@RequestParam(value = "current", required = false) Integer current, ActivityQuery activityQuery){
        if(current == null){
            current = 1;
        }
        PageInfo<TActivity> pageInfo = activityService.getActivityByPage(current, activityQuery);

        return R.OK(pageInfo);
    }


    @PostMapping("/api/activity")
    public R addActivity(ActivityQuery activityQuery, @RequestHeader("Authorization") String token){
        activityQuery.setToken(token);
        int save = activityService.saveActivity(activityQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }

    @GetMapping("/api/activity/{id}")
    public R loadActivity(@PathVariable("id")Integer id){
        TActivity tActivity = activityService.getActivityById(id);
        return R.OK(tActivity);
    }

    @PutMapping("/api/activity")
    public R editUser(ActivityQuery activityQuery, @RequestHeader("Authorization") String token){
        activityQuery.setToken(token);
        int update = activityService.updateActivity(activityQuery);
        return update >= 1 ? R.OK() : R.FAIL();
    }

    @DeleteMapping("/api/activity/{id}")
    public R delActivity(@PathVariable("id")Integer id){
        int del = activityService.delActivityById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }

    @DeleteMapping("/api/activity")
    public R batchDelActivity(@RequestParam("ids")String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        int batchDel = activityService.batchDelActivityByIds(idList);
        return batchDel >= idList.size() ? R.OK() : R.FAIL();
    }
}
