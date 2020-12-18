package com.ctsi.controller;

import com.ctsi.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/18  22:29
 */
@RestController
public class ChartController {

    @Autowired
    ChartService chartService;

    //统计心理数据
    @GetMapping("/chart/mentalIllnessStatistics/{activeId}")
    public HashMap<String,Number> mentalIllnessStatistics(@PathVariable Integer activeId) {
        return chartService.mentalIllnessStatistics(activeId);
    }

}
