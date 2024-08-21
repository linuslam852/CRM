package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TTran;
import com.linus.model.TUser;
import com.linus.query.TranQuery;

public interface TranService {
    PageInfo<TTran> getTranByPage(Integer current);

    int createTran(TranQuery tranQuery);

    TTran getTranById(Integer id);
}
