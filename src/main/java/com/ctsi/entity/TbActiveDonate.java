package com.ctsi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName : TbActiveDonate
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-18 18:12
 */
@Data
public class TbActiveDonate {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer donateId;
    private Integer activeId;
    private Date createTime;

    @TableField(exist = false)
    private TbActive active;
}
