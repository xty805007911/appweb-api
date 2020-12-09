package com.ctsi.controller;

import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.PutObjectOptions;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName : MinioController
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-09 08:49
 */
@Controller
public class MinioController {

    @Autowired
    private MinioClient minioClient;
    private static final String MINIO_BUCKET = "appweb";


    @GetMapping("/get")
    public String list() throws Exception {
        System.out.println(minioClient.getObjectUrl(MINIO_BUCKET,"default.gif"));
        return null;
    }










}
