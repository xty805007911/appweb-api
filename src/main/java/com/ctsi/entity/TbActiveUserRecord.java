package com.ctsi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/13  10:37
 */
@Data
public class TbActiveUserRecord {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer activeUserId;
    private Integer activeId;
    private Integer userId;
    private Integer ratings;
    private String content;
    private Date createTime;

}
