package com.linus.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linus.constant.Constants;
import com.linus.mapper.TCustomerRemarkMapper;
import com.linus.model.TClueRemark;
import com.linus.model.TCustomerRemark;
import com.linus.model.TUser;
import com.linus.query.CustomerRemarkQuery;
import com.linus.service.CustomerRemarkService;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerRemarkServiceImpl implements CustomerRemarkService {
    @Resource
    private TCustomerRemarkMapper tCustomerRemarkMapper;

    @Override
    public int saveCustomerRemark(CustomerRemarkQuery customerRemarkQuery) {
        TCustomerRemark tCustomerRemark = new TCustomerRemark();
        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(customerRemarkQuery,tCustomerRemark);

        tCustomerRemark.setCreateTime(new Date());

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(customerRemarkQuery.getToken()), TUser.class).getId();
        tCustomerRemark.setCreateBy(loginUserId);
        return tCustomerRemarkMapper.insertSelective(tCustomerRemark);
    }

    @Override
    public PageInfo<TCustomerRemark> getCustomerRemarkByPage(Integer current, CustomerRemarkQuery customerRemarkQuery) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TCustomerRemark> list = tCustomerRemarkMapper.getCustomerRemarkByPage(customerRemarkQuery);
        PageInfo<TCustomerRemark> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public TCustomerRemark getCustomerRemarkById(Integer id) {
        return tCustomerRemarkMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateCustomerRemark(CustomerRemarkQuery customerRemarkQuery) {
        TCustomerRemark tCustomerRemark = new TCustomerRemark();
        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(customerRemarkQuery,tCustomerRemark);

        tCustomerRemark.setEditTime(new Date());

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(customerRemarkQuery.getToken()), TUser.class).getId();
        tCustomerRemark.setEditBy(loginUserId);
        return tCustomerRemarkMapper.updateByPrimaryKeySelective(tCustomerRemark);
    }

    @Override
    public int delCustomerRemarkById(Integer id) {
        TCustomerRemark tCustomerRemark = new TCustomerRemark();
        tCustomerRemark.setId(id);
        tCustomerRemark.setDeleted(1); //邏輯刪除
        return tCustomerRemarkMapper.updateByPrimaryKeySelective(tCustomerRemark);
    }
}
