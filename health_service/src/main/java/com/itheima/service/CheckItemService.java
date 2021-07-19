package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {

    /**
     * 新增检查项
     * @param checkItem
     * @return
     */
    int add(CheckItem checkItem);

    /**
     * 查询分页
     * @param bean
     * @return  由于页面使用elementUT来编写的，所以返回的数据必须包含两个数据：total&list
     *              正好PageResult里面包含了这两个数据，所以返回值就写PageResult。
     */
   PageResult<CheckItem> findPage(QueryPageBean bean);

    /**
     * 删除检查项
     * @param id
     * @return
     */
   int delete(int id);

    /**
     * 更行检查项
     * @param checkItem
     * @return 影响的行数
     */
   int update(CheckItem checkItem);

    /**
     * 查询所有的检查项
     * @return
     */
  List<CheckItem> findAll();
}
