package com.ctsi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.entity.TbFileUrl;
import com.ctsi.mapper.TbFileUrlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName : TbFileUrlService
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-10 10:59
 */
@Service
public class TbFileUrlService {

    @Autowired
    TbFileUrlMapper fileUrlMapper;

    //保存
    public void save(TbFileUrl fileUrl) {
        fileUrlMapper.insert(fileUrl);
    }

    //根据外表名和外表id查询
    public List<TbFileUrl> getFileUrlByTbnameAndTbId(String tbname, Integer tbId) {
        QueryWrapper<TbFileUrl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tbname",tbname);
        queryWrapper.eq("tb_id",tbId);
        List<TbFileUrl> tbFileUrlList = fileUrlMapper.selectList(queryWrapper);
        return tbFileUrlList;
    }

}
