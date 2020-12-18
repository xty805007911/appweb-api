package com.ctsi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.entity.TbActiveUser;
import com.ctsi.entity.TbUser;
import com.ctsi.mapper.TbActiveUserMapper;
import com.ctsi.mapper.TbUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/18  22:47
 */
@Service
public class ChartService {

    @Autowired
    TbActiveUserMapper activeUserMapper;
    @Autowired
    TbUserMapper userMapper;

    //根据活动id查询用户集合
    public List<TbUser> getUserIdListByActiveId(Integer activeId) {
        QueryWrapper<TbActiveUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("active_id",activeId);
        queryWrapper.groupBy("user_id");

        List<TbActiveUser> activeUserList = activeUserMapper.selectList(queryWrapper);

        List<Integer> userIdList = new ArrayList<>();
        for(TbActiveUser activeUser : activeUserList) {
            userIdList.add(activeUser.getUserId());
        }

        return userMapper.selectBatchIds(userIdList);

    }

    //统计心理
    public HashMap<String,Number> mentalIllnessStatistics(Integer activeId) {
        List<TbUser> userList = getUserIdListByActiveId(activeId);
        HashMap<String,Number> result = new HashMap<>();

        Integer mentalIllnessTrue = 0;
        Integer mentalIllnessFalse = 0;

        Integer physicalIllnessTrue = 0;
        Integer physicalIllnessFalse = 0;

        Double weightAvg = 0.0D;
        Double heightAvg = 0.0d;
        Double dayActive = 0.0d;

        for(TbUser user: userList) {

            //心理活动
            if(user.getMentalIllness() == null || "".equals(user.getMentalIllness().trim())) {
                mentalIllnessFalse++;
            }else {
                mentalIllnessTrue++;
            }

            //身体活动
            if(user.getPhysicalIllness() == null ||"".equals(user.getPhysicalIllness().trim())) {
                physicalIllnessFalse++;
            }else {
                physicalIllnessTrue++;
            }

            //身高
            if(user.getHeight()!= null) {
                heightAvg+=user.getHeight();
            }
            //体重
            if(user.getWeight()!=null) {
                weightAvg+=heightAvg;
            }

            if(user.getDayActive()!=null) {
                dayActive+=user.getDayActive();
            }

        }

        result.put("mentalIllnessTrue",mentalIllnessTrue);
        result.put("mentalIllnessFalse",mentalIllnessFalse);

        result.put("physicalIllnessFalse",physicalIllnessFalse);
        result.put("physicalIllnessTrue",physicalIllnessTrue);


        result.put("heightAvg",heightAvg/userList.size());
        result.put("weightAvg",weightAvg/userList.size());

        result.put("dayActive",dayActive);

        return result;

    }



}
