package com.linus.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linus.constant.Constants;
import com.linus.mapper.TClueRemarkMapper;
import com.linus.model.TActivityRemark;
import com.linus.model.TClueRemark;
import com.linus.model.TUser;
import com.linus.query.ActivityRemarkQuery;
import com.linus.query.ClueRemarkQuery;
import com.linus.service.ClueRemarkService;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {
    @Resource
    private TClueRemarkMapper tClueRemarkMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveClueRemark(ClueRemarkQuery clueRemarkQuery) {
        TClueRemark tClueRemark = new TClueRemark();
        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(clueRemarkQuery,tClueRemark);

        tClueRemark.setCreateTime(new Date());

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(clueRemarkQuery.getToken()), TUser.class).getId();
        tClueRemark.setCreateBy(loginUserId);
        return tClueRemarkMapper.insertSelective(tClueRemark);
    }

    @Override
    public PageInfo<TClueRemark> getClueRemarkByPage(Integer current, ClueRemarkQuery clueRemarkQuery) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TClueRemark> list = tClueRemarkMapper.getClueRemarkByPage(clueRemarkQuery);
        PageInfo<TClueRemark> info = new PageInfo<>(list);
        return info;
    }
}
