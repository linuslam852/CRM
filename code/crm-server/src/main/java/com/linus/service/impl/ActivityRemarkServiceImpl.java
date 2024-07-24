package com.linus.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linus.constant.Constants;
import com.linus.mapper.TActivityRemarkMapper;
import com.linus.model.TActivity;
import com.linus.model.TActivityRemark;
import com.linus.model.TUser;
import com.linus.query.ActivityRemarkQuery;
import com.linus.service.ActivityRemarkService;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Resource
    private TActivityRemarkMapper tActivityRemarkMapper;

    @Override
    public int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery) {
        TActivityRemark tActivityRemark = new TActivityRemark();
        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(activityRemarkQuery,tActivityRemark);

        tActivityRemark.setCreateTime(new Date());

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(activityRemarkQuery.getToken()), TUser.class).getId();
        tActivityRemark.setCreateBy(loginUserId);
        return tActivityRemarkMapper.insertSelective(tActivityRemark);
    }

    @Override
    public PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current, ActivityRemarkQuery activityRemarkQuery) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TActivityRemark> list = tActivityRemarkMapper.getActivityRemarkByPage(activityRemarkQuery);
        PageInfo<TActivityRemark> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public TActivityRemark getActivityRemarkById(Integer id) {
        return tActivityRemarkMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateActivityRemark(ActivityRemarkQuery activityRemarkQuery) {
        TActivityRemark tActivityRemark = new TActivityRemark();
        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(activityRemarkQuery,tActivityRemark);

        tActivityRemark.setEditTime(new Date());

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(activityRemarkQuery.getToken()), TUser.class).getId();
        tActivityRemark.setEditBy(loginUserId);
        return tActivityRemarkMapper.updateByPrimaryKeySelective(tActivityRemark);
    }

    @Override
    public int delActivityRemarkById(Integer id) {
        TActivityRemark tActivityRemark = new TActivityRemark();
        tActivityRemark.setId(id);
        tActivityRemark.setDeleted(1); //邏輯刪除
        return tActivityRemarkMapper.updateByPrimaryKeySelective(tActivityRemark);

    }
}
