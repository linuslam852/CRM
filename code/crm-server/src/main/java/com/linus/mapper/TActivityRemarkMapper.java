package com.linus.mapper;

import com.linus.commons.DataScope;
import com.linus.model.TActivityRemark;
import com.linus.query.ActivityRemarkQuery;

import java.util.List;

public interface TActivityRemarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TActivityRemark record);

    int insertSelective(TActivityRemark record);

    TActivityRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TActivityRemark record);

    int updateByPrimaryKey(TActivityRemark record);

    @DataScope(tableAlias = "tar", tableField = "create_by")
    List<TActivityRemark> getActivityRemarkByPage(ActivityRemarkQuery activityRemarkQuery);
}