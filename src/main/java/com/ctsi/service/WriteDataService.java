package com.ctsi.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ctsi.config.DataSourceConstant;
import com.ctsi.entity.TestUser;
import com.ctsi.mapper.TestUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName : WriteDataService
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-08-28 13:59
 */
@Service
@DS(DataSourceConstant.Write_DATASOURCE)
public class WriteDataService {

    @Autowired
    private TestUserDao testUserDao;

    public int insertUser(TestUser user) {
        int insert = testUserDao.insert(user);
        return insert;
    }

}
