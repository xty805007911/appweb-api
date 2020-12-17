package com.ctsi.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.entity.TbChat;
import com.ctsi.entity.TbUser;
import com.ctsi.mapper.TbChatMapper;
import com.ctsi.util.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
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
    @Autowired
    TbUserService userService;

    //分页查询消息，从后往前查询
    public PageResult<TbChat> pageChatListDesc(Integer page, Integer size,Integer userUserId,Integer providerId) {

        if(page == null || page <= 0) {
            page = 1;
        }

        QueryWrapper<TbChat> chatQueryWrapper = new QueryWrapper<>();
        chatQueryWrapper.eq("user_user_id",userUserId);
        chatQueryWrapper.eq("active_user_id",providerId);

        chatQueryWrapper.orderByDesc("id");

        PageHelper.startPage(page,size);
        List<TbChat> chatList = chatMapper.selectList(chatQueryWrapper);

        if(!CollectionUtils.isEmpty(chatList)) {
            for(TbChat chat : chatList) {
                chat.setUserAvatar(userService.getUserAvatar(chat.getUserUserId()));
                chat.setProviderAvatar(userService.getUserAvatar(chat.getActiveUserId()));
            }
        }

        PageInfo<TbChat> pageInfo = new PageInfo<>(chatList);
        PageResult<TbChat> pageResult = new PageResult<>(pageInfo);


        return pageResult;
    }

    //保存
    public void save(TbChat chat) {
        chat.setCreateTime(new Date());
        chatMapper.insert(chat);
    }

    /**
     * @Author Xiaotianyu
     * @Description以活动提供者身份查询正在交谈的用户
     * #以提供者查询
    SELECT tu.* FROM `tb_chat` tc
    LEFT OUTER JOIN tb_user tu
    ON tc.user_user_id = tu.id
    WHERE tc.active_user_id = 15
    GROUP BY tc.user_user_id
     * @Date  
     * @Param 
     * @return 
     **/
    public PageResult<TbUser> selectChatingUserToActiveProvider(Integer page,Integer size,Integer providerId) {
        if(page == null || page <= 0) {
            page = 1;
        }

        PageHelper.startPage(page,size);

        List<TbUser> userList = chatMapper.selectChatingUserToActiveProvider(providerId);

        if(!CollectionUtils.isEmpty(userList)) {
            //设置用户头像
            for(TbUser user : userList) {
                String avatar = userService.getUserAvatar(user.getId());
                user.setAvatar(avatar);
            }
        }

        PageInfo<TbUser> pageInfo = new PageInfo<>(userList);
        PageResult<TbUser> pageResult = new PageResult<>(pageInfo);
        return pageResult;
    }


}
