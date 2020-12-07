package com.ctsi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctsi.entity.TestUser;
import org.springframework.stereotype.Repository;

/**
 * @ClassName : TestUserDao
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-11-04 08:23
 */
@Repository
public interface TestUserDao extends BaseMapper<TestUser> {
}
