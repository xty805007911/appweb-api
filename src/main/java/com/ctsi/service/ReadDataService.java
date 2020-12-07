package com.ctsi.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctsi.config.DataSourceConstant;
import com.ctsi.entity.TestUser;
import com.ctsi.mapper.TestUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName : ReadDataService
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-08-28 13:40
 */
@Service
@DS(DataSourceConstant.READ_DATASOURCE)
public class ReadDataService {
    @Autowired
    private TestUserDao testUserDao;

    public TestUser findById(Integer id) {
       return testUserDao.selectById(id);
    }

    public IPage<TestUser> findUserListByPage(int page,int pageSize) {
        IPage<TestUser> userPage = new Page<>(page,pageSize);
        userPage = testUserDao.selectPage(userPage, new QueryWrapper<>());
        return userPage;
    }
}
