package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约管理的controller
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Autowired
    private OrderSettingService os;


    @RequestMapping("/updateordernumber")
    public Result updateOrderNumber(String date,int number){

        //1.调用service干活
      int row=  os.updateOrderNumber(date,number);
      //2.判断
        Result result=null;
        if(row > 0){
            result=new Result(true,"设置预约数量成功!");
        }else{
            result=new Result(false,"设置预约数量失败!");
        }
        return result;
    }

    @RequestMapping("/getOrderSettingByMonth")
    public Result findOrderByMonth(String date){//2021-06

        //1.调用service
        List<OrderSetting> list = os.findOrderByMonth(date);

        //2.由于页面已经写好了，它需要的数据和我们从service拿到的数据不一样,所以不能直接

        //给页面返回，需要手动封装一下
        List<Map> mapList=new ArrayList<Map>();

        for (OrderSetting orderSetting : list) {

            //每一条预约对象就是一个map，之所以要封装是因为页面要求的属性的名字和OrderSetting的属性名不一样!

            Map map=new HashMap();

            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());

            //构建好了之后，封装到集合里面去
            mapList.add(map);
        }
        //返回数据
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,mapList);
    }

    /**
     * 导入预约信息,导入excel文件
     * @param excelFile 就是页面上传上来的Excel文件
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile){

        try {
        /*
            1.读取excel文件的内容
                1.1 由于表格里面有可能有很多的行，所以返回值是一个list集合
                1.2 里面包装的是每一行的数据，用数组来包装
                1.3 数组的第0位，表示第一个格子的内容，数组的第1位，表示第二格子的内容
         */
            List<String[]> strList= POIUtils.readExcel(excelFile);

            //2.遍历集合，取出来预约的日期以及可预约的最大数量，然后封装到一个OrderSetting身上.
            List<OrderSetting> orderSettingList=new ArrayList<>();
            SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd");

            //组装数据成JavaBean OrderSetting对象
            for (String[] strArr : strList) {
                OrderSetting os=new OrderSetting(sf.parse(strArr[0]),Integer.parseInt(strArr[1]));
                orderSettingList.add(os);
            }

            //4.交代service干活
            int row= os.add(orderSettingList);

            Result result=null;
            if (row > 0){
                result =new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
            }else{
                result =new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
            }
            return result;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }
}
