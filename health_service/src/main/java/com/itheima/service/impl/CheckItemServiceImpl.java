package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
    1.把这个类交给spring托管，需要加上注解@Service
    2.考虑使用事务，在类身上加上注解@Transactional
    3.调用dao即可
 */
@Service
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    //注入dao
    @Autowired
    private CheckItemDao dao;

    /**
     * 新增检查项
     * @param checkItem
     * @return
     */
    @Override
    public int add(CheckItem checkItem) {
        return dao.add(checkItem);
    }

    /**
     * 分页查询
     *  1. 一般来说，service向上(controller)返回的数据，通常都是问dao层要的，dao层 去查询数据库然后得到这份数据
     *  2.但是现在比较特殊，要返回的是PageResult，dao没有办法去查询具体某一张表，然后得到PageResult这种对象。
     *  3.所以在service层里面，我们需要手动封装PageResult。，也就是我们要手动创建PageResult对象，缺什么东西就问Dao层要即可
     * @param bean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean bean) {
        //0.设置查询第几页，每页查询多少条
        PageHelper.startPage(bean.getCurrentPage(),bean.getPageSize());

        //1.调用dao层返回数据
        Page<CheckItem> page = dao.findPage(bean);

        //2.准备数据
        long total = page.getTotal();//总记录数
        List<CheckItem> rows = page.getResult();//当前页的集合数据

        //3.创建PageResult
        PageResult<CheckItem> pr=new PageResult<CheckItem>(total,rows);

        return pr;
    }

    /**
     * 删除检查项
     *      1.不能像以前一样，直接上来就删除数据，需要做判断。
     *      2.如果检查项现在被某一个检查组所使用，那么禁止删除这个检查项
     *          2.1 要想知道检查项是否被检查组使用，其实就是拿着检查项的id去 t_checkgroup_chekitem 检查总记录数
     *          2.2 如果总记录数>0 即表示该检查项被检查组使用了，那么禁止删除
     *          2.3 如果总记录数=0 即表示该检查项没有被检查组使用，那么可以删除。
     * @param id 检查项的id
     * @return
     */
    @Override
    public int delete(int id) {
        //1.先查询这个检查项是否有被检查组使用
        int count = dao.findCountById(id);

        //如果 > 0 即表示有记录，那么禁止删除
        if(count > 0){
            System.out.println("存在检查组使用的情况，禁止删除该检查项:"+id);
            return 0;
        }
        //2.如果没有，就执行删除的操作
        return dao.delete(id);
    }

    /**
     * 更行检查项
     * @param checkItem
     * @return
     */
    @Override
    public int update(CheckItem checkItem) {
        return dao.update(checkItem);
    }


    /**
     * 查询所有的检查项
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return dao.findAll();
    }
}
