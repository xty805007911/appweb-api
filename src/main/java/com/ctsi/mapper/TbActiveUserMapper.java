package com.ctsi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctsi.entity.TbActiveUser;
import com.ctsi.entity.TbUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2020/12/12  17:21
 */
@Repository
public interface TbActiveUserMapper extends BaseMapper<TbActiveUser> {

    @Select("SELECT u.* FROM `tb_active_user` au\n" +
            "LEFT OUTER JOIN `tb_user` u\n" +
            "ON u.id=au.user_id\n" +
            "WHERE au.active_id=#{activeId}")
    List<TbUser> selectAllUserByActiveId(Integer activeId);
}
