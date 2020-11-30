package com.mogo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mogo.enums.ResponseEnum;
import com.mogo.exception.ApiException;

import java.util.Date;

/**
 * @Author: miaogang
 * @Date: 2020年11月04日
 * @Description: Token工具类
 */
public class JwtUtil {
    /**
     * 过期时间60分钟
      */
    private final static long EXPIRE_TIME = 60 * 60 * 1000;
    /**
     * TOKEN加盐
      */
    private final static String TOKEN_SECRET = "my_springcloud_course";
    /**
     * Token前缀
      */
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * Token header key
      */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 生成签名,5min后过期
     * @param username 用户名
     * @param userId 用户ID
     * @param secret 用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String userId) {
        // 过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            return JWT.create()
                    .withClaim("username", username)
                    .withClaim("userId", userId)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
        } catch (TokenExpiredException e) {
            throw new ApiException(ResponseEnum.TOKEN_EXPIRED);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            return false;
        }
        return true;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}

