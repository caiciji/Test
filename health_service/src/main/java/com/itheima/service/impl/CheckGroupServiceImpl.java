package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    //注入dao
    @Autowired
    private CheckGroupDao dao;

    /**
     * 新增检查组
     *      1.检查组包含两份数据，一份是自己的检查组的基本信息，一份是检查组使用了哪些检查项
     *      2.这两份数据需要保存到两张不同的表：t_checkgroup & t_checkgroup_checkitem
     *          2.1 先把基本的信息存入到检查组的表里面：t_checkgroup
     *          2.2 再把这个检查组用到了哪些检查项，把这些记录保存到中间表: t_checkgroup_checkitem
     *      3.一定要先往t_checkgroup这张表添加数据，这样子我们才能得到主键的返回,才能知道这个检查组的 id值是多少，有了id值，才能去往中间表里面添加记录。
     * @param checkGroup 检查组的基本信息
     * @param checkitemIds 检查组包含的检查的id值
     * @return
     */
    @Override
    public int add(CheckGroup checkGroup, int[] checkitemIds) {

        //1.往t_checkgroup 添加基本信息
        int row= dao.add(checkGroup);

        /*
            2.往t_checkgroup_checkitem添加检查项信息
                2.1 由于从页面过来的时候，这个检查组可能选择了很多的检查项,
                    所以这里要遍历出来每一个检查项
                2.2 遍历一次，就往中间表里面添加一条记录。
                    checkitemIds= 【28.29.30】;
         */
        //自有主表(检查组的表) 添加成功了之后，再去考虑加从表(中间表）的数据
        int row2=0;
        if(row>0){
            for (int checkitemId : checkitemIds) {
                row2+=dao.addItem(checkGroup.getId(),checkitemId);
            }
        }
        //当所有的操作都成功的时候，就返回1，否则就返回0.
        return (row>0 && row2==checkitemIds.length) ? 1 : 0;
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {

        //- 判断是否有查询条件，如果有则实现模糊查询，拼接%
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }

        //-使用PageHelper.startPage(页码，大小)
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        //-调用dao的findByCondition条件查询,返回page对象
        Page<CheckGroup> page = dao.findByCondition(queryPageBean.getQueryString());

        //-通过page对象获取total，result分页结果集
        //-封装到pageResult，返回给controller
        PageResult<CheckGroup> pageResult=new PageResult<CheckGroup>(page.getTotal(),page.getResult());

        return pageResult;
    }

    /**
     * 通过id检查组信息
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return dao.findById(id);
    }

    /**
     * 通过检查组id查询选中的检查项id集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        return dao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * 更新检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.更新检查组信息
        dao.update(checkGroup);

        //2.获取新增的检查组id
        Integer checkGroupId = checkGroup.getId();

        //3.先通过检查组id删除检查组与检查项的旧关系
        dao.deleteCheckGroupCheckItem(checkGroupId);

        //4.遍历选中的检查项id数组,空判断
        if(null !=checkitemIds){
            //5.添加检查组与检查项的新关系
            for (Integer checkitemId : checkitemIds) {
                dao.addItem(checkGroupId,checkitemId);
            }
        }
        //6.事务控制
    }

    /**
     * 删除检查组
     * @param id
     * @return
     */
    @Override
    public int delete(int id) {

        //1.先查询这个检查组是否有被套餐使用
      int count= dao.findCountById(id);

        //2.再通过检查组id删除检查组与检查项的旧关系
        dao.deleteCheckGroupCheckItem(id);

      //如果 >0 即表示有记录，那么禁止删除
        if(count >0){
            System.out.println("存在套餐使用的情况，禁止删除该检查组:"+id);
            return 0;
        }

        //2.如果没有，就执行删除的操作
        return dao.delete(id);
    }

    /**
     * 检查所有的检查组信息
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {

        //调用dao
        return dao.findAll();
    }
}
