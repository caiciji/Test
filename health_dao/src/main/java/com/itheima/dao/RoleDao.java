package com.itheima.dao;

import com.itheima.health.pojo.Role;

import java.util.Set;

public interface RoleDao {


    /**
     * 根据用户的id查询这个用户属于什么角色
     * @param uid
     * @return 一个用户可能有多重角色，所以要返回set集合
     */
    Set<Role> findRoleByUid(int uid);
}
