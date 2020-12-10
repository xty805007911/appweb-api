package com.ctsi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @ClassName : TbFileUrl
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-10 09:53
 */
@Data
public class TbFileUrl {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String tbname;
    private String urlFont;
    private String urlEnd;
    private String realName;
    private Integer tbId;

    @TableField(exist = false)
    private String url;

    public String getUrl() {
        this.url = getUrlFont() + getUrlEnd();
        return this.url;
    }
}
