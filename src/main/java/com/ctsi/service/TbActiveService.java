package com.ctsi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.config.Constant;
import com.ctsi.entity.TbActive;
import com.ctsi.entity.TbFileUrl;
import com.ctsi.entity.TbUser;
import com.ctsi.mapper.TbActiveMapper;
import com.ctsi.util.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class TbActiveService {

    @Autowired
    TbActiveMapper activeMapper;
    @Autowired
    TbFileUrlService tbFileUrlService;
    @Autowired
    TbUserService userService;

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

        wrapper.orderByDesc("create_time");
        wrapper.eq("enabled",1);

        List<TbActive> list = activeMapper.selectList(wrapper);

        //封装图片信息
        for (TbActive tbActive: list) {
            List<TbFileUrl> fileUrlByTbnameAndTbId = tbFileUrlService.getFileUrlByTbnameAndTbId(Constant.FILE_TB_NAME_ACTIVE, tbActive.getId());
            if(!CollectionUtils.isEmpty(fileUrlByTbnameAndTbId)) {
                TbFileUrl fileUrl = fileUrlByTbnameAndTbId.get(0);
                String image = fileUrl.getUrlFont() + fileUrl.getUrlEnd();
                tbActive.setImage(image);
            }
        }

        PageInfo<TbActive> pageInfo = new PageInfo<>(list);
        PageResult<TbActive> pageResult = new PageResult<>(pageInfo);

        return pageResult;
    }

    //根据id查询
    public TbActive getActiveById(Integer id) {
        TbActive tbActive = activeMapper.selectById(id);

        //查询所有文件
        List<TbFileUrl> fileUrlList = tbFileUrlService.getFileUrlByTbnameAndTbId(Constant.FILE_TB_NAME_ACTIVE, id);
        tbActive.setFileList(fileUrlList);

        //查询创建人
        TbUser createUser = userService.getUserById(tbActive.getCreateUserId());
        tbActive.setCreateUser(createUser);

        return tbActive;
    }

    //修改
    public void editActive(TbActive active) {
        activeMapper.updateById(active);
    }

    //设置enabled
    public void setActiveEnabled(Integer id,Integer enabled) {
        TbActive tbActive = activeMapper.selectById(id);
        tbActive.setEnabled(enabled);
        activeMapper.updateById(tbActive);
    }


    //首页设置：根据tag查询
    public  List<TbActive> getActiveListByTag(Integer size,String tag) {
        PageHelper.startPage(1,size);
        //包装对象
        QueryWrapper wrapper = new QueryWrapper();

        wrapper.orderByDesc("create_time");
        wrapper.eq("enabled",1);
        if(tag != null) {
            wrapper.like("tag",tag);
        }
        List<TbActive> list = activeMapper.selectList(wrapper);
        //封装图片信息
        for (TbActive active: list) {
            List<TbFileUrl> fileUrl = tbFileUrlService.getFileUrlByTbnameAndTbId(Constant.FILE_TB_NAME_ACTIVE, active.getId());
            if(!CollectionUtils.isEmpty(fileUrl)) {
                String image = fileUrl.get(0).getUrlFont() + fileUrl.get(0).getUrlEnd();
                active.setImage(image);
            }
        }
        PageInfo<TbActive> pageInfo = new PageInfo<>(list);
        return pageInfo.getList();
    }

    //首页设置：根据最新创建时间查询
    public  List<TbActive> getActiveListByCreateTimeDesc(Integer size) {
        PageHelper.startPage(1,size);
        //包装对象
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("create_time");
        wrapper.eq("enabled",1);
        List<TbActive> list = activeMapper.selectList(wrapper);

        PageInfo<TbActive> pageInfo = new PageInfo<>(list);
        return pageInfo.getList();
    }

    //首页设置：根据开始时间查询
    public  List<TbActive> getActiveListByStartTimeDesc(Integer size) {
        PageHelper.startPage(1,size);
        //包装对象
        QueryWrapper wrapper = new QueryWrapper();

        wrapper.orderByDesc("start_time");
        wrapper.eq("enabled",1);

        List<TbActive> list = activeMapper.selectList(wrapper);
        //封装图片信息
        for (TbActive active: list) {
            List<TbFileUrl> fileUrl = tbFileUrlService.getFileUrlByTbnameAndTbId(Constant.FILE_TB_NAME_ACTIVE, active.getId());
            if(!CollectionUtils.isEmpty(fileUrl)) {
                System.out.println();
                String image = fileUrl.get(0).getUrlFont() + fileUrl.get(0).getUrlEnd();
                active.setImage(image);
            }
        }
        PageInfo<TbActive> pageInfo = new PageInfo<>(list);
        return pageInfo.getList();
    }

}
