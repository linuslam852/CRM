package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TClueRemark;
import com.linus.query.ClueRemarkQuery;

public interface ClueRemarkService {
    int saveClueRemark(ClueRemarkQuery clueRemarkQuery);

    PageInfo<TClueRemark> getClueRemarkByPage(Integer current, ClueRemarkQuery clueRemarkQuery);

    TClueRemark getClueRemarkById(Integer id);

    int updateClueRemark(ClueRemarkQuery clueRemarkQuery);

    int delClueRemarkById(Integer id);
}
