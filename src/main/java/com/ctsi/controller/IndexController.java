package com.ctsi.controller;

import com.ctsi.config.Constant;
import com.ctsi.entity.TbActive;
import com.ctsi.entity.TbActiveType;
import com.ctsi.service.TbActiveService;
import com.ctsi.service.TbActiveTypeService;
import com.ctsi.service.TbUserService;
import com.ctsi.util.PageResult;
import com.ctsi.vo.ActivityQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:首页
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/10  21:21
 */
@Controller
public class IndexController {

    @Autowired
    TbUserService userService;
    @Autowired
    TbActiveTypeService activeTypeService;
    @Autowired
    TbActiveService activeService;

    //首页
    @RequestMapping("/")
    public String index(HttpServletRequest request) {

        request.getSession().setAttribute("sessionUser",userService.getUserById(1));
        //返回结果集
        Map<String,Object> map = new HashMap<>();

        List<TbActiveType> activeTypeList = activeTypeService.getActiveTypeList();
        map.put("activeTypeList",activeTypeList);

        //查询推荐、热门
        List<TbActive> hotActiveList = activeService.getActiveListByTag(10, Constant.ACTIVITY_TAG_HOT);
        List<TbActive> recommendActiveList = activeService.getActiveListByTag(10, Constant.ACTIVITY_TAG_RECOMMEND);
        map.put("hotActiveList",hotActiveList);
        map.put("recommendActiveList",recommendActiveList);

        //根据创建时间查询
        List<TbActive> activeListByCreateTimeDesc = activeService.getActiveListByCreateTimeDesc(5);
        map.put("activeListByCreateTimeDesc",activeListByCreateTimeDesc);

        //根据开始时间查询（即将到来的）
        List<TbActive> activeListByStartTimeDesc = activeService.getActiveListByStartTimeDesc(10);
        map.put("activeListByStartTimeDesc",activeListByStartTimeDesc);


        request.setAttribute("result",map);
        return "/index";
    }

    //首页：查询活动详情
    @RequestMapping("/index/active/{id}")
    public String indexActiveDetail(@PathVariable Integer id,HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        TbActive active = activeService.getActiveById(id);

        List<TbActiveType> activeTypeList = activeTypeService.getActiveTypeList();

        map.put("active",active);
        map.put("activeTypeList",activeTypeList);

        request.setAttribute("result",map);
        return "/index-active-detail";
    }

    //查询
    @RequestMapping(value = "/index/active/search")
    public String search(ActivityQueryVO activityQueryVO,HttpServletRequest request) {

        activityQueryVO.setSize(Constant.PAGE_SIZE);

        PageResult<TbActive> activePageResult = activeService.pageListByKeywords(activityQueryVO);

        request.setAttribute("activityQueryVO",activityQueryVO);
        request.setAttribute("pageResult",activePageResult);

        return "/index-active-search";
    }

}