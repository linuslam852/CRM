package com.linus.service.impl;

import com.linus.mapper.TDicTypeMapper;
import com.linus.model.TDicType;
import com.linus.model.TProduct;
import com.linus.service.DicTypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicTypeServiceImpl implements DicTypeService {

    @Resource
    private TDicTypeMapper tDicTypeMapper;

    @Override
    public List<TDicType> loadAllDicData() {
        return tDicTypeMapper.selectAll();
    }

}
