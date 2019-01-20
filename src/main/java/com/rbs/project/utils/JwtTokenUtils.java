package com.rbs.project.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Description:
 *
 * @Author: 17Wang
 * @Date: 12:34 2018/12/10
 */
public class JwtTokenUtils {
    /**
     *  加盐
     */
    private static String salt="wangshiqi";

    public static String generateToken(String subject, int expirationSeconds) {
        return Jwts.builder()
                .setClaims(null)
                .setSubject(subject)
                //设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                //设置加密方式
                .signWith(SignatureAlgorithm.HS512, salt)
                .compact();
    }

    public static String parseToken(String token) {
        String subject = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(salt)
                    .parseClaimsJws(token).getBody();
            subject = claims.getSubject();
        } catch (Exception e) {
        }
        return subject;
    }
}
