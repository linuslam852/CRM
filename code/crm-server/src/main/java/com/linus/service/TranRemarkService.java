package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TTranRemark;
import com.linus.query.TranRemarkQuery;

public interface TranRemarkService {
    PageInfo<TTranRemark> getTranRemarkByPage(Integer current, TranRemarkQuery tranRemarkQuery);

    int saveTranRemark(TranRemarkQuery tranRemarkQuery);

    TTranRemark getTranRemarkById(Integer id);

    int updateTranRemark(TranRemarkQuery tranRemarkQuery);

    int delTranRemarkById(Integer id);
}
