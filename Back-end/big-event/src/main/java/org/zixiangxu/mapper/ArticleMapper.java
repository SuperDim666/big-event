package org.zixiangxu.mapper;/*
 * @author Zixiang Xu
 * @date 2024/1/3 19:29
 */


import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.zixiangxu.pojo.Article;

@Mapper
public interface ArticleMapper {

    // Add new Article
    @Insert("insert into article (title, content, cover_img, state, category_id, "
                               + "create_user, create_time, update_time) "
        + "values(#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, "
               + "#{createUser}, #{createTime}, #{updateTime})")
    void add(Article article);

    // Update Article
    @Update("update article set "
        + "title=#{title}, content=#{content}, cover_img=#{coverImg},"
        + "state=#{state}, category_id=#{categoryId}, update_time=now() "
        + "where id=#{id} and create_user=#{createUser}")
    void update(Article article);

    // Get Article
    @Select("select * from article where id=#{id} and create_user=#{createUserId}")
    Article findById(int id, int createUserId);

    // Delete Article
    @Delete("delete from article where id=#{id} and create_user=#{createUserId}")
    void delete(int id, int createUserId);

    // Get Article list (conditional paging with pageHelper)
    // Note: using mapping profiles to write dynamic SQL:
    //       -- src/main/resources/org/zixiangxu/mapper/ArticleMapper.xml
    List<Article> list(int createUserId, String categoryId, String state);
}