package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.handler.MessageContext;
import java.util.List;

/*
    @Controller:
        1.表示这个是一个组件，会被spring管理起来
        2.默认所有的方法返回值都会被当成是页面的名字

    @RestController:
        1.等于@Controller+@REsponseBody
        2.在类身上加上@RestController,即表示给所有的方法都加上了@ResponseBody
            等于告诉springmvc，所有的方法返回值都是字符串，不是页面的名字.
 */
//@Controller+@ResponseBody=@RestController
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {


    @Autowired
    private CheckItemService cs;

    /**
     * 更新检查项
     * @param checkItem
     * @return
     */
    @RequestMapping("/update")
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")//一定要有这个权限，才可以做更新操作
    public Result update(@RequestBody CheckItem checkItem){

        //1.调用service
        int row = cs.update(checkItem);

        //2.响应结果
        Result result=null;
        if(row > 0){
            result=new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }else{
            result=new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return result;
    }

    /**
     * 删除检查项
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result delete(int id){
        //1.调用service删除
        int row = cs.delete(id);

        //2.判定结果
        Result result=null;

        if(row>0){
            result=new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }else{
            result=new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        //3.返回
        return result;
    }

    /**
     * 检查项分页查询
     * @param bean  提交上来的参数，包含当前页，每页个数以及查询的条件
     *                  {currentPage:1 , pageSize:10 , queryString :"视力"}
     *                  {currentPage:1 , pageSize:10 , queryString:null}
     * @return
     */
    @RequestMapping("/findPage")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findPage(@RequestBody QueryPageBean bean){
        Result result=null;

        try {
            //1.调用service
            PageResult<CheckItem> pr = cs.findPage(bean);

            //2.组装result返回
            result=new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pr);


        } catch (Exception e) {
            e.printStackTrace();
            //2.组装result返回
            result=new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        //3.返回result
        return result;
    }

    /**
     * 添加检查项
     * @param checkItem
     * @return
     */
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem){
        //1.调用service就可以了.
        int row = cs.add(checkItem);

        //2.判断结果
        Result result=null;

        if(row>0) {//添加成功
            result=new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }else {//添加失败
            result=new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }
        //3.把result对像给页面返回!
        return result;
    }

    /**
     * 查询所有的检查项
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        Result result=null;

        try {
            //1.调用service，查询所有的检查项
            List<CheckItem> list = cs.findAll();

            //2.返回
            result=new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            result=new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return result;

    }
}
