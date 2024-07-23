package com.linus.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linus.constant.Constants;
import com.linus.mapper.TActivityMapper;
import com.linus.model.TActivity;
import com.linus.model.TUser;
import com.linus.query.ActivityQuery;
import com.linus.query.BaseQuery;
import com.linus.service.ActivityService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private TActivityMapper tActivityMapper;

    @Override
    public PageInfo<TActivity> getActivityByPage(Integer current, ActivityQuery activityQuery) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TActivity> list = tActivityMapper.getActivityByPage(activityQuery);
        PageInfo<TActivity> info = new PageInfo<>(list);
        return info;
    }
}
