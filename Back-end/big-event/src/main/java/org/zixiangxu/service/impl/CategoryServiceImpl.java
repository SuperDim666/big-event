package org.zixiangxu.service.impl;/*
 * @author Zixiang Xu
 * @date 2024/1/1 21:47
 */


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zixiangxu.mapper.CategoryMapper;
import org.zixiangxu.pojo.Category;
import org.zixiangxu.service.CategoryService;
import org.zixiangxu.utils.ThreadLocalUtil;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void add(Category category) {
        // Supplement some extra params required
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Map<String, Object> claims = ThreadLocalUtil.get();
        int id = (Integer) claims.get("id");
        category.setCreateUser(id);
        // Add new category into database
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        int id = (Integer) claims.get("id");
        return categoryMapper.list(id);
    }

    @Override
    public Category findById(int id) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        int createUserId = (Integer) claims.get("id");
        return categoryMapper.findById(id, createUserId);
    }

    @Override
    public void update(Category category) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        category.setCreateUser((Integer) claims.get("id"));
        categoryMapper.update(category);
    }

    @Override
    public void delete(int id) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        int createUserId = (Integer) claims.get("id");
        categoryMapper.delete(id, createUserId);
    }
}