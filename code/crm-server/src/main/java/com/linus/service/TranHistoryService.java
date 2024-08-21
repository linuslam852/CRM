package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TTranHistory;
import com.linus.query.TranHistoryQuery;

import java.util.List;

public interface TranHistoryService {

    PageInfo<TTranHistory> getHistorykByPage(Integer current, TranHistoryQuery tranHistoryQuery);

    Boolean saveTranHistory(TranHistoryQuery tranHistoryQuery);
}
