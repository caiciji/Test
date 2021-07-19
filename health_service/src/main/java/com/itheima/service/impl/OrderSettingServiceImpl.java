package com.itheima.service.impl;

import com.itheima.dao.OrderSettingDao;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

     @Autowired
     private OrderSettingDao dao;



    /**
     * 批量导入预约信息
     * @param orderSettingList
     * @return
     */
    @Override
    public int add(List<OrderSetting> orderSettingList) {
        int row1=0;
        int row2=0;

        //1.遍历集合，取出来每一个预约的对象，预约的对象里面包含预约的日期和最大可以预约的数

        for (OrderSetting orderSetting : orderSettingList) {

            //2.得到预约的日期和最大可预约数量
            Date date = orderSetting.getOrderDate();
            int number = orderSetting.getNumber();

            /*
                3. 往数据库里面添加|更新数据
                    3.1 不能盲目的认为批量导入就是直接往数据库里面添加预约的数据即可
                    3.2 要考虑到数据库里面已经存在了现在正要导入的预约的日期这种情况
                    3.3 要先拿着现在要导入的这个日期取数据库里面查询记录，数据库里面有没有这一天的预约数据

                        如果有:
                            3.3.1 数据库里面有现在要导入的这一天的预约记录，要更新这条记录

                            3.3.2 要比较数据库里面的已经预约的数量(reservations) 和现在要到如的可预约的数量大小关系

                                    如果 已预约的数量 > 现在要导入的可预约的数量，禁止更新

                                    否则 ，就可以更新了!

                        如果没有:
                            直接添加这条记录即可。

                    3.4 到底要做的是更新还是添加，取决与数据库里面有没有现在要导入这一天的预约记录
             */

            //3.根据日期去查询数据库里面的记录信息
            OrderSetting osInDB=dao.findOrderSettingByDate(date);
            if(osInDB != null){ //表明有记录存在 ： 还要判断

                //4.判断数据库的已经预约的数量和现在要导入的可以预约的数量大小关系
                if(number < osInDB.getReservations()){//禁止导入
                    throw new RuntimeException("可预约数量必须要大于已经预约的数量!");
                }else{//做更新操作
                    row1+=dao.update(orderSetting);
                }

            }else{//表明没有记录，添加操作
                row2+=dao.add(orderSetting);
            }
        }
        return (row1+ row2==orderSettingList.size()) ? 1 :0;
    }

    /**
     * 根据月份，来查询指定月的预约信息
     * @param date 2021-06 打算让dao查询一个区间的数据，从6-1 到 6月31日
     * @return
     */
    @Override
    public List<OrderSetting> findOrderByMonth(String date) {

        //1.拼接开始日期和结束日期
        String begin=date+"-1";//2021-6-1
        String end=date+"-31";//2021-6-31

        //2.查询
        return dao.findOrderByMonth(begin,end);
    }


    /**
     * 根据日期修改预约数量
     * @param date 日期
     * @param number 数量
     * @return
     */
    @Override
    public int updateOrderNumber(String date, int number) {
        return dao.updateOrderNumber(date,number);
    }
}
