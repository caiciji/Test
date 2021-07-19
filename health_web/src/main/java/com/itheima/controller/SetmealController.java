package com.itheima.controller;

import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    //注入
    @Autowired
    private SetmealService ss;


    
   /* public Result update(){
        return null;
    }*/
    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){

        try {
            //1.调用service
            PageResult<Setmeal> pageResult = ss.findPage(queryPageBean);


            //2.响应
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }


    /**
     * 新增
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, int [] checkgroupIds){

        //1.调用service
        int row = ss.add(setmeal, checkgroupIds);

        //2.判定
        Result result=null;
        if(row>0){
            result=new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        }else{
            result=new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return result;

    }


    /**
     * 文件上传
     * @param imgFile imgFile 是页面上已经规定好的。所以这个参数的名字必须和页面的名字一样
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){

        try {
            //1.准备文件名字
            String oldName=imgFile.getOriginalFilename();//girl.jpg
            String newName= UUID.randomUUID().toString()+oldName.substring(oldName.lastIndexOf('.'));

            //2.把这个文件上传到七牛云
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),newName);

        /*
            3.返回结果给页面
                3.1 页面上需要把图片显示出来，上传成功之后要展示图片，所以我们需要把图片的地址给返回回去
                3.2 页面上点击确定之后，去完成新建套餐的动作，也需要把图片的名字给保存到数据库，所以也需要把图片的名字返回。
                3.3 在这里不能盲目的直接把图片的地址返回。现在打算拆分返回。
                3.4 使用一个map集合来封装返回的数据: 包含两个数据
                    3.4.1 一个是七牛云，自己的空间的域名
                    3.4.2 一个是图片的名字
         */
            Map<String,String> map=new HashMap<String,String>();
            map.put("domain",QiNiuUtils.DOMAIN);
            map.put("imgName",newName);

            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }

    }
}
