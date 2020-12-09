package com.ctsi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
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

    @DateTimeFormat(pattern="mm/dd/yyyy")
    private Date startTime;

    @DateTimeFormat(pattern="mm/dd/yyyy")
    private Date endTime;


    private String mobile;


    private Integer personNum;

    private Integer status;

    private Integer enabled;

    private Integer type;

    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date createTime;


}