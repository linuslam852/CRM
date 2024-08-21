package com.linus.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linus.constant.Constants;
import com.linus.mapper.TTranRemarkMapper;
import com.linus.model.TCustomerRemark;
import com.linus.model.TTranRemark;
import com.linus.model.TUser;
import com.linus.query.TranRemarkQuery;
import com.linus.result.R;
import com.linus.service.TranRemarkService;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

@Service
public class TranRemarkServiceImpl implements TranRemarkService {

    @Resource
    TTranRemarkMapper tTranRemarkMapper;

    @Override
    public PageInfo<TTranRemark> getTranRemarkByPage(Integer current, TranRemarkQuery tranRemarkQuery) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TTranRemark> list = tTranRemarkMapper.getTranRemarkByPage(tranRemarkQuery);
        PageInfo<TTranRemark> info = new PageInfo<>(list);
        return info;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveTranRemark(TranRemarkQuery tranRemarkQuery) {
        TTranRemark tTranRemark = new TTranRemark();
        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(tranRemarkQuery,tTranRemark);

        tTranRemark.setCreateTime(new Date());

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(tranRemarkQuery.getToken()), TUser.class).getId();
        tTranRemark.setCreateBy(loginUserId);
        return tTranRemarkMapper.insertSelective(tTranRemark);
    }

    @Override
    public TTranRemark getTranRemarkById(Integer id) {
        return tTranRemarkMapper.selectByPrimaryKey(id);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateTranRemark(TranRemarkQuery tranRemarkQuery) {
        TTranRemark tTranRemark = new TTranRemark();
        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(tranRemarkQuery,tTranRemark);

        tTranRemark.setEditTime(new Date());

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(tranRemarkQuery.getToken()), TUser.class).getId();
        tTranRemark.setEditBy(loginUserId);
        return tTranRemarkMapper.updateByPrimaryKeySelective(tTranRemark);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delTranRemarkById(Integer id) {
        TTranRemark tTranRemark = new TTranRemark();
        tTranRemark.setId(id);
        tTranRemark.setDeleted(1); //邏輯刪除
        return tTranRemarkMapper.updateByPrimaryKeySelective(tTranRemark);
    }


}
