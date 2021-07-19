package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/*
    检查组的控制器
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Autowired
    private CheckGroupService cs;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,int [] checkitemIds){
        System.out.println(checkGroup);
        System.out.println(Arrays.toString(checkitemIds));


        //1.调用Service干活
      int row = cs.add(checkGroup,checkitemIds);

      //2.判定
        Result result=null;
        if(row > 0){
            result=new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }else{
            result=new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }

        return result;
    }


    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用业务查询
        PageResult<CheckGroup> pageResult = cs.findPage(queryPageBean);

        //封装到Result返回
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /**
     * 通过id检查信息
     */
    @GetMapping("/findById")
    public Result findById(int id){
        //调用业务查询
        CheckGroup checkGroup = cs.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     * 通过检查组id查询选中的检查项id集合
     * @param id
     * @return
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int id){
        List<Integer> list = cs.findCheckItemIdsByCheckGroupId(id);

        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }

    /**
     * 更新检查组
     * @param checkGroup
     * @param checkitemIds  注意与前端提价的参数名要一致
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        //调用业务更新
        cs.update(checkGroup,checkitemIds);

        //返回结果
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除检查组
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(int id){
        //1.调用service删除
       int row= cs.delete(id);

       //2.判定结果
        Result result=null;

        if(row > 0){
            result=new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        }else{
            result=new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }

        //3.返回
        return result;
    }

    /**
     * 查询所有的检查组信息
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            //1.调用service
            List<CheckGroup> list= cs.findAll();

            //2.响应
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();

            return new Result(true,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }
}
