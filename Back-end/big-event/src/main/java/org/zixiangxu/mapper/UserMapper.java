package org.zixiangxu.mapper;/*
 * @author Zixiang Xu
 * @date 2024/1/1 21:49
 */

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.zixiangxu.pojo.User;

@Mapper
public interface UserMapper {

    // Search for User by username from database
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);

    // Add User into database
    @Insert("insert into user(username, password, create_time, update_time)"
        + " values(#{username}, #{password}, now(), now())")
    void add(String username, String password);

    // Update the specified User in the database
    @Update("update user set "
        + "nickname=#{nickname}, "
        + "email=#{email}, "
        + "update_time=#{updateTime} "
        + "where id=#{id}")
    void update(User user);

    // Update avatar url and update_time only
    @Update("update user set user_pic=#{avatarUrl}, update_time=now() "
        + "where id=#{id}")
    void updateAvatar(String avatarUrl, int id);

    // Update password and update_time only
    @Update("update user set password=#{password}, update_time=now() "
        + "where id=#{id}")
    void updatePwd(String password, int id);
}