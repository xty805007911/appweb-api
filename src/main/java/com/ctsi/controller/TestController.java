package com.ctsi.controller;

import com.ctsi.util.IpUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @ClassName : TestController
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-14 10:53
 */
@RestController
public class TestController {

    private static final double num = Math.random();


    @GetMapping("/lbtest")
    public Object Test(HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<>();


        map.put("Ip", IpUtils.getIpAddr(request));
        map.put("Random",num);
        return map;
    }
}
