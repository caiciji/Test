package com.itheima.service;

import com.github.pagehelper.PageHelper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

public interface SetmealService {

    /**
     * 新增套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    int add(Setmeal setmeal,int [] checkgroupIds);


    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
   PageResult<Setmeal>findPage(QueryPageBean queryPageBean);
}
