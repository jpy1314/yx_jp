package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.CategoryDao;
import cn.baizhi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CateService {
    @Autowired
    private CategoryDao cd;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryByLevels(int levels) {
        return  cd.queryByLevels(levels);

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryByParentId(String id) {
        return cd.queryByParentId(id);

    }
@DeleteCache
    @Override
    public void insert(Category category) {
        category.setId(UUID.randomUUID().toString());
    cd.insert(category);
    }
    @DeleteCache
    @Override
    public void delete(String id) {
        cd.delete(id);
    }
}
