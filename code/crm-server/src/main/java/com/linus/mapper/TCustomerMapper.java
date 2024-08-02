package com.linus.mapper;

import com.linus.model.TCustomer;
import com.linus.query.BaseQuery;

import java.util.List;

public interface TCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TCustomer record);

    int insertSelective(TCustomer record);

    TCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TCustomer record);

    int updateByPrimaryKey(TCustomer record);

    List<TCustomer> selectCustomerByPage(BaseQuery build);

    List<TCustomer> selectCustomerByExcel(List<String> idList);

    Integer selectByCount();
}