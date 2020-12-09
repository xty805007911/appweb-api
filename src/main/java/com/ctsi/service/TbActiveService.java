package com.ctsi.service;

import com.ctsi.entity.TbActive;
import com.ctsi.mapper.TbActiveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbActiveService {

    @Autowired
    TbActiveMapper activeMapper;

    //添加活动
    public void add(TbActive active) {
        activeMapper.insert(active);
    }

}
