package org.zixiangxu.service.impl;/*
 * @author Zixiang Xu
 * @date 2024/1/3 19:28
 */


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zixiangxu.mapper.ArticleMapper;
import org.zixiangxu.pojo.Article;
import org.zixiangxu.pojo.PageBean;
import org.zixiangxu.service.ArticleService;
import org.zixiangxu.utils.ThreadLocalUtil;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> claims = ThreadLocalUtil.get();
        article.setCreateUser((Integer) claims.get("id"));
        articleMapper.add(article);
    }

    @Override
    public void update(Article article) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        article.setCreateUser((Integer) claims.get("id"));
        articleMapper.update(article);
    }

    @Override
    public Article findById(int id) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        int createUserId = (Integer) claims.get("id");
        return articleMapper.findById(id, createUserId);
    }

    @Override
    public void delete(int id) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        int createUserId = (Integer) claims.get("id");
        articleMapper.delete(id, createUserId);
    }

    @Override
    public PageBean<Article> list(int pageNum, int pageSize,
                                  String categoryId, String state) {
        // 1. Create PageBean object
        PageBean<Article> pageBean = new PageBean<>();

        // 2. Enable paging query in MyBatis: PageHelper
        PageHelper.startPage(pageNum, pageSize);

        // 3. Use mapper to query
        Map<String, Object> claims = ThreadLocalUtil.get();
        int createUserId = (Integer) claims.get("id");
        List<Article> articleList = articleMapper.list(createUserId, categoryId, state);

        // 4. Reason to forced type-conversion
        //      -- Page provides methods to get the total number of records
        //         and the current page data from the PageHelper paging query.
        Page<Article> page = (Page<Article>) articleList;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }
}