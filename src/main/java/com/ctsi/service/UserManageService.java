package com.ctsi.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.entity.TbUser;
import com.ctsi.mapper.TbUserMapper;
import com.ctsi.util.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName : UserManageService
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-16 10:16
 */
@Service
public class UserManageService {
    @Autowired
    TbUserMapper userMapper;
    @Autowired
    TbUserService userService;

    public PageResult<TbUser> userPageList(Integer page,Integer size) {

        PageHelper.startPage(page,size);

        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        List<TbUser> userList = userMapper.selectList(queryWrapper);
        for(TbUser user : userList) {
            String avatar = userService.getUserAvatar(user.getId());
            user.setAvatar(avatar);
        }

        PageInfo<TbUser> pageInfo = new PageInfo<>(userList);
        PageResult<TbUser> pageResult = new PageResult<>(pageInfo);

        return pageResult;
    }

    public void setEnabled(Integer userId,Integer enabled) {
        TbUser tbUser = userMapper.selectById(userId);
        tbUser.setEnabled(enabled);
        userMapper.updateById(tbUser);
    }

}
