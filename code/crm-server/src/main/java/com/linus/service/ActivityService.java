package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TActivity;
import com.linus.query.ActivityQuery;

import java.util.List;

public interface ActivityService {
    PageInfo<TActivity> getActivityByPage(Integer current, ActivityQuery activityQuery);

    int saveActivity(ActivityQuery activityQuery);

    TActivity getActivityById(Integer id);

    int updateActivity(ActivityQuery activityQuery);


    List<TActivity> getOnGoingActivities();

    int delActivityById(Integer id);

    int batchDelActivityByIds(List<String> idList);
}
