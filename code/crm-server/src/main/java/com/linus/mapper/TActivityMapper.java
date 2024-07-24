package com.linus.mapper;

import com.linus.commons.DataScope;
import com.linus.model.TActivity;
import com.linus.query.ActivityQuery;
import com.linus.query.BaseQuery;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

public interface TActivityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TActivity record);

    int insertSelective(TActivity record);

    TActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TActivity record);

    int updateByPrimaryKey(TActivity record);

    @DataScope(tableAlias = "ta", tableField = "owner_id")
    List<TActivity> getActivityByPage(ActivityQuery activityQuery);


    TActivity selectDetailByPrimaryKey(Integer id);
}