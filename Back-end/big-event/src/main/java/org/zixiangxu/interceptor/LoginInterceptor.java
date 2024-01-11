package org.zixiangxu.interceptor;/*
 * @author Zixiang Xu
 * @date 2024/1/2 1:12
 */


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.zixiangxu.utils.JwtUtil;
import org.zixiangxu.utils.ThreadLocalUtil;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate; // Redis

    @Autowired
    public LoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        // Login Token Validation
        String token = request.getHeader("Authorization");
        try {
            // Get and check token in Redis
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get(token);
            if (redisToken == null) throw new RuntimeException();

            Map<String, Object> claims = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(claims);
        } catch (Exception e) {
            // Set the http response status code to 401
            response.setStatus(401);
            return false; // passing not allowed
        }
        return true; // passing allowed
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception ex) {
        // Clear our ThreadLocal used previously after the response
        ThreadLocalUtil.remove();
    }
}