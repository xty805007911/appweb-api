package com.ctsi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/9  21:27
 */
@Data
public class TbActiveType {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;
    private String extra;
}
