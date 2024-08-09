package com.linus.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linus.constant.Constants;
import com.linus.mapper.TTranMapper;
import com.linus.model.TClue;
import com.linus.model.TTran;
import com.linus.model.TUser;
import com.linus.query.BaseQuery;
import com.linus.service.TranService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranServiceImpl implements TranService {

    @Resource
    private TTranMapper tTranMapper;

    @Override
    public PageInfo<TTran> getTranByPage(Integer current) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TTran> list = tTranMapper.selectTranByPage(BaseQuery.builder().build());
        PageInfo<TTran> info = new PageInfo<>(list);
        return info;
    }
}
