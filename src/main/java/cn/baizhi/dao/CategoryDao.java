package cn.baizhi.dao;

import cn.baizhi.entity.Category;

import java.util.List;

public interface CategoryDao {
    //查一个
    List<Category> queryByLevels (int levels);
    //根据id查询二级类别
    List<Category> queryByParentId(String id);
    //添加一级类别对应的二级类别
    void insert(Category category);
    //删除
    void delete(String id);
}
