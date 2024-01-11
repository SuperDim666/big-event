package org.zixiangxu.controller;/*
 * @author Zixiang Xu
 * @date 2024/1/1 21:46
 */

import jakarta.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zixiangxu.pojo.Result;
import org.zixiangxu.pojo.User;
import org.zixiangxu.service.UserService;
import org.zixiangxu.utils.Md5Util;
import org.zixiangxu.utils.JwtUtil;
import org.zixiangxu.utils.ThreadLocalUtil;


@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final StringRedisTemplate stringRedisTemplate; // Redis

    @Autowired
    public UserController(UserService userService, StringRedisTemplate stringRedisTemplate) {
        this.userService = userService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @PostMapping("/register")
    public Result<?> register(@Pattern(regexp = "^\\S{3,30}$") String username,
                              @Pattern(regexp = "^\\S{5,32}$") String password) {

        // User Inquiry based on username
        User user = userService.findByUserName(username);
        if (user == null) { // Username is not taken
            // Register
            userService.register(username, password);
            return Result.success();
        } else { // Username has been taken
            return Result.error("Username already taken");
        }
    }

    @PostMapping("/login")
    public Result<?> login(@Pattern(regexp = "^\\S{3,30}$") String username,
                           @Pattern(regexp = "^\\S{5,32}$") String password) {
        // User Inquiry based on username
        User loginUser = userService.findByUserName(username);
        if (loginUser == null) { // User with given username not found
            return Result.error("User with given username not found");
        }

        // Check password decrypted by MD5
        if (Md5Util.checkPassword(password, loginUser.getPassword())) {
            // Login Successful
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", username);
            String token = JwtUtil.genToken(claims);

            // Save token into Redis
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);
            // We take token out of Redis and check
            // when password changes while login

            return Result.success(token);
        }

        // Password unmatched
        return Result.error("Wrong password");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo() {

        // Get username from by ThreadLocal saved in LoginInterceptor
        // Since in LoginInterceptor, we saved claims data obtained from JWT token
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");

        // Get User info using user service
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    // Use @Validated to enable validations inside User.class
    // Example: @NotNull on User.id
    @PutMapping("/update")
    public Result<?> update(@RequestBody @Validated User user) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        int loginUserId = (Integer) claims.get("id");
        if (user.getId() != loginUserId) return Result.error("User ID mismatches with login User");
        userService.update(user);
        return Result.success();
    }

    // Update user info without login permission (administrator)
    @PutMapping("/updateAdmin")
    public Result<?> updateAdmin(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    // Validate if a string is a URL address by @URL
    @PatchMapping("/updateAvatar")
    public Result<?> updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result<?> updatePwd(@RequestBody Map<String, String> params,
                               @RequestHeader("Authorization") String token) {

        // 1. Validate params
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        String rePassword = params.get("rePassword");
        if (!StringUtils.hasLength(oldPassword) ||
            !StringUtils.hasLength(newPassword) ||
            !StringUtils.hasLength(rePassword))
            return Result.error("Lack of required password field(s)");

        // check if old password is same as the one in the database
        Map<String, Object> claims = ThreadLocalUtil.get();
        User user = userService.findByUserName((String) claims.get("username"));
        if (!Md5Util.checkPassword(oldPassword, user.getPassword()))
            return Result.error("Old password mismatches");
        if (!newPassword.equals(rePassword))
            return Result.error("New password mismatches the re-entered one");
        if (!newPassword.matches("^\\S{5,32}$"))
            return Result.error("New password pattern mismatches: no whitespace and 5-32 long");

        // 2. update password with encryption
        userService.updatePwd(newPassword, (Integer) claims.get("id"));

        // Delete old token in Redis
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        return Result.success();
    }
}