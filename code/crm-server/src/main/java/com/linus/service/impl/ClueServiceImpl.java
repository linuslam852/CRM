package com.linus.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linus.config.listener.UploadDataListener;
import com.linus.constant.Constants;
import com.linus.mapper.TClueMapper;
import com.linus.model.TActivity;
import com.linus.model.TClue;
import com.linus.model.TUser;
import com.linus.query.BaseQuery;
import com.linus.query.ClueQuery;
import com.linus.service.ClueService;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {
    @Resource
    private TClueMapper tClueMapper;

    @Override
    public PageInfo<TClue> getClueByPage(Integer current) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TClue> list = tClueMapper.selectClueByPage(BaseQuery.builder().build());
        PageInfo<TClue> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public void importExcel(InputStream inputStream,String token) {
        EasyExcel.read(inputStream, TClue.class, new UploadDataListener(tClueMapper, token))
                .sheet()
                .doRead();
    }

    @Override
    public Boolean checkPhone(String phone) {
        int count = tClueMapper.selectByCount(phone);
        return count <= 0;
    }

    @Override
    public int saveClue(ClueQuery clueQuery) {
        TClue tClue = new TClue();
        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(clueQuery,tClue);

        tClue.setCreateTime(new Date());

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(clueQuery.getToken()), TUser.class).getId();
        tClue.setCreateBy(loginUserId);
        return tClueMapper.insertSelective(tClue);
    }

    @Override
    public TClue getClueById(Integer id) {
        return tClueMapper.selectDetailById(id);
    }

    @Override
    public int updateClue(ClueQuery clueQuery) {
        TClue tClue = new TClue();
        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(clueQuery,tClue);

        tClue.setEditTime(new Date());

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(clueQuery.getToken()), TUser.class).getId();
        tClue.setEditBy(loginUserId);//編輯人
        return tClueMapper.updateByPrimaryKeySelective(tClue);
    }
}
