package com.itheima.dao;

import com.itheima.health.pojo.User;

public interface UserDao {

    /**
     * 根据用户名来查询用户的信息，要查询数据库
     * @param username
     * @return
     */
   User findUserByUsername(String username);

}
