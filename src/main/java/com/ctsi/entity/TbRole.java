package com.ctsi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;


@Data
public class TbRole implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer parentid;
    private String extra;
    private String code;

}