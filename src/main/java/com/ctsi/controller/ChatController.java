package com.ctsi.controller;

import com.ctsi.entity.TbChat;
import com.ctsi.entity.TbUser;
import com.ctsi.service.TbChatService;
import com.ctsi.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/14  22:54
 */
@Controller
public class ChatController {

    @Autowired
    TbChatService chatService;

    //用户去聊天页面
    @GetMapping("/chat/list")
    @ResponseBody
    public Object toChat(HttpServletRequest request,Integer providerId,Integer page) {
        TbUser sessionUser = (TbUser) request.getSession().getAttribute("sessionUser");
        PageResult<TbChat> pageResult = chatService.pageChatListDesc(page, 100,sessionUser.getId(),providerId);
        return pageResult;
    }

    //provider去聊天页面
    @GetMapping("/chat/list/forProvider")
    @ResponseBody
    public Object toChatForProvider(HttpServletRequest request,Integer userId,Integer page) {
        TbUser sessionUser = (TbUser) request.getSession().getAttribute("sessionUser");
        PageResult<TbChat> pageResult = chatService.pageChatListDesc(page, 100,userId,sessionUser.getId());
        return pageResult;
    }


    //用户给provider发消息
    @GetMapping("/chat/toProvider")
    @ResponseBody
    public Object chatToProvider(TbChat chat,Integer providerId,HttpServletRequest request) {
        TbUser sessionUser = (TbUser) request.getSession().getAttribute("sessionUser");
        chat.setActiveUserId(providerId);
        chat.setUserUserId(sessionUser.getId());
        chat.setStatus(1);
        chatService.save(chat);
        return "succ";
    }

    //provider给user发消息
    @GetMapping("/chat/toUser")
    @ResponseBody
    public Object chatToUser(TbChat chat,Integer userId,HttpServletRequest request) {
        TbUser sessionUser = (TbUser) request.getSession().getAttribute("sessionUser");
        chat.setActiveUserId(sessionUser.getId());
        chat.setUserUserId(userId);
        chat.setStatus(0);
        chatService.save(chat);
        return "succ";
    }


    //以活动提供者角度查询正在交流的用户列表
    @RequestMapping("/chat/provider/chattingUser")
    public Object chatingUserToProvider(HttpSession session,HttpServletRequest request) {
        TbUser activeProviderUser = (TbUser) session.getAttribute("sessionUser");
        PageResult<TbUser> chattingUserPageResult = chatService.selectChatingUserToActiveProvider(1, 100, activeProviderUser.getId());
        request.setAttribute("chattingUserPageResult",chattingUserPageResult);
        return "chat-user-list";
    }

}
