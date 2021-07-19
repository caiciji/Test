package com.itheima.security;

import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
    提供认证和授权的代码
        1. 其实就是根据用户名来查询用户，并且给这个用户补全它的权限信息，返回。
        2. 这个类要被spring管理起来
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Autowired
    private UserService userService;


    /**
     * 根据用户名来查询用户的信息，包含用户的权限信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //1. 查询数据库得到用户信息
        User userInDB = userService.findUserByUsername(username);

        //2. 不一定要这个用户
        if(userInDB ==null){
            return null;
        }

        //3. 封装权限！
        List<GrantedAuthority> list=new ArrayList<>();

        //3.1 遍历这个用户的角色信息，因为一个用户可能有多种角色
        Set<Role> roles=userInDB.getRoles();

        //3.2 遍历所有的角色，因为一种角色可以有多种权限
        for (Role role : roles) {

            //3.3 获取这个角色的权限信息，可能有有多种
            Set<Permission> permissions=role.getPermissions();

            //3.4遍历每一个权限
            for (Permission permission : permissions) {
                //3.5 把权限的关键字装到list集合中
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        //4.最后封装出来全新打包的user对象
        return new org.springframework.security.core.userdetails.User(userInDB.getUsername(),userInDB.getPassword(),list);
    }
}
