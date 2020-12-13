package com.ctsi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/12  17:18
 */
@Data
public class TbActiveUser {

    @TableId(type=IdType.AUTO)
    private Integer id;
    private Integer activeId;
    private Integer userId;
    private Integer enabled;
}
