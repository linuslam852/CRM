package com.linus.mapper;

import com.linus.commons.DataScope;
import com.linus.model.TClueRemark;
import com.linus.query.ClueRemarkQuery;

import java.util.List;

public interface TClueRemarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TClueRemark record);

    int insertSelective(TClueRemark record);

    TClueRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TClueRemark record);

    int updateByPrimaryKey(TClueRemark record);
    @DataScope(tableAlias = "tcr", tableField = "create_by")
    List<TClueRemark> getClueRemarkByPage(ClueRemarkQuery clueRemarkQuery);
}