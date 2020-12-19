package com.ctsi.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.config.Constant;
import com.ctsi.entity.*;
import com.ctsi.mapper.TbActiveMapper;
import com.ctsi.mapper.TbActiveUserMapper;
import com.ctsi.mapper.TbActiveUserRecordMapper;
import com.ctsi.util.DateUtils;
import com.ctsi.util.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/12  17:44
 */
@Controller
public class TbActiveUserService {

    @Autowired
    TbActiveUserMapper activeUserMapper;
    @Autowired
    TbActiveMapper activeMapper;
    @Autowired
    TbActiveUserRecordMapper activeUserRecordMapper;
    @Autowired
    TbFileUrlService fileUrlService;


    //用户是否在活动中
    public boolean isUserInActive(Integer userId,Integer activeId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("active_id",activeId);
        queryWrapper.eq("enabled",1);
        TbActiveUser activeUser = activeUserMapper.selectOne(queryWrapper);
        if(activeUser == null || activeUser.getId() == null) {
            return false;
        }
        return true;
    }

    //根据userid和activeid查询
    public TbActiveUser selectActiveUserByUserIdAndActiveId(Integer userId,Integer activeId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("active_id",activeId);
        return activeUserMapper.selectOne(queryWrapper);
    }


    //保存用户-活动信息
    public TbActiveUser save(Integer userId,Integer activeId) {
        //先查询该用户是否在活动中，如果在，则直接返回
        QueryWrapper<TbActiveUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("active_id",activeId);
        queryWrapper.eq("enabled",1);
        List<TbActiveUser> tbActiveUserList = activeUserMapper.selectList(queryWrapper);
        if(!CollectionUtils.isEmpty(tbActiveUserList)) {
            return tbActiveUserList.get(0);
        }

        TbActiveUser activeUser = new TbActiveUser();
        activeUser.setUserId(userId);
        activeUser.setActiveId(activeId);
        activeUser.setEnabled(1);
        activeUser.setCreateTime(new Date());
        activeUserMapper.insert(activeUser);
        return activeUser;
    }

    //根据用户查询所有的活动
    //如果activeId == null 则查询所有活动
    public List<TbActive> selectActiveListByUser(Integer userId,Integer activeId) {
        QueryWrapper<TbActiveUser> activeUserQueryWrapper = new QueryWrapper<>();
        activeUserQueryWrapper.eq("user_id",userId);
        activeUserQueryWrapper.eq("enabled",1);
        if(activeId != null) {
            activeUserQueryWrapper.eq("active_id",activeId);
        }
        List<TbActiveUser> activeUserList = activeUserMapper.selectList(activeUserQueryWrapper);

        List<Integer> activeIdList = new ArrayList<>();
        List<Integer> activeUserIdList = new ArrayList<>();
        for(TbActiveUser activeUser : activeUserList) {
            activeIdList.add(activeUser.getActiveId());
            activeUserIdList.add(activeUser.getId());
        }

        //查询所有的活动
        List<TbActive> activeList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(activeIdList)) {
            activeList = activeMapper.selectBatchIds(activeIdList);
        }
        for (TbActive active: activeList) {
            //封装图片信息
            List<TbFileUrl> fileUrl = fileUrlService.getFileUrlByTbnameAndTbId(Constant.FILE_TB_NAME_ACTIVE, active.getId());
            if(!CollectionUtils.isEmpty(fileUrl)) {
                String image = fileUrl.get(0).getUrlFont() + fileUrl.get(0).getUrlEnd();
                active.setImage(image);
            }

            //根据所有的active-user查询活动记录
            QueryWrapper<TbActiveUserRecord> recordQueryWrapper = new QueryWrapper<>();

            recordQueryWrapper.eq("active_id",active.getId());
            recordQueryWrapper.eq("user_id",userId);
            List<TbActiveUserRecord> activeUserRecordList = activeUserRecordMapper.selectList(recordQueryWrapper);
            active.setActiveUserRecordList(activeUserRecordList);
        }

        return activeList;
    }


    public TbActiveUserRecord saveActiveUserRecord(Integer activeUserId,String content,Integer ratings) {
        //先查询active-user，在插入
        TbActiveUser activeUser = activeUserMapper.selectById(activeUserId);

        TbActiveUserRecord record = new TbActiveUserRecord();

        record.setActiveId(activeUser.getActiveId());
        record.setActiveUserId(activeUserId);
        record.setUserId(activeUser.getUserId());

        record.setContent(content);
        record.setRatings(ratings);

        record.setCreateTime(new Date());

        activeUserRecordMapper.insert(record);

        return record;

    }

    //根据活动id查询所有用户
    public PageResult<TbUser> selectAllUserByActiveId(Integer page,Integer size,Integer activeId) {
        if(page == null || page <=0) {
            page = 1;
        }

        PageHelper.startPage(page,size);
        List<TbUser> userList = activeUserMapper.selectAllUserByActiveId(activeId);

        PageInfo<TbUser> pageInfo = new PageInfo<>(userList);
        PageResult<TbUser> pageResult = new PageResult<>(pageInfo);
        return pageResult;

    }


}
