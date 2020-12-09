package com.ctsi.controller;

import com.ctsi.config.Constant;
import com.ctsi.entity.TbActive;
import com.ctsi.service.TbActiveService;
import com.ctsi.util.CheckNPTUtils;
import com.ctsi.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @ClassName : ActiveController
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-09 15:37
 */
@Controller
public class ActiveController {
    @Autowired
    TbActiveService activeService;

    //添加活动
    @RequestMapping("/active/add")
    public String addActive(HttpServletRequest request, TbActive active) {
        activeService.add(active);
        return "rediect:/active/pageList";
    }

    //分页条件查询
    @RequestMapping("/active/pageList")
    public String activePageList(@RequestParam(required = false) TbActive active,HttpServletRequest request,Integer page) throws IOException {
        if(active == null) {
            active = new TbActive();
        }
        CheckNPTUtils.checkCurrentPage(page);
        PageResult<TbActive> pageResult = activeService.pageList(active, page, Constant.PAGE_SIZE);
        request.setAttribute("pageResult",pageResult);
        return "/activemanage/active-list";
    }


}
