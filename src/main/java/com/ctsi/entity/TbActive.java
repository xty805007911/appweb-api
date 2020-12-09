package com.ctsi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author ctsi-biyi-generator
*/
@Data
public class TbActive implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    @TableField("`desc`")
    private String desc;


    private Float cost;


    private String address;

    private Integer createUserId;


    private String code;


    private String images;


    private Date startTime;


    private Date endTime;


    private String mobile;


    private Integer personNum;


}