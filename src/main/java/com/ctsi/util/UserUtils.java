package com.ctsi.util;

import com.ctsi.entity.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/9  22:51
 */
public class UserUtils {

    //获取sessionUser
    public static TbUser getSessionTbUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object sessionUser = session.getAttribute("sessionUser");
        if(sessionUser == null) {
            return null;
        }
        TbUser result = (TbUser)sessionUser;
        return result;
    }
}
