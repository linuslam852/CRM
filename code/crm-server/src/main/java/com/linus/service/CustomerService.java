package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TCustomer;
import com.linus.query.CustomerQuery;
import com.linus.result.CustomerExcel;

import java.util.List;

public interface CustomerService {
    Boolean converCutomer(CustomerQuery customerQuery);

    PageInfo<TCustomer> getCustomerByPage(Integer current);

    List<CustomerExcel> getCustomerByExcel(List<String> idList);

    TCustomer getCustomerById(Integer id);
}
