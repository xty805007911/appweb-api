package com.ctsi.controller;

import com.ctsi.entity.TbChat;
import com.ctsi.entity.TbUser;
import com.ctsi.service.TbChatService;
import com.ctsi.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/14  22:54
 */
@RestController
public class ChatController {

    @Autowired
    TbChatService chatService;

    //去聊天页面
    @GetMapping("/chat/list")
    public Object toChat(HttpServletRequest request,Integer toUserId,Integer page) {
        TbUser sessionUser = (TbUser) request.getSession().getAttribute("sessionUser");
        PageResult<TbChat> pageResult = chatService.pageChatListDesc(page, 6);
        return pageResult;
    }
}
