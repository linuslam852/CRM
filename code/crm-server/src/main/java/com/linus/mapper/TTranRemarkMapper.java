package com.linus.mapper;

import com.linus.model.TTranRemark;
import com.linus.query.TranRemarkQuery;

import java.util.List;

public interface TTranRemarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TTranRemark record);

    int insertSelective(TTranRemark record);

    TTranRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTranRemark record);

    int updateByPrimaryKey(TTranRemark record);

    List<TTranRemark> getTranRemarkByPage(TranRemarkQuery tranRemarkQuery);
}