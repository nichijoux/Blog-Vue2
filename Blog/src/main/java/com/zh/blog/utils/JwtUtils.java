package com.zh.blog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.UUID;


public class JwtUtils {
    // 工具类
    private JwtUtils() {
    }

    // 有效期为
    public static final Long JWT_TTL = 60 * 60 * 1000L;// 60 * 60 * 1000  一个小时

    // 设置秘钥
    public static final String JWT_KEY = "75b9571306f0f26f2ef62bdddae7c579";

    /**
     * 获取一个UUID
     *
     * @return 随机生成的UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成jwt token
     *
     * @param subject token中要存放的数据（json格式）
     * @return 生成的jwt Token
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成jwt
     *
     * @param subject   token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return 生成的jwt Token
     */
    public static String createJWT(String subject, Long ttlMillis) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    /**
     * 获取生产的JWT token
     *
     * @param subject   主题
     * @param ttlMillis 超时时间
     * @param uuid      uuid
     * @return 生成的jwt Token
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtils.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)                // 唯一的ID
                .setSubject(subject)        // 主题  可以是JSON数据
                .setIssuer(JWT_KEY)         // 签发者
                .setIssuedAt(now)           // 签发时间
                //使用HS256对称加密算法签名, 第二个参数为秘钥
                .signWith(signatureAlgorithm, JWT_KEY)
                .setExpiration(expDate);
    }

    /**
     * 创建token
     *
     * @param id        用户id
     * @param subject   认证主题
     * @param ttlMillis 超时时间
     * @return 生成的jwt Token
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    /**
     * 解析
     *
     * @param jwt jwt Token
     * @return 解析后的实体
     */
    public static Claims parseJWT(String jwt) throws Exception{
        return Jwts.parser()
                .setSigningKey(JWT_KEY)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
