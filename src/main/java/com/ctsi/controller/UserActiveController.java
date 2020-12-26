package com.ctsi.controller;

import com.ctsi.config.Constant;
import com.ctsi.entity.TbActive;
import com.ctsi.entity.TbActiveUser;
import com.ctsi.entity.TbActiveUserRecord;
import com.ctsi.entity.TbUser;
import com.ctsi.service.TbActiveService;
import com.ctsi.service.TbActiveUserService;
import com.ctsi.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    TbUserService userService;

    //用户加入活动
    @RequestMapping("/user/joinActive/{activeId}")
    public String userJoinActive(@PathVariable Integer activeId, HttpServletRequest request) {
        TbUser sessionUser = (TbUser) request.getSession().getAttribute("sessionUser");
        if(sessionUser == null) {
            return "redirect:/toLogin";
        }
        activeUserService.save(sessionUser.getId(),activeId);

        //查询所有的活动
        List<TbActive> activeList = activeUserService.selectActiveListByUser(sessionUser.getId(),activeId);
        TbUser userById = userService.getUserById(sessionUser.getId());
        request.setAttribute("activeList",activeList);
        request.setAttribute("user",userById);

        TbActiveUser activeUser = activeUserService.selectActiveUserByUserIdAndActiveId(sessionUser.getId(), activeId);
        request.setAttribute("activeUser",activeUser);


        request.setAttribute("user",sessionUser);
        return "personinfo/person-document";//去编辑档案页面
    }

    //查询用户信息（个人用户）
    @RequestMapping("/user/document")
    public String personDocument(HttpServletRequest request) {
        TbUser sessionUser = (TbUser) request.getSession().getAttribute("sessionUser");
        if(sessionUser == null) {
            return "redirect:/toLogin";
        }
        //查询所有的活动
        List<TbActive> activeList = activeUserService.selectActiveListByUser(sessionUser.getId(),null);

        request.setAttribute("activeList",activeList);

        TbUser dbUser = userService.getUserById(sessionUser.getId());
        dbUser.setAvatar(userService.getUserAvatar(sessionUser.getId()));
        request.setAttribute("user",dbUser);
        return "personinfo/person-document";//去编辑档案页面
    }

    //查询用户活动档案
    @RequestMapping("/user/document/user/{userId}/active/{activeId}")
    public String personDocumentByUserId(HttpServletRequest request,@PathVariable Integer userId,@PathVariable Integer activeId) {

        //查询所有的活动
        List<TbActive> activeList = activeUserService.selectActiveListByUser(userId,activeId);
        TbUser userById = userService.getUserById(userId);
        userById.setAvatar(userService.getUserAvatar(userId));
        request.setAttribute("activeList",activeList);
        request.setAttribute("user",userById);

        TbActiveUser activeUser = activeUserService.selectActiveUserByUserIdAndActiveId(userId, activeId);
        request.setAttribute("activeUser",activeUser);
        return "activemanage/user-document-detail";
    }

    //管理员：添加一条活动记录
    @RequestMapping("/user/putDocument/activeUser/{activeUserId}")
    public String putUserDocument(@PathVariable Integer activeUserId,HttpServletRequest request,String content,@RequestParam(required = false,value = "demo0") Integer ratings) {

        TbActiveUserRecord activeUserRecord = activeUserService.saveActiveUserRecord(activeUserId,content,ratings);

        //查询所有的活动
        List<TbActive> activeList = activeUserService.selectActiveListByUser(activeUserRecord.getUserId(),activeUserRecord.getActiveId());
        TbUser userById = userService.getUserById(activeUserRecord.getUserId());
        request.setAttribute("activeList",activeList);
        request.setAttribute("user",userById);

        TbActiveUser activeUser = activeUserService.selectActiveUserByUserIdAndActiveId(activeUserRecord.getUserId(), activeUserRecord.getActiveId());
        request.setAttribute("activeUser",activeUser);

        return "activemanage/user-document-detail";
    }

    //查询用户信息（个人用户），根据用户id查询
    @RequestMapping("/user/document/{userId}")
    public String personDocument(HttpServletRequest request,@PathVariable Integer userId) {

        //查询所有的活动
        List<TbActive> activeList = activeUserService.selectActiveListByUser(userId,null);

        request.setAttribute("activeList",activeList);
        TbUser dbUser = userService.getUserById(userId);
        dbUser.setAvatar(userService.getUserAvatar(dbUser.getId()));
        request.setAttribute("user",dbUser);
        return "personinfo/person-document";//去编辑档案页面
    }

    //根据活动id查询所有的用户
    @RequestMapping("/active/user/list/active/{activeId}")
    public String activeUserList(Integer page,@PathVariable Integer activeId,HttpServletRequest request) {

        request.setAttribute("pageResult",activeUserService.selectAllUserByActiveId(page, Constant.PAGE_SIZE, activeId));

        return "activedonatemanage/activedonate-user-list";
    }

}
