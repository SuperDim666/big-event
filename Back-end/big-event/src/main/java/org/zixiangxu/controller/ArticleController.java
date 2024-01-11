package org.zixiangxu.controller;/*
 * @author Zixiang Xu
 * @date 2024/1/1 23:12
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zixiangxu.pojo.Article;
import org.zixiangxu.pojo.PageBean;
import org.zixiangxu.pojo.Result;
import org.zixiangxu.service.ArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public Result<?> add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody @Validated(Article.Update.class) Article article) {
        if (articleService.findById(article.getId()) == null) return Result.error("Article not found");
        articleService.update(article);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result<?> detail(int id) {
        Article article = articleService.findById(id);
        if (article == null) return Result.error("Article not found");
        return Result.success(article);
    }

    @DeleteMapping
    public Result<?> Delete(int id) {
        if (articleService.findById(id) == null) return Result.error("Article not found");
        articleService.delete(id);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(int pageNum, int pageSize,
                                          @RequestParam(required = false) String categoryId,
                                          @RequestParam(required = false) String state) {
        PageBean<Article> pageBean = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pageBean);
    }
}