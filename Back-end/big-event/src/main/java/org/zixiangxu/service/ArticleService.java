package org.zixiangxu.service;/*
 * @author Zixiang Xu
 * @date 2024/1/3 19:28
 */


import org.zixiangxu.pojo.Article;
import org.zixiangxu.pojo.PageBean;

public interface ArticleService {

    // Add Article
    void add(Article article);

    // Update Article
    void update(Article article);

    // Get Article by id
    Article findById(int id);

    // Delete Article by id
    void delete(int id);

    // Get Article list (conditional paging)
    PageBean<Article> list(int pageNum, int pageSize, String categoryId, String state);
}
