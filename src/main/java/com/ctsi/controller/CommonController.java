package com.ctsi.controller;

import com.ctsi.config.Constant;
import com.ctsi.entity.TbFileUrl;
import com.ctsi.entity.TbRole;
import com.ctsi.entity.TbUser;
import com.ctsi.service.*;
import com.ctsi.util.CookieUtils;
import com.ctsi.util.PageResult;
import com.ctsi.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : CommonController
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-07 17:36
 */
@Controller
@Scope("prototype")
public class CommonController {

    @Autowired
    TbUserService userService;
    @Autowired
    MinioService minioService;
    @Autowired
    TbFileUrlService fileUrlService;
    @Autowired
    TbRoleService roleService;
    @Autowired
    TbChatService chatService;

    //登录页跳转
    @RequestMapping("/toLogin")
    public String login(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        map.put("msg","");
        request.setAttribute("result",map);
        return "login";
    }

    // 去注册页面
    @RequestMapping("/toRegister")
    public String toRegister() {

        return "register";
    }

    //注册
    @RequestMapping("/register")
    public String register(TbUser formUser, MultipartFile[] file,HttpServletRequest request) {

        if(formUser.getMobile() == null || formUser.getMobile().trim().equals("")) {
            request.setAttribute("msg","The cell phone number cannot be empty");
            return "register";
        }
        if(formUser.getPassword() == null || formUser.getPassword().trim().equals("")) {
            request.setAttribute("msg","The password cannot be empty");
            return "register";
        }
        if(formUser.getRealname() == null || formUser.getRealname().trim().equals("")) {
            request.setAttribute("msg","The real name cannot be empty");
            return "register";
        }
        if(userService.isUserExistByMobile(formUser.getMobile())) {
            request.setAttribute("msg","The phone number has been registered");
            return "register";
        }

        userService.saveUser(formUser);

        System.out.println(file[0].getSize());

        //文件为空，设置默认头像
        if(file == null || file.length == 0 || file[0].getSize()==0) {
            TbFileUrl fileUrl = new TbFileUrl();
            fileUrl.setTbId(formUser.getId());
            fileUrl.setTbname(Constant.FILE_TB_NAME_USER);
            fileUrl.setUrlFont(minioService.getFileUrlFont());
            fileUrl.setUrlEnd("default.gif");
            fileUrlService.save(fileUrl);
        }else {
            //如果选择文件，执行以下逻辑
            Map<String, List> fileNameMap = minioService.upload(file);//名称
            List<String> realName = fileNameMap.get("realName");
            List<String> uuidName = fileNameMap.get("uuidName");

            String fileUrlFont = minioService.getFileUrlFont();

            int index = 0;
            for(String name : uuidName) {
                TbFileUrl fileUrl = new TbFileUrl();
                fileUrl.setTbId(formUser.getId());
                fileUrl.setTbname(Constant.FILE_TB_NAME_USER);
                fileUrl.setUrlFont(fileUrlFont);
                fileUrl.setUrlEnd(name);
                fileUrl.setRealName(realName.get(index));
                fileUrlService.save(fileUrl);
                index++;
            }
        }


        return "redirect:/toLogin";
    }

    //用户登录
    @RequestMapping("/user/login")
    public String userLogin(HttpServletRequest request, HttpSession session, TbUser formUser) {
        TbUser user = userService.getUserByMobileAndPassword(formUser.getMobile(), formUser.getPassword());
        if(user == null || user.getId() == null) {
            Map<String,Object> map = new HashMap<>();
            map.put("msg","mobile or password error.");
            request.setAttribute("result",map);
            return "login";
        }

        user.setAvatar(userService.getUserAvatar(user.getId()));
        List<TbRole> roleList = roleService.userRoleList(user.getId());

        List<TbRole> allRoleList = roleService.selectAllRoles();
        if(!CollectionUtils.isEmpty(roleList)) {

            for(TbRole role : allRoleList) {
                session.setAttribute("sessionrole"+role.getCode(),0);
            }
            for(TbRole role : roleList) {
                session.setAttribute("sessionrole"+role.getCode(),1);
            }
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
