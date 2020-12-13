package com.ctsi.controller;

import com.ctsi.config.Constant;
import com.ctsi.entity.TbActive;
import com.ctsi.entity.TbUser;
import com.ctsi.service.TbActiveService;
import com.ctsi.service.TbActiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/12  17:37
 */
@Controller
public class UserActiveController {
    @Autowired
    TbActiveUserService activeUserService;
    @Autowired
    TbActiveService activeService;

    //用户加入活动
    @RequestMapping("/user/joinActive/{activeId}")
    public String userJoinActive(@PathVariable Integer activeId, HttpServletRequest request) {
        TbUser sessionUser = (TbUser) request.getSession().getAttribute("sessionUser");
        if(sessionUser == null) {
            return "redirect:/toLogin";
        }
        activeUserService.save(sessionUser.getId(),activeId);


        request.setAttribute("user",sessionUser);
        return "/personinfo/person-document";//去编辑档案页面
    }

    //查询用户信息
    @RequestMapping("/user/document")
    public String personDocument(HttpServletRequest request) {
        TbUser sessionUser = (TbUser) request.getSession().getAttribute("sessionUser");
        if(sessionUser == null) {
            return "redirect:/toLogin";
        }
        //查询所有的活动
        List<TbActive> activeList = activeUserService.selectActiveListByUser(sessionUser.getId());

        request.setAttribute("activeList",activeList);
        request.setAttribute("user",sessionUser);
        return "/personinfo/person-document";//去编辑档案页面
    }

}
