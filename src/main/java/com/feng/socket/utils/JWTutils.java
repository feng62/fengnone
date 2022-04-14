package com.feng.socket.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTutils {

    private static final String SING = "RTYHFG%^*$$jjbujyg%^&^&*u";
    /**
     * 生成token
     */
    public  static String getToken(Map<String , String> map){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE ,30);//默认30天过期
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        String token = builder.withExpiresAt(instance.getTime()) //指定令牌时间
                .sign(Algorithm.HMAC256( SING ));
        return token;
    }

    /**
     * 验证token
     */
    public static DecodedJWT verify (String token){
        return JWT.require(Algorithm.HMAC256( SING )).build().verify( token );
    }

}
