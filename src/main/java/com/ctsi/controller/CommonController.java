package com.ctsi.controller;

import com.ctsi.entity.TbUser;
import com.ctsi.service.UserService;
import com.ctsi.util.CookieUtils;
import com.ctsi.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName : CommonController
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-07 17:36
 */
@RestController
public class CommonController {

    @Autowired
    UserService userService;

    @PostMapping("/user/login")
    public Result login(@RequestBody TbUser formUser) {
        TbUser user = userService.getUserByMobileAndPassword(formUser.getMobile(), formUser.getPassword());
        return Result.ok(user);
    }

    @GetMapping("/user/logout")
    public Result logout() {
        return Result.ok();
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
