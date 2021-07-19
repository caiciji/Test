package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SetmealServiceImpl implements SetmealService {
    //注入dao
    @Autowired
    private SetmealDao dao;

    /**
     * 新增套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @Override
    public int add(Setmeal setmeal, int[] checkgroupIds) {

        //1.先往套餐表里面添加它的基本信息
        int row1=dao.add(setmeal);


        //2.再往套餐-检查组的中间表，添加这个套餐用来哪些检查组
        int row2=0;
        if(checkgroupIds!=null && checkgroupIds.length>0){
            for (int checkgroupId : checkgroupIds) {

               row2+= dao.addCheckGroup(setmeal.getId(),checkgroupId);
            }
        }
        return (row1>0 && row2==checkgroupIds.length) ? 1 :0;
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {

        //先从第几页开始,每一页查询多少条
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());


        //调用dao ，返回QueryStringjson数据
        Page<Setmeal> page = dao.findPage(queryPageBean);

        Long total=page.getTotal();//总记录数

        List<Setmeal> rows=page.getResult();//记录集合的数据

        return new PageResult<>(total,rows);
    }
}
