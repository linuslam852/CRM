package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TClue;
import com.linus.query.ClueQuery;

import java.io.InputStream;

public interface ClueService {
    PageInfo<TClue> getClueByPage(Integer current);

    void importExcel(InputStream inputStream,String token);

    Boolean checkPhone(String phone);

    int saveClue(ClueQuery clueQuery);

    TClue getClueById(Integer id);

    int updateClue(ClueQuery clueQuery);
}
