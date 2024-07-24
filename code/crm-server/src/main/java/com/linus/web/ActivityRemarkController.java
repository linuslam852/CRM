package com.linus.web;

import com.github.pagehelper.PageInfo;
import com.linus.model.TActivityRemark;
import com.linus.query.ActivityRemarkQuery;
import com.linus.result.R;
import com.linus.service.ActivityRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityRemarkController {

    @Resource
    private ActivityRemarkService activityRemarkService;

    @PostMapping("/api/activity/remark")
    public R addActivityRemark(@RequestBody ActivityRemarkQuery activityRemarkQuery, @RequestHeader("Authorization")String token){
        activityRemarkQuery.setToken(token);
        int save = activityRemarkService.saveActivityRemark(activityRemarkQuery);
        return save >= 1 ? R.OK() : R.FAIL();

    }

    @GetMapping("/api/activity/remark")
    public R activityRemarkPage(@RequestParam(value = "current", required = false) Integer current,@RequestParam(value = "activityId") Integer activityId){
        ActivityRemarkQuery activityRemarkQuery = new ActivityRemarkQuery();
        activityRemarkQuery.setActivityId(activityId);
        if(current == null){
            current = 1;
        }
        PageInfo<TActivityRemark> pageInfo = activityRemarkService.getActivityRemarkByPage(current, activityRemarkQuery);

        return R.OK(pageInfo);
    }

    @GetMapping("/api/activity/remark/{id}")
    public R activityRemarkPage(@PathVariable(value = "id") Integer id){
        TActivityRemark tActivityRemark = activityRemarkService.getActivityRemarkById(id);
        return R.OK(tActivityRemark);
    }

    @PutMapping("/api/activity/remark")
    public R editActivityRemark(@RequestBody ActivityRemarkQuery activityRemarkQuery, @RequestHeader("Authorization")String token){
        activityRemarkQuery.setToken(token);
        int save = activityRemarkService.updateActivityRemark(activityRemarkQuery);
        return save >= 1 ? R.OK() : R.FAIL();

    }

    @DeleteMapping("/api/activity/remark/{id}")
    public R delActivityRemark(@PathVariable("id")Integer id){
        int del = activityRemarkService.delActivityRemarkById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }

}

