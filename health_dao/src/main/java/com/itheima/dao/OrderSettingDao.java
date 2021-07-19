package com.itheima.dao;

import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface OrderSettingDao {


    /**
     * 根据日期去查询预约记录
     * @param date 如果存在指定日期，那么返回这个日期的预约记录对象，否则返回null
     * @return
     */
   OrderSetting findOrderSettingByDate(Date date);


    /**
     * 更新预约信息(根据日期去更新的。)
     * @param orderSetting
     * @return
     */
   int update(OrderSetting orderSetting);

    /**
     * 添加预约记录
     * @param orderSetting
     * @return
     */
   int add(OrderSetting orderSetting);

    /**
     * 查询从某格一个时间到另一个时间段的预约信息
     * @param begin
     * @param end
     * @return
     */
  List<OrderSetting> findOrderByMonth(@Param("begin") String begin,@Param("end") String end);

    /**
     * 根据日期修改预约数量
     * @param date 日期
     * @param number 预约数
     * @return
     */
  int updateOrderNumber(@Param("date") String date,@Param("number") int number);
}
