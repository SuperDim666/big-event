package org.zixiangxu.utils;/*
 * @author Zixiang Xu
 * @date 2024/1/4 0:20
 */


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "ZixiangJacobXu";
    private static final int deltaExpireTime = 1000 * 60 * 60 * 12; // 12 hours
	
	// Receive service data, generate token and return it.
    public static String genToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + deltaExpireTime))
                .sign(Algorithm.HMAC256(KEY));
    }

	// Receive token, validate token, and return service data.
    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }
}
