package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TTran;
import com.linus.model.TUser;

public interface TranService {
    PageInfo<TTran> getTranByPage(Integer current);
}
