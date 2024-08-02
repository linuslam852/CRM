package com.linus.manager;

import com.linus.mapper.TClueMapper;
import com.linus.mapper.TClueRemarkMapper;
import com.linus.mapper.TCustomerMapper;
import com.linus.model.TClue;
import com.linus.model.TCustomer;
import com.linus.model.TUser;
import com.linus.query.CustomerQuery;
import com.linus.utils.JSONUtil;
import com.linus.utils.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Component
public class CustomerManager {
    @Resource
    private TCustomerMapper tCustomerMapper;

    @Resource
    private TClueMapper tClueMapper;

    @Transactional(rollbackFor = Exception.class)
    public Boolean convertCustomer(CustomerQuery customerQuery) {
        //驗證該線索是否已經轉過客戶
        TClue tClue = tClueMapper.selectByPrimaryKey(customerQuery.getClueId());
        if(tClue.getState() == -1){
            throw new RuntimeException("該線索已經轉過客戶，不能再轉了");
        }

        //向客戶表插入一條數據
        TCustomer tCustomer = new TCustomer();
        BeanUtils.copyProperties(customerQuery,tCustomer);
        tCustomer.setCreateTime(new Date());
        //獲取當前登入人的id
        Integer loginUserId = JSONUtil.toBean(JWTUtils.parseJWT(customerQuery.getToken()), TUser.class).getId();
        tCustomer.setCreateBy(loginUserId);
        int insert = tCustomerMapper.insertSelective(tCustomer);

        //把線索的狀態改為-1
        TClue tClue1 = new TClue();
        tClue1.setId(customerQuery.getClueId());
        tClue1.setState(-1);
        int update = tClueMapper.updateByPrimaryKeySelective(tClue1);

        return insert >= 1 && update >=1;
    }
}
