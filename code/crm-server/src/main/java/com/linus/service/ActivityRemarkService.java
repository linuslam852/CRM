package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TActivityRemark;
import com.linus.query.ActivityRemarkQuery;

public interface ActivityRemarkService {
     int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery);

     PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current,ActivityRemarkQuery activityRemarkQuery);

     TActivityRemark getActivityRemarkById(Integer id);

     int updateActivityRemark(ActivityRemarkQuery activityRemarkQuery);

     int delActivityRemarkById(Integer id);
}
