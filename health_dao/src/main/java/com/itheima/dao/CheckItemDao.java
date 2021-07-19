package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

    /**
     * 新增检查项
     * @param checkItem
     * @return
     */
    int add(CheckItem checkItem);

    /**
     * 分页查询
     * @param bean
     * @return
     */
   Page<CheckItem> findPage(QueryPageBean bean);

    /**
     * 根据检查项的id，去t_checkgroup_checkitem表查询它是否有记录
     * @param checkItemId
     * @return 存在的记录数
     */
   int findCountById(int checkItemId);

    /**
     * 删除检查项
     * @param checkItemId id值
     * @return
     */
    int delete(int checkItemId);

    /**
     * 更新检查项
     * @param checkItem
     * @return
     */
    int update(CheckItem checkItem);

    /**
     * 查询所有的检查项
     * @return
     */
    List<CheckItem> findAll();
}
