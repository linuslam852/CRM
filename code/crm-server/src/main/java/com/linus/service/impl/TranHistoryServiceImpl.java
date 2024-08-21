package com.linus.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linus.constant.Constants;
import com.linus.manager.TranManager;
import com.linus.mapper.TTranHistoryMapper;
import com.linus.model.TCustomerRemark;
import com.linus.model.TTranHistory;
import com.linus.model.TTranRemark;
import com.linus.model.TUser;
import com.linus.query.TranHistoryQuery;
import com.linus.service.TranHistoryService;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TranHistoryServiceImpl implements TranHistoryService {

    @Resource
    private TTranHistoryMapper tTranHistoryMapper;

    @Resource
    private TranManager tranManager;


    @Override
    public PageInfo<TTranHistory> getHistorykByPage(Integer current, TranHistoryQuery tranHistoryQuery) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TTranHistory> list = tTranHistoryMapper.getTranHistoryByPage(tranHistoryQuery);
        PageInfo<TTranHistory> info = new PageInfo<>(list);
        return info;
    }


    @Override
    public Boolean saveTranHistory(TranHistoryQuery tranHistoryQuery) {
       return tranManager.saveTranHistory(tranHistoryQuery);
    }
}
