package com.ctsi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName : TbUser
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-07 17:28
 */
@Data
public class TbUser {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String realname;
    private Integer gender;
    private String mobile;
    private Date create_time;
    private String address;
    private Integer enabled;
    private String avatar;

    //个人档案
    private String mentalIllness;
    private String physicalIllness;

    @TableField(exist = false)
    private String token;

    //一个用户对多个活动
    @TableField(exist = false)
    private List<TbActive> activeList;
}
