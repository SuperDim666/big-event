package org.zixiangxu.mapper;/*
 * @author Zixiang Xu
 * @date 2024/1/3 16:37
 */


import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.zixiangxu.pojo.Category;

@Mapper
public interface CategoryMapper {

    // Add new Article Category into database
    @Insert("insert into category(category_name, category_alias,"
                               + "create_user, create_time, update_time) "
        + "values(#{categoryName}, #{categoryAlias}, #{createUser},"
               + "#{createTime}, #{updateTime})")
    void add(Category category);

    // Get Category list of current User
    @Select("select * from category where create_user=#{id}")
    List<Category> list(int id);

    // Get Category details
    @Select("select * from category where id=#{id} and create_user=#{createUserId}")
    Category findById(int id, int createUserId);

    // Update Category details
    @Update("update category set "
        + "category_name=#{categoryName}, "
        + "category_alias=#{categoryAlias}, "
        + "update_time=now() "
        + "where id=#{id} and create_user=#{createUser}")
    void update(Category category);

    // Delete Category
    @Delete("delete from category where id=#{id} and create_user=#{createUserId}")
    void delete(int id, int createUserId);
}