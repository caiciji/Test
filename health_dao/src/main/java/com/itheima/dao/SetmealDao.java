package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealDao {

    /**
     * 添加套餐
     * @param setmeal
     * @return
     */
   int add(Setmeal setmeal);


    /**
     * 添加套餐用到的检查组的信息到中间表去
     * @param setmealId   套餐的id
     * @param checkgroupId  检查组的id
     * @return
     */
   int addCheckGroup(@Param("setmealId") int setmealId,@Param("checkgroupId") int checkgroupId);

    /**
     * 查询所有图片
     * @return
     */
    List<String> getImgs();

    /**
     * 分页查询
     * @param bean
     * @return
     */
   Page<Setmeal> findPage(QueryPageBean bean);
}
