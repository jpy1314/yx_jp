package cn.baizhi.service;

import cn.baizhi.entity.Category;

import java.util.List;

public interface CateService {
    List<Category> queryByLevels (int levels);
    List<Category> queryByParentId(String id);
    void insert(Category category);
    void delete(String id);

}
