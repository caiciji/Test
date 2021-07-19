package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    /**
     * 添加检查组
     * @param checkGroup 检查组的基本信息
     * @param checkitemIds 检查组包含的检查的id值
     * @return>0 : 添加成功 ，否则：添加失败.
     */
    int add(CheckGroup checkGroup,int [] checkitemIds);

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
   PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id检查组信息
     * @param id
     * @return
     */
   CheckGroup findById(int id);

    /**
     * 通过检查组id查询选中的检查项id集合
     * @param id
     * @return
     */
   List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 更新检查组
     * @param checkGroup
     * @param checkitemIds
     */
   void update(CheckGroup checkGroup, Integer[] checkitemIds);


    /**
     * 删除检查组
     * @param id
     * @return
     */
  int  delete(int id);


    /**
     * 查询所有的检查组信息
     * @return
     */
 List<CheckGroup> findAll();
}
