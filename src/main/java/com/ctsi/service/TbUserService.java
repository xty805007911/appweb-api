package com.ctsi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.config.Constant;
import com.ctsi.entity.TbFileUrl;
import com.ctsi.entity.TbUser;
import com.ctsi.mapper.TbFileUrlMapper;
import com.ctsi.mapper.TbUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @ClassName : UserService
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-07 17:30
 */
@Repository
public class TbUserService {
    @Autowired
    TbUserMapper userMapper;
    @Autowired
    TbFileUrlMapper fileUrlMapper;

    public TbUser getUserByMobileAndPassword(String mobile,String password) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("mobile",mobile);
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

    public void saveUser(TbUser user) {
        if(user.getAvatar() == null || user.getAvatar().equals("")) {
            user.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        }
        user.setCreate_time(new Date());
        user.setEnabled(1);
        userMapper.insert(user);
    }

    public boolean isUserExistByMobile(String mobile) {
        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",queryWrapper);
        List<TbUser> tbUsers = userMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(tbUsers)) {
            return false;
        }
        return true;
    }

    //获取用户头像
    public String getUserAvatar(Integer userId) {
        QueryWrapper<TbFileUrl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tb_id",userId);
        queryWrapper.eq("tbname", Constant.FILE_TB_NAME_USER);
        queryWrapper.orderByDesc("id");
        TbFileUrl tbFileUrl = fileUrlMapper.selectOne(queryWrapper);
        if(tbFileUrl == null) {
            return "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
        }else {
            return tbFileUrl.getUrlFont() + tbFileUrl.getUrlEnd();
        }
    }
}
