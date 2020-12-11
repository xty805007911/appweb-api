package com.ctsi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    @DateTimeFormat(pattern="MM/dd/yyyy")
    private Date startTime;

    @DateTimeFormat(pattern="MM/dd/yyyy")
    private Date endTime;

    private String mobile;

    private Integer personNum;

    private String status;

    private Integer enabled;

    private Integer type;

    private String tag;

    @DateTimeFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date createTime;

    @TableField(exist = false)
    private List<TbFileUrl> fileList;

    @TableField(exist = false)
    private TbUser createUser;//创建者

    @TableField(exist = false)
    private String image;



}