package com.ctsi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

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

    @TableField(exist = false)
    private String token;
}
