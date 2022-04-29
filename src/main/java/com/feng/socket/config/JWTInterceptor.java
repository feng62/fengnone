package com.feng.socket.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feng.socket.pojo.Color;
import com.feng.socket.utils.JWTutils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String , Object> map = new HashMap<>();
        String token = request.getHeader("token");
        try {
            JWTutils.verify(token);
        }catch (SignatureVerificationException e ){
            e.printStackTrace();
            map.put("message","无效签名");
        }catch (TokenExpiredException e ){
            e.printStackTrace();
            map.put("message","token过期");
        }catch (AlgorithmMismatchException e ){
            e.printStackTrace();
            map.put("message","token算法不一致");
        }catch (Exception e ){
            e.printStackTrace();
            map.put("message","token无效");
        }
        map.put("success",false);
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }

}
