package com.ctsi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ctsi.entity.TestUser;
import com.ctsi.service.K8sService;
import com.ctsi.service.ReadDataService;
import com.ctsi.service.WriteDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @ClassName : MainController
 * @Description :
 * @Author : Xiaotianyu
 * @Date: 2020-08-27 10:10
 */
@RestController
public class MainController {

    @Autowired
    ReadDataService readDataService;
    @Autowired
    WriteDataService writeDataService;

    private final Logger logger = LoggerFactory.getLogger(MainController.class);


    //swagger 重定向地址，与比翼相同
    @GetMapping("/swagger-ui/index.html")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    //请求首页
    @GetMapping("/")
    public HashMap<String,String> index() {
        HashMap<String,String> map = new LinkedHashMap<>();
        map.put("message","后端服务已正常启动...");
        return map;
    }

    @GetMapping("/read")
    public TestUser read(@RequestParam(required = true) Integer id) {
        return readDataService.findById(id);
    }

    @GetMapping("/page/{page}/{pageSize}")
    public IPage<TestUser> readByPage(@PathVariable Integer page, @PathVariable Integer pageSize) {
        return readDataService.findUserListByPage(page,pageSize);
    }

    @GetMapping("/insert")
    public Object insert(TestUser testUser) {
        return writeDataService.insertUser(testUser);
    }

    @Autowired
    K8sService k8sService;
    @GetMapping("/k8s/nodes")
    public Object getNodes() {
        return k8sService.NodeList();
    }



}
