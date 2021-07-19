package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {

    /**
     * 添加检查组的基本信息
     * @param checkGroup 检查组的对象
     * @return 影响的行数
     */
    int add(CheckGroup checkGroup);

    /**
     * 添加检查组和检查项的关系到中间表去
     * @param checkGroupId 检查组的id
     * @param checkItemId 检查项的id
     * @return 影响的行数
     */
    int addItem(@Param("checkGroupId") int checkGroupId,@Param("checkItemId") int checkItemId);

    /**
     * 分页查询检查组
     * @param queryString
     * @return
     */
  Page<CheckGroup> findByCondition(String queryString);

    /**
     * 通过id检查组的信息
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
     * 更新检查组信息
     * @param checkGroup
     */
  void update(CheckGroup checkGroup);

    /**
     * 通过检查组id删除检查组与检查项的旧关系
     * @param checkGroupId
     */
  void deleteCheckGroupCheckItem(Integer checkGroupId);

    /**
     * 根据检查组的id，去t_setmeal_checkgroup表中是否有记录
     * @param checkgroupId
     * @return
     */
  int findCountById(int checkgroupId);

    /**
     * 删除检查组
     * @param checkgroupId
     * @return
     */
  int delete(int checkgroupId);

    /**
     * 查询检查组信息
     * @return
     */
  List<CheckGroup>findAll();
}
