package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TCustomerRemark;
import com.linus.query.CustomerRemarkQuery;

public interface CustomerRemarkService {
    int saveCustomerRemark(CustomerRemarkQuery customerRemarkQuery);

    PageInfo<TCustomerRemark> getCustomerRemarkByPage(Integer current, CustomerRemarkQuery customerRemarkQuery);

    TCustomerRemark getCustomerRemarkById(Integer id);

    int updateCustomerRemark(CustomerRemarkQuery customerRemarkQuery);

    int delCustomerRemarkById(Integer id);
}
