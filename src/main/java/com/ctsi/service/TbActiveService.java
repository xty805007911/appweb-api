package com.ctsi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.config.Constant;
import com.ctsi.entity.*;
import com.ctsi.mapper.TbActiveMapper;
import com.ctsi.mapper.TbActiveUserMapper;
import com.ctsi.mapper.TbActiveUserRecordMapper;
import com.ctsi.mapper.TbUserMapper;
import com.ctsi.util.DateUtils;
import com.ctsi.util.PageResult;
import com.ctsi.vo.ActivityQueryVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TbActiveService {

    @Autowired
    TbActiveMapper activeMapper;
    @Autowired
    TbFileUrlService tbFileUrlService;
    @Autowired
    TbActiveUserMapper activeUserMapper;
    @Autowired
    TbUserService userService;
    @Autowired
    TbUserMapper userMapper;
    @Autowired
    TbActiveUserRecordMapper activeUserRecordMapper;

    //添加活动
    public void add(TbActive active) {
        //填充其他属性
        active.setCreateTime(new Date());
        active.setEnabled(1);

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
        wrapper.orderByDesc("start_time");
        wrapper.eq("enabled",1);
        if(tag != null) {
            wrapper.like("tag",tag);
        }
        List<TbActive> list = activeMapper.selectList(wrapper);

        for (TbActive active: list) {
            //封装图片信息
            List<TbFileUrl> fileUrl = tbFileUrlService.getFileUrlByTbnameAndTbId(Constant.FILE_TB_NAME_ACTIVE, active.getId());
            if(!CollectionUtils.isEmpty(fileUrl)) {
                String image = fileUrl.get(0).getUrlFont() + fileUrl.get(0).getUrlEnd();
                active.setImage(image);
            }

            //封装日期信息
            Integer gapDateForStartTime = DateUtils.dateGapBetween(new Date(), active.getStartTime());//开始时间距离今天多久
            Integer gapDateForEndTime = DateUtils.dateGapBetween(active.getEndTime(), new Date());//今天距离结束时间多久

            //pending，活动马上开始
            if(gapDateForStartTime <= -1) {
                active.setStatus(Constant.ACTIVITY_STATUS_PENDING);
            }
            // In Progress，活动在进行中
            if(gapDateForStartTime>=0 && gapDateForEndTime >= 0) {
                active.setStatus(Constant.ACTIVITY_STATUS_IN_PROGRESS);
            }

            //已结束
            if(gapDateForEndTime <= 0) {
                active.setStatus(Constant.ACTIVITY_STATUS_ON_HOLD);
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

    //根据关键字查询
    public PageResult<TbActive> pageListByKeywords(ActivityQueryVO activityQueryVO) {

        int page = activityQueryVO.getPage() == null || activityQueryVO.getPage() <= 0 ? 1: activityQueryVO.getPage();
        PageHelper.startPage(page,activityQueryVO.getSize());

        if(activityQueryVO.getActiveTypeId() != null) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("type",activityQueryVO.getActiveTypeId());
            queryWrapper.orderByDesc("create_time");
            PageInfo<TbActive> pageInfo = new PageInfo<>(activeMapper.selectList(queryWrapper));
            PageResult<TbActive> pageResult = new PageResult<>(pageInfo);

            return pageResult;
        }

        List<TbActive> list = activeMapper.searchByQueryVO(activityQueryVO);

        PageInfo<TbActive> pageInfo = new PageInfo<>(list);
        PageResult<TbActive> pageResult = new PageResult<>(pageInfo);

        return pageResult;
    }

    //根据活动id分页查询用户
    public PageResult<TbUser> selectPageUserListByActiveId(Integer activeId,Integer page,Integer pageSize) {
        if(page==null||page<=0) {
            page = 1;
        }
        PageHelper.startPage(page,pageSize);
        QueryWrapper<TbActiveUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("active_id",activeId);
        List<TbActiveUser> activeUserList = activeUserMapper.selectList(queryWrapper);

        List<Integer> activeUserIdList = new ArrayList<>();
        for(TbActiveUser activeUser : activeUserList) {
            activeUserIdList.add(activeUser.getUserId());
        }

        List<TbUser> userList = new ArrayList<>();
        //如果用户id不为空，拼接  in  条件
        if(!CollectionUtils.isEmpty(activeUserIdList)) {
            userList = userMapper.selectBatchIds(activeUserIdList);
        }
        PageInfo<TbUser> pageInfo = new PageInfo<>(userList);
        PageResult<TbUser> pageResult = new PageResult<>(pageInfo);
        return pageResult;
    }

    //添加一条活动记录
    public void addActiveUserRecord(TbActiveUserRecord activeUserRecord) {
        activeUserRecordMapper.insert(activeUserRecord);
    }

    //根据活动id查询活动提供者
    public TbUser selectActiveProviderUserByActiveId(Integer activeId) {
        TbActive tbActive = activeMapper.selectById(activeId);
        return userMapper.selectById(tbActive.getCreateUserId());

    }

}
