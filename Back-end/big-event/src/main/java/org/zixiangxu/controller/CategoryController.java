package org.zixiangxu.controller;/*
 * @author Zixiang Xu
 * @date 2024/1/3 16:36
 */


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zixiangxu.pojo.Category;
import org.zixiangxu.pojo.Result;
import org.zixiangxu.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Result<?> add(@RequestBody @Validated Category category) {
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping
    public Result<List<Category>> list() {
        List<Category> categoryList = categoryService.list();
        return Result.success(categoryList);
    }

    @GetMapping("/detail")
    public Result<?> detail(int id) {
        Category category = categoryService.findById(id);
        if (category == null) return Result.error("Category not found");
        return Result.success(category);
    }

    @PutMapping
    public Result<?> update(@RequestBody @Validated(Category.Update.class) Category category) {
        if (categoryService.findById(category.getId()) == null) return Result.error("Category not found");
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    public Result<?> delete(int id) {
        if (categoryService.findById(id) == null) return Result.error("Category not found");
        categoryService.delete(id);
        return Result.success();
    }
}