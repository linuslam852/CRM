package com.linus.mapper;

import com.linus.model.TDicType;

import java.util.List;

public interface TDicTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TDicType record);

    int insertSelective(TDicType record);

    TDicType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TDicType record);

    int updateByPrimaryKey(TDicType record);

    List<TDicType> selectAll();
}