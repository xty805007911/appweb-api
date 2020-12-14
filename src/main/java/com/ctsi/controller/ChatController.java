package com.ctsi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/14  22:54
 */
@Controller
public class ChatController {

    //去聊天页面
    @RequestMapping("/toChat")
    public String toChat(HttpServletRequest request) {


        return "chat";
    }
}
