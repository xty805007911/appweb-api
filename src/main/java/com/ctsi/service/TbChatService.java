package com.ctsi.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.entity.TbChat;
import com.ctsi.mapper.TbChatMapper;
import com.ctsi.util.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName : ChatService
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-15 09:46
 */
@Service
public class TbChatService {

    @Autowired
    TbChatMapper chatMapper;

    //分页查询消息，从后往前查询
    public PageResult<TbChat> pageChatListDesc(Integer page, Integer size) {

        if(page == null || page <= 0) {
            page = 1;
        }

        QueryWrapper<TbChat> chatQueryWrapper = new QueryWrapper<>();
        chatQueryWrapper.eq("user_user_id",2);
        chatQueryWrapper.eq("active_user_id",1);

        chatQueryWrapper.orderByDesc("create_time");

        PageHelper.startPage(page,size);
        List<TbChat> chatList = chatMapper.selectList(chatQueryWrapper);

        PageInfo<TbChat> pageInfo = new PageInfo<>(chatList);
        PageResult<TbChat> pageResult = new PageResult<>(pageInfo);

        System.out.println(pageResult.getList().size());

        return pageResult;
    }
}
