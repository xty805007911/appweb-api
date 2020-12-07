package com.ctsi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName : TestUser
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-11-03 18:43
 */
@Data
public class TestUser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
}
