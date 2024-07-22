package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TActivity;

public interface ActivityService {
    PageInfo<TActivity> getActivityByPage(Integer current);
}
