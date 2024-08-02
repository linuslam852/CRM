package com.linus.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linus.constant.Constants;
import com.linus.manager.CustomerManager;
import com.linus.mapper.TCustomerMapper;
import com.linus.model.TClue;
import com.linus.model.TCustomer;
import com.linus.query.BaseQuery;
import com.linus.query.CustomerQuery;
import com.linus.result.CustomerExcel;
import com.linus.service.CustomerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerManager customerManager;

    @Resource
    private TCustomerMapper tCustomerMapper;

    @Override
    public Boolean converCutomer(CustomerQuery customerQuery) {

        return customerManager.convertCustomer(customerQuery);
    }

    @Override
    public PageInfo<TCustomer> getCustomerByPage(Integer current) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TCustomer> list = tCustomerMapper.selectCustomerByPage(BaseQuery.builder().build());
        PageInfo<TCustomer> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public List<CustomerExcel> getCustomerByExcel(List<String> idList) {
        List<CustomerExcel> customerExcelList = new ArrayList<>();
        List<TCustomer> tCustomerList =  tCustomerMapper.selectCustomerByExcel(idList);
        tCustomerList.forEach(tCustomer -> {
            CustomerExcel customerExcel = new CustomerExcel();
            customerExcel.setOwnerName(ObjectUtils.isEmpty(tCustomer.getOwnerDO()) ? Constants.EMPTY : tCustomer.getOwnerDO().getName());
            customerExcel.setActivityName(ObjectUtils.isEmpty(tCustomer.getActivityDO()) ? Constants.EMPTY : tCustomer.getActivityDO().getName());
            customerExcel.setFullName(tCustomer.getClueDO().getFullName());
            customerExcel.setAppellationName(ObjectUtils.isEmpty(tCustomer.getAppellationDO()) ? Constants.EMPTY : tCustomer.getAppellationDO().getTypeValue());
            customerExcel.setPhone(tCustomer.getClueDO().getPhone());
            customerExcel.setEmail(tCustomer.getClueDO().getEmail());
            customerExcel.setAge(tCustomer.getClueDO().getAge());
            customerExcel.setJob(tCustomer.getClueDO().getJob());
            customerExcel.setYearIncome(tCustomer.getClueDO().getYearIncome());
            customerExcel.setAddress(tCustomer.getClueDO().getAddress());
            customerExcel.setNeedLoadName(ObjectUtils.isEmpty(tCustomer.getNeedLoanDO()) ? Constants.EMPTY : tCustomer.getNeedLoanDO().getTypeValue());
            customerExcel.setProductName(ObjectUtils.isEmpty(tCustomer.getIntentionProductDO()) ? Constants.EMPTY : tCustomer.getIntentionProductDO().getName());
            customerExcel.setSourceName(ObjectUtils.isEmpty(tCustomer.getSourceDO()) ? Constants.EMPTY : tCustomer.getSourceDO().getTypeValue());
            customerExcel.setDescription(tCustomer.getDescription());
            customerExcel.setNextContactTime(tCustomer.getNextContactTime());

            customerExcelList.add(customerExcel);

        });
        return customerExcelList;
    }
}
