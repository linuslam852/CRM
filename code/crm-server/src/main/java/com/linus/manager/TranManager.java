package com.linus.manager;


import com.linus.mapper.TTranHistoryMapper;
import com.linus.mapper.TTranMapper;
import com.linus.model.TTran;
import com.linus.model.TTranHistory;
import com.linus.model.TUser;
import com.linus.query.TranHistoryQuery;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class TranManager {

   @Resource
    private TTranHistoryMapper tTranHistoryMapper;

   @Resource
    private TTranMapper tTranMapper;

    @Transactional(rollbackFor = Exception.class)
    public Boolean saveTranHistory(TranHistoryQuery tranHistoryQuery) {
        TTranHistory tTranHistory = new TTranHistory();

        //名字和類型相同才可以賦值
        BeanUtils.copyProperties(tranHistoryQuery,tTranHistory);

        tTranHistory.setCreateTime(new Date());
        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(tranHistoryQuery.getToken()), TUser.class).getId();
        tTranHistory.setCreateBy(loginUserId);
        int insert = tTranHistoryMapper.insertSelective(tTranHistory);

        TTran tTran = new TTran();
        tTran.setId(tranHistoryQuery.getTranId());
        tTran.setEditTime(new Date());
        tTran.setEditBy(loginUserId);
        tTran.setStage(tranHistoryQuery.getStage());
        int update = tTranMapper.updateByPrimaryKeySelective(tTran);

        return  insert >= 1 && update >=1;
    }

}
