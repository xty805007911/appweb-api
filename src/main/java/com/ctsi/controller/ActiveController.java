package com.ctsi.controller;

import com.ctsi.config.Constant;
import com.ctsi.entity.TbActive;
import com.ctsi.entity.TbActiveType;
import com.ctsi.entity.TbFileUrl;
import com.ctsi.entity.TbUser;
import com.ctsi.service.MinioService;
import com.ctsi.service.TbActiveService;
import com.ctsi.service.TbActiveTypeService;
import com.ctsi.service.TbFileUrlService;
import com.ctsi.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    @Autowired
    MinioService minioService;
    @Autowired
    TbFileUrlService fileUrlService;

    //添加活动
    @RequestMapping("/active/add")
    public String addActive(HttpServletRequest request, TbActive active, MultipartFile[] file) {
        activeService.add(active);
        //没有选择文件，直接返回
        if(file == null || file.length == 0) {
            return "redirect:/active/pageList";
        }
        //如果选择文件，执行以下逻辑
        Map<String, List> fileNameMap = minioService.upload(file);//名称
        List<String> realName = fileNameMap.get("realName");
        List<String> uuidName = fileNameMap.get("uuidName");

        String fileUrlFont = minioService.getFileUrlFont();

        int index = 0;
        for(String name : uuidName) {
            TbFileUrl fileUrl = new TbFileUrl();
            fileUrl.setTbname(Constant.FILE_TB_NAME_ACTIVE);
            fileUrl.setUrlFont(fileUrlFont);
            fileUrl.setUrlEnd(name);
            fileUrl.setRealName(realName.get(index));
            fileUrl.setTbId(active.getId());
            fileUrlService.save(fileUrl);
            index++;
        }
        return "redirect:/active/pageList";
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

    //活动详情页面
    @RequestMapping("/active/{id}")
    public String activeDetail(@PathVariable Integer id,HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        TbActive active = activeService.getActiveById(id);

        List<TbActiveType> activeTypeList = activeTypeService.getActiveTypeList();

        map.put("active",active);
        map.put("activeTypeList",activeTypeList);

        request.setAttribute("result",map);
        return "/activemanage/active-edit";
    }

    //修改
    @RequestMapping("/active/edit")
    public String editActive(TbActive active,HttpServletRequest request) {
        activeService.editActive(active);
        return "redirect:/active/"+active.getId();
    }

    //删除活动
    @RequestMapping("/active/delete/{id}")
    public String deleteActive(@PathVariable Integer id) {
        activeService.setActiveEnabled(id,0);
        return "redirect:/active/pageList";
    }

    //查询活动参与人
    @RequestMapping("/active/participant/{id}")
    public String participantForActive(HttpServletRequest request,@PathVariable Integer id,Integer page) {
        PageResult<TbUser> pageResult = activeService.selectPageUserListByActiveId(id, page, Constant.PAGE_SIZE);
        request.setAttribute("pageResult",pageResult);
        request.setAttribute("activeId",id);
        return "/activemanage/active-participant";
    }


}
