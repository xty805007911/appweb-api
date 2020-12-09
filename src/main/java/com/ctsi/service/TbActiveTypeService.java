package com.ctsi.service;

import com.ctsi.entity.TbActive;
import com.ctsi.entity.TbActiveType;
import com.ctsi.mapper.TbActiveTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/9  22:37
 */
@Service
public class TbActiveTypeService {
    @Autowired
    TbActiveTypeMapper activeTypeMapper;

    //查询所有活动分类
    public List<TbActiveType> getActiveTypeList() {
        return activeTypeMapper.selectList(null);
    }
}
