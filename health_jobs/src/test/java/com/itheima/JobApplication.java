package com.itheima;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class JobApplication {

    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:applicationContext-jobs.xml");
        System.in.read();// 阻塞当前的main线程，不让它结果，spring容器就会一直存在，任务才可以运行
    }
}
