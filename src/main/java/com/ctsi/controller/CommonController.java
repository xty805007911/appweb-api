package com.ctsi.controller;

import com.ctsi.entity.TbUser;
import com.ctsi.service.UserService;
import com.ctsi.util.CookieUtils;
import com.ctsi.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : CommonController
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-07 17:36
 */
@Controller
public class CommonController {

    @Autowired
    UserService userService;

    //首页
    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        map.put("message","测试成功");
        request.setAttribute("result",map);
        return "/tmp";
    }

    //登录页跳转
    @RequestMapping("/toLogin")
    public String login(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        map.put("error","");
        request.setAttribute("result",map);
        return "/login";
    }
    //用户登录
    @PostMapping("/user/login")
    public String userLogin(HttpServletRequest request, HttpSession session, TbUser formUser) {
        TbUser user = userService.getUserByMobileAndPassword(formUser.getMobile(), formUser.getPassword());

        if(user == null || user.getId() == null) {
            Map<String,Object> map = new HashMap<>();
            map.put("error","mobile or password error.");
            request.setAttribute("result",map);
            return "/login";
        }

        session.setAttribute("sessionUser",user);
        return "redirect:/";
    }
    //退出
    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionUser");
        return "redirect:/";
    }

    @GetMapping("/user/info")
    public Result info(HttpServletRequest request) {
        String token = CookieUtils.getCookie(request, "token");
        Integer id = Integer.parseInt(token);
        return Result.ok(userService.getUserById(id));
    }

    @PostMapping("/user/register")
    public Result register(@RequestBody TbUser user) {
        userService.saveUser(user);
        return Result.ok();
    }
}
