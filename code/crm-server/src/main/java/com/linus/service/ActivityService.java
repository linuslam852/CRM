package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TActivity;
import com.linus.query.ActivityQuery;

public interface ActivityService {
    PageInfo<TActivity> getActivityByPage(Integer current, ActivityQuery activityQuery);
}
