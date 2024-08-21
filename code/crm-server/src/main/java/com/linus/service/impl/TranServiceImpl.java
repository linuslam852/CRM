package com.linus.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linus.constant.Constants;
import com.linus.manager.RedisManager;
import com.linus.mapper.TTranMapper;
import com.linus.model.TTran;
import com.linus.model.TUser;
import com.linus.query.BaseQuery;
import com.linus.query.TranQuery;
import com.linus.service.TranService;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TranServiceImpl implements TranService {

    @Resource
    private TTranMapper tTranMapper;

    @Resource
    private RedisManager redisManager;


    @Override
    public PageInfo<TTran> getTranByPage(Integer current) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TTran> list = tTranMapper.selectTranByPage(BaseQuery.builder().build());
        PageInfo<TTran> info = new PageInfo<>(list);
        return info;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int createTran(TranQuery tranQuery) {
        //向交易表插入一條數據
        TTran tTran = new TTran();
        BeanUtils.copyProperties(tranQuery,tTran);

        String tranNo = redisManager.getOnlyNumber(Constants.REDIS_ONLY_NUMBER_KEY); //獲取唯一性的交易流水號
        tTran.setTranNo(tranNo);

        tTran.setCreateTime(new Date());

        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(tranQuery.getToken()), TUser.class).getId();
        tTran.setCreateBy(loginUserId);
        return tTranMapper.insertSelective(tTran);
    }

    @Override
    public TTran getTranById(Integer id) {
        return tTranMapper.selectDetailById(id);
    }
}
