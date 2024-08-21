package com.linus.mapper;

import com.linus.model.TTranHistory;
import com.linus.query.TranHistoryQuery;

import java.util.List;

public interface TTranHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TTranHistory record);

    int insertSelective(TTranHistory record);

    TTranHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTranHistory record);

    int updateByPrimaryKey(TTranHistory record);

    List<TTranHistory> getTranHistoryByPage(TranHistoryQuery tranHistoryQuery);
}