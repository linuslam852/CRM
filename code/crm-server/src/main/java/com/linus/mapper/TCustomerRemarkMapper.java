package com.linus.mapper;

import com.linus.model.TCustomerRemark;
import com.linus.query.CustomerRemarkQuery;

import java.util.List;

public interface TCustomerRemarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TCustomerRemark record);

    int insertSelective(TCustomerRemark record);

    TCustomerRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TCustomerRemark record);

    int updateByPrimaryKey(TCustomerRemark record);

    List<TCustomerRemark> getCustomerRemarkByPage(CustomerRemarkQuery customerRemarkQuery);
}