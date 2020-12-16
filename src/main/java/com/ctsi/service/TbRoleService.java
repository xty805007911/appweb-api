package com.ctsi.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.entity.TbRole;
import com.ctsi.entity.TbUserRole;
import com.ctsi.mapper.TbRoleMapper;
import com.ctsi.mapper.TbUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbRoleService{

    @Autowired
    TbRoleMapper roleMapper;
    @Autowired
    TbUserRoleMapper userRoleMapper;

    public List<TbRole> userRoleList(Integer userId) {
        QueryWrapper<TbUserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id",userId);
        List<TbUserRole> userRoleList = userRoleMapper.selectList(userRoleQueryWrapper);

        List<Integer> roleIdList = new ArrayList<>();
        for(TbUserRole userRole : userRoleList) {
            roleIdList.add(userRole.getRoleId());
        }

        if(!CollectionUtils.isEmpty(roleIdList)) {
            QueryWrapper<TbRole> roleQueryWrapper = new QueryWrapper<>();
            roleQueryWrapper.in("id",roleIdList);
            List<TbRole> roleList = roleMapper.selectList(roleQueryWrapper);
            return roleList;
        }
        return null;
    }

    public List<TbRole> selectAllRoles() {
        return roleMapper.selectList(null);
    }

}
