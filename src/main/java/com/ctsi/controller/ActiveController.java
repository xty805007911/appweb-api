package com.ctsi.controller;

import com.ctsi.config.Constant;
import com.ctsi.entity.TbActive;
import com.ctsi.entity.TbActiveType;
import com.ctsi.service.TbActiveService;
import com.ctsi.service.TbActiveTypeService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    TbActiveTypeService activeTypeService;

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
        if(page == null || page <= 0) {
            page = 1;
        }
        PageResult<TbActive> pageResult = activeService.pageList(active, page, Constant.PAGE_SIZE);
        request.setAttribute("pageResult",pageResult);
        return "/activemanage/active-list";
    }

    //去添加页面
    @RequestMapping("/active/toAdd")
    public String toAdd(HttpServletRequest request) {
        List<TbActiveType> activeTypeList = activeTypeService.getActiveTypeList();
        Map<String,Object> map = new HashMap<>();
        map.put("activeTypeList",activeTypeList);
        request.setAttribute("result",map);
        return "/activemanage/active-add";
    }


}
