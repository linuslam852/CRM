package com.linus.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linus.constant.Constants;
import com.linus.mapper.TActivityMapper;
import com.linus.model.TActivity;
import com.linus.model.TUser;
import com.linus.query.ActivityQuery;
import com.linus.service.ActivityService;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
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

    @Override
    public int saveActivity(ActivityQuery activityQuery) {
        TActivity tActivity = new TActivity();
        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(activityQuery,tActivity);

        tActivity.setCreateTime(new Date());

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(activityQuery.getToken()), TUser.class).getId();
        tActivity.setCreateBy(loginUserId);
        return tActivityMapper.insertSelective(tActivity);
    }

    @Override
    public TActivity getActivityById(Integer id) {
        return tActivityMapper.selectDetailByPrimaryKey(id);
    }

    @Override
    public int updateActivity(ActivityQuery activityQuery) {
        TActivity tActivity = new TActivity();
        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(activityQuery,tActivity);


        tActivity.setEditTime(new Date());//編輯時間

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(activityQuery.getToken()), TUser.class).getId();
        tActivity.setEditBy(loginUserId);//編輯人
        return tActivityMapper.updateByPrimaryKeySelective(tActivity);
    }
}
