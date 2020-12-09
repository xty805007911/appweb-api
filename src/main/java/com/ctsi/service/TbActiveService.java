package com.ctsi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctsi.entity.TbActive;
import com.ctsi.mapper.TbActiveMapper;
import com.ctsi.util.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TbActiveService {

    @Autowired
    TbActiveMapper activeMapper;

    //添加活动
    public void add(TbActive active) {
        //填充其他属性
        active.setCreateTime(new Date());
        active.setEnabled(1);
        active.setStatus(1);

        activeMapper.insert(active);
    }

    //分页查询
    public  PageResult<TbActive> pageList(TbActive active,Integer page,Integer size) {

        PageHelper.startPage(page,size);

        //包装对象
        QueryWrapper wrapper = new QueryWrapper();
        if(active.getName() != null) {
            wrapper.like("name",active.getName());
        }
        if(active.getAddress()!=null) {
            wrapper.like("address",active.getAddress());
        }

        List<TbActive> list = activeMapper.selectList(wrapper);

        PageInfo<TbActive> pageInfo = new PageInfo<>(list);
        PageResult<TbActive> pageResult = new PageResult<>(pageInfo);

        return pageResult;
    }

}
