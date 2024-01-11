package org.zixiangxu.service;/*
 * @author Zixiang Xu
 * @date 2024/1/3 16:36
 */


import java.util.List;
import org.zixiangxu.pojo.Category;

public interface CategoryService {

    // Add new Category
    void add(Category category);

    // Get Category list
    List<Category> list();

    // Get Category details
    Category findById(int id);

    // Update Category info
    void update(Category category);

    // Delete Category
    void delete(int id);
}