package com.ctsi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/14  22:49
 */
@Data
public class TbChat {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer activeUserId;
    private Integer userUserId;
    private String message;
    private Date createTime;
    private Integer status;

    @TableField(exist = false)
    private String userAvatar;

    @TableField(exist = false)
    private String providerAvatar;


}
