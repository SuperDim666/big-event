package org.zixiangxu.service.impl;/*
 * @author Zixiang Xu
 * @date 2024/1/1 21:47
 */

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zixiangxu.mapper.UserMapper;
import org.zixiangxu.pojo.User;
import org.zixiangxu.service.UserService;
import org.zixiangxu.utils.Md5Util;
import org.zixiangxu.utils.ThreadLocalUtil;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(String username, String password) {

        // Add with password encrypted by MD5
        userMapper.add(username, Md5Util.getMD5String(password));
    }

    @Override
    public void update(User user) {

        // Update the current time
        user.setUpdateTime(LocalDateTime.now());

        // Update all data into database
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {

        // Get User ID
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");

        // Update avatarUrl and update_time
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePwd(String password, int id) {

        // Update avatarUrl and update_time
        userMapper.updatePwd(Md5Util.getMD5String(password), id);
    }
}