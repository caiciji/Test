package com.itheima.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;

public interface OrderSettingService {

    /**
     * 添加预约信息
     * @param orderSettingList
     * @return
     */
   int  add(List<OrderSetting> orderSettingList);


    /**
     * 查询指定月份的信息
     * @param date
     * @return
     */
   List<OrderSetting> findOrderByMonth(String date);


    /**
     * 根据日期，修改预约数量
     * @param date 日期
     * @param number 数量
     * @return
     */
   int updateOrderNumber(String date,int number);
}
