package com.ctsi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctsi.entity.TbChat;
import com.ctsi.entity.TbUser;
import com.ctsi.util.PageResult;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @ClassName : TbChatMapper
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-15 09:43
 */
@Repository
public interface TbChatMapper extends BaseMapper<TbChat> {


    //以活动提供者身份查询用户
    @Select("SELECT tu.* FROM `tb_chat` tc\n" +
            "LEFT OUTER JOIN tb_user tu\n" +
            "ON tc.user_user_id = tu.id\n" +
            "WHERE tc.active_user_id = #{id}\n" +
            "GROUP BY tc.user_user_id  ORDER BY tc.create_time DESC ")
    List<TbUser> selectChatingUserToActiveProvider(Integer providerId);

}
