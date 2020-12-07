package com.ctsi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.entity.TbUser;
import com.ctsi.mapper.TbUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ClassName : UserService
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-07 17:30
 */
@Repository
public class UserService {
    @Autowired
    TbUserMapper userMapper;

    public TbUser getUserByUsernameAndPassword(String username,String password) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        List list = userMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(list)) {
            return null;//未登陆
        }
        return (TbUser) list.get(0);
    }

    public TbUser getUserById(Integer id) {
        return userMapper.selectById(id);
    }
}
