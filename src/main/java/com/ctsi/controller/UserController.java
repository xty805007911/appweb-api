package com.ctsi.controller;

import com.ctsi.config.Constant;
import com.ctsi.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName : UserController
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-16 09:29
 */
@Controller
public class UserController {

    @Autowired
    UserManageService userManageService;

    @RequestMapping("/manage/user/list")
    public String userPageList(Integer page, HttpServletRequest request) {
        if(page == null || page <=0 ) {
            page = 1;
        }
        request.setAttribute("pageResult",userManageService.userPageList(page, Constant.PAGE_SIZE));
        return "usermanage/user-list";
    }

    @RequestMapping("/manager/user/enabled")
    public String setUserEnabled(Integer userId,Integer status) {
        if(status==null) {
            status = 0;
        }
        userManageService.setEnabled(userId,status);
        return "redirect:/manage/user/list";
    }

}
