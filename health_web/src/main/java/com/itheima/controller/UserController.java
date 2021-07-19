package com.itheima.controller;


import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    @RequestMapping("/getUsername")
    public Result getUsername(){

        try {
            //1. 问springsecurity要用户信息
            //当我们登录成功了之后，springSecurity会在session里面保存我们的用户认证授权的信息
            User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
