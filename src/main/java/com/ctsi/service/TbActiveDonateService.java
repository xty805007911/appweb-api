package com.ctsi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctsi.entity.TbActive;
import com.ctsi.entity.TbActiveDonate;
import com.ctsi.mapper.TbActiveDonateMapper;
import com.ctsi.util.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName : TbActiveDonateService
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-18 18:20
 */
@Service
public class TbActiveDonateService {

    @Autowired
    TbActiveDonateMapper activeDonateMapper;
    @Autowired
    TbActiveService activeService;

    //添加
    public void addActiveDonate(TbActiveDonate activeDonate) {
        activeDonate.setCreateTime(new Date());
        activeDonateMapper.insert(activeDonate);
    }
    //分页查询
    public PageResult<TbActiveDonate> activeDonatePageList(Integer page,Integer size,Integer donateUserId) {
        if(page == null || page <= 0) {
            page = 1;
        }

        //开始分页
        PageHelper.startPage(page,size);

        QueryWrapper<TbActiveDonate> donateQueryWrapper = new QueryWrapper<>();
        donateQueryWrapper.eq("donate_id",donateUserId);
        donateQueryWrapper.orderByDesc("id");

        List<TbActiveDonate> tbActiveDonateList = activeDonateMapper.selectList(donateQueryWrapper);
        for(TbActiveDonate donate : tbActiveDonateList) {
            TbActive activeById = activeService.getActiveById(donate.getActiveId());
            donate.setActive(activeById);
        }

        PageInfo<TbActiveDonate> pageInfo = new PageInfo<>(tbActiveDonateList);
        PageResult<TbActiveDonate> pageResult = new PageResult<>(pageInfo);

        return pageResult;

    }


}
