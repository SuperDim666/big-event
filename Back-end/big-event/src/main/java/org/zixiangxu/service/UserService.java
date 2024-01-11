package org.zixiangxu.service;/*
 * @author Zixiang Xu
 * @date 2024/1/1 21:47
 */

import org.zixiangxu.pojo.User;

public interface UserService {

    // Search for User by username
    User findByUserName(String username);

    // Register User
    void register(String username, String password);

    // Update User
    void update(User user);

    // Update User's avatar
    void updateAvatar(String avatarUrl);

    // Update User's password
    void updatePwd(String password, int id);
}
