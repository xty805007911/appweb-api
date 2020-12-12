package com.ctsi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctsi.entity.TbActive;
import com.ctsi.entity.TbUser;
import com.ctsi.vo.ActivityQueryVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName : TbActiveMapper
 * @Description :
 * @Author : Xiaotianyu  //作者
 * @Date: 2020-12-09 15:30
 */
@Repository
public interface TbActiveMapper extends BaseMapper<TbActive> {

    // 条件查询
    @Select("SELECT * FROM tb_active WHERE `NAME` LIKE CONCAT('%',#{keywords},'%') OR `DESC` LIKE CONCAT('%',#{keywords},'%') OR cost LIKE CONCAT('%',#{keywords},'%')")
    public List<TbActive> searchByQueryVO(ActivityQueryVO activityQueryVO);

}
